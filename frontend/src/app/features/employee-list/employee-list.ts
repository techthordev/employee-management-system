import { Component, computed, signal } from '@angular/core';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

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
export class EmployeeList {
  /** --- State Signals --- */
  readonly employees = signal<Employee[]>([]);
  readonly totalElements = signal(0);
  readonly pageIndex = signal(0);
  readonly pageSize = signal(10);
  readonly sortState = signal<Sort>({ active: 'lastName', direction: 'asc' });
  readonly loading = signal(false);

  readonly displayedColumns = ['rowNumber', 'firstName', 'lastName', 'email', 'actions'];
  readonly pageSizeOptions = computed(() => [5, 10, 20, 50, this.totalElements()]);

  readonly dataSource = computed(() => {
    const ds = new MatTableDataSource<Employee>(this.employees());
    return ds;
  });

  constructor(
    private employeeApi: EmployeeApiService,
    private dialog: MatDialog,
  ) {
    this.loadEmployees();
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
   * Handle filter/search functionality.
   */
   applyFilter(event: Event) {
     const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
   
     if (filterValue) {
       // Wenn gesucht wird: Wir fordern ALLE Elemente an, die die DB kennt
       this.employeeApi
         .getEmployees(0, this.totalElements(), 'lastName,asc', filterValue)
         .subscribe({
           next: (response) => {
             this.employees.set(response.content);
             // Wichtig: Wir setzen den lokalen Filter der DataSource, 
             // damit die Tabelle sofort reagiert
             this.dataSource().filter = filterValue;
           }
         });
     } else {
       // Wenn der Filter geleert wird: Zurück zum normalen Paging
       this.loadEmployees();
     }
   }

  /**
   * Handle pagination changes.
   */
  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadEmployees();
  }

  /**
   * Handle sort state changes.
   */
  onSortChange(sort: Sort): void {
    this.sortState.set(sort.active ? sort : { active: 'lastName', direction: 'asc' });
    this.pageIndex.set(0); // Reset to first page on sort
    this.loadEmployees();
  }

  /**
   * Open dialog to edit an existing employee.
   */
  edit(employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeDialog, {
      width: '400px',
      data: { mode: 'edit', employee: { ...employee } } as EmployeeDialogData,
    });
    dialogRef.afterClosed().subscribe((res) => {
      if (res) this.loadEmployees();
    });
  }

  /**
   * Open dialog to confirm employee deletion.
   */
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
