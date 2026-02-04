import { AfterViewInit, Component, signal, ViewChild } from '@angular/core';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

import { Employee } from '../../api/models/employee';
import { EmployeeApiService } from '../../api/services/employee-api';
import { Page } from '../../api/models/page';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css'
})
export class EmployeeList implements AfterViewInit {
  @ViewChild(MatSort) sort!: MatSort;

  readonly employees = signal<Employee[]>([]);
  readonly totalElements = signal(0);
  readonly pageIndex = signal(0);
  readonly pageSize = signal(10);
  readonly sortState = signal<Sort>({ active: 'id', direction: 'asc' });
  readonly loading = signal(false);

  readonly displayedColumns = ['id', 'firstName', 'lastName', 'email', 'actions'];

  constructor(private employeeApi: EmployeeApiService) {
    this.sortState.set({ active: 'id', direction: 'asc' });
    this.loadEmployees();
  }

  ngAfterViewInit() {}

  loadEmployees(): void {
    this.loading.set(true);
    const sortStr = this.sortState().direction 
      ? `${this.sortState().active},${this.sortState().direction}` 
      : `${this.sortState().active},asc`;

    this.employeeApi.getEmployees(this.pageIndex(), this.pageSize(), sortStr)
      .subscribe({
        next: (page: Page<Employee>) => {
          this.employees.set(page.content);
          this.totalElements.set(page.totalElements);
          this.loading.set(false);
        },
        error: () => this.loading.set(false)
      });
  }
  
  edit(employee: Employee): void {
    console.log('Edit:',
      employee.id,
      employee.firstName,
      employee.lastName,
      employee.email
    );
  }
  
  delete(employee: Employee): void {
    console.log('Delete:',
      employee.id,
      employee.firstName,
      employee.lastName,
      employee.email
    );
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadEmployees();
  }

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