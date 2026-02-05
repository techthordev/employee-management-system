import { AfterViewInit, Component, computed, signal, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Employee } from '../../api/models/employee';
import { EmployeeApiService } from '../../api/services/employee-api';
import { Page } from '../../api/models/page';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EmployeeDialog, EmployeeDialogData } from '../employee-dialog/employee-dialog';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatPaginator,
    MatSortModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css',
})
export class EmployeeList implements AfterViewInit {
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  readonly employees = signal<Employee[]>([]);
  readonly searchTerm = signal('');
  readonly totalElements = signal(0);
  readonly pageIndex = signal(0);
  readonly pageSize = signal(10);
  readonly sortState = signal<Sort>({ active: 'id', direction: 'asc' });
  readonly loading = signal(false);
  readonly displayedColumns = ['rowNumber', 'firstName', 'lastName', 'email', 'actions'];

  constructor(
    private employeeApi: EmployeeApiService,
    private dialog: MatDialog,
  ) {
    this.sortState.set({ active: 'id', direction: 'asc' });
    this.loadEmployees();
  }

  ngAfterViewInit() { }
  
  get dataSource(): Employee[] {
    return this.employees();
  }

  /**
   * Load employees from API with current pagination and sorting.
   */
  loadEmployees(): void {
    this.loading.set(true);

    const sortStr = this.sortState().direction
      ? `${this.sortState().active},${this.sortState().direction}`
      : `${this.sortState().active},asc`;

    this.employeeApi.getEmployees(this.pageIndex(), this.pageSize(), sortStr, this.searchTerm()).subscribe({
      next: (response: Page<Employee>) => {
        this.employees.set(response.content);
        this.totalElements.set(response.page.totalElements);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  /**
   * Handle search input change event.
   */
  onSearchChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchTerm.set(input.value.trim());
    this.pageIndex.set(0);
    this.loadEmployees();
  }

  /**
   * Clear the search term.
   */
  clearSearch(): void {
    this.searchTerm.set('');
  }

  /**
   * Open edit dialog for employee.
   */
  edit(employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeDialog, {
      width: '400px',
      data: { mode: 'edit', employee: { ...employee } } as EmployeeDialogData,
    });

    dialogRef.afterClosed().subscribe((result: Employee | undefined) => {
      if (result) {
        this.loadEmployees();
      }
    });
  }

  /**
   * Open delete confirmation dialog for employee.
   */
  delete(employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeDialog, {
      width: '300px',
      data: { mode: 'delete', employee } as EmployeeDialogData,
    });

    dialogRef.afterClosed().subscribe((result: boolean | undefined) => {
      if (result === true) {
        this.loadEmployees();
      }
    });
  }

  /**
   * Handle pagination change event.
   */
  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadEmployees();
  }

  /**
   * Handle sort change event.
   */
  onSortChange(sort: Sort): void {
    if (!sort.active) {
      this.sortState.set({ active: 'id', direction: 'asc' });
    } else {
      this.sortState.set(sort);
    }
    this.pageIndex.set(0);
    this.loadEmployees();
  }
}
