import { Component, computed, signal, OnDestroy } from '@angular/core';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { Employee } from '../../api/models/employee';
import { EmployeeApiService } from '../../api/services/employee-api';
import { Page } from '../../api/models/page';
import { EmployeeDialog, EmployeeDialogData } from '../employee-dialog/employee-dialog';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
  ],
  templateUrl: './employee-list.html',
  styleUrls: ['./employee-list.css'],
})
export class EmployeeList implements OnDestroy {
  /** --- State Signals --- */
  readonly employees = signal<Employee[]>([]);
  readonly totalElements = signal(0);
  readonly pageIndex = signal(0);
  readonly pageSize = signal(10);
  readonly sortState = signal<Sort>({ active: 'lastName', direction: 'asc' });
  readonly loading = signal(false);

  readonly displayedColumns = ['rowNumber', 'firstName', 'lastName', 'email', 'actions'];
  readonly pageSizeOptions = computed(() => [5, 10, 20, 50, this.totalElements()]);

  // The dataSource is computed from the employees signal
  readonly dataSource = computed(() => {
    return new MatTableDataSource<Employee>(this.employees());
  });

  // Search logic
  private filterSubject = new Subject<string>();
  private filterSubscription: Subscription;

  constructor(
    private employeeApi: EmployeeApiService,
    private dialog: MatDialog,
  ) {
    // Initialize the debounce logic for searching
    this.filterSubscription = this.filterSubject.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(val => {
      this.executeSearch(val);
    });

    this.loadEmployees();
  }

  /**
   * Required by OnDestroy interface to prevent memory leaks.
   */
  ngOnDestroy(): void {
    if (this.filterSubscription) {
      this.filterSubscription.unsubscribe();
    }
  }

  /**
   * Fetch paginated and sorted employees from the API.
   */
  loadEmployees(): void {
    this.loading.set(true);
    const sortStr = `${this.sortState().active},${this.sortState().direction || 'asc'}`;

    this.employeeApi.getEmployees(this.pageIndex(), this.pageSize(), sortStr).subscribe({
      next: (response: Page<Employee>) => {
        this.employees.set(response.content);
        this.totalElements.set(response.page.totalElements);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error fetching employees:', err);
        this.loading.set(false);
      },
    });
  }

  /**
   * Triggered on every keystroke in the filter input.
   */
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filterSubject.next(filterValue);
  }

  /**
   * The actual search logic, called after debounce.
   */
  private executeSearch(filterValue: string): void {
    const trimmedValue = filterValue.trim().toLowerCase();

    if (trimmedValue) {
      this.loading.set(true);
      // "The Compromise": Fetch all elements from DB to allow full client-side filtering
      this.employeeApi
        .getEmployees(0, this.totalElements(), 'lastName,asc', trimmedValue)
        .subscribe({
          next: (response) => {
            this.employees.set(response.content);
            this.dataSource().filter = trimmedValue;
            this.loading.set(false);
          },
          error: (err) => {
            console.error('Search error:', err);
            this.loading.set(false);
          }
        });
    } else {
      // If filter is empty, return to normal paginated view
      this.pageIndex.set(0);
      this.loadEmployees();
    }
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadEmployees();
  }

  onSortChange(sort: Sort): void {
    this.sortState.set(sort.active ? sort : { active: 'lastName', direction: 'asc' });
    this.pageIndex.set(0);
    this.loadEmployees();
  }

  edit(employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeDialog, {
      width: '400px',
      data: { mode: 'edit', employee: { ...employee } } as EmployeeDialogData,
    });
    dialogRef.afterClosed().subscribe((res) => {
      if (res) this.loadEmployees();
    });
  }

  delete(employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeDialog, {
      width: '300px',
      data: { mode: 'delete', employee } as EmployeeDialogData,
    });
    dialogRef.afterClosed().subscribe((res) => {
      if (res) this.loadEmployees();
    });
  }
}