import { Component, inject, AfterViewInit, OnInit, signal, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { Employee } from '../../core/models/employee';
import { EmployeeDialog } from './employee-dialog/employee-dialog';
import { ConfirmDialog } from './confirm-dialog/confirm-dialog';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-dashboard',
  imports: [
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatSortModule,
    MatInputModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, AfterViewInit {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  @ViewChild(MatSort) sort!: MatSort;

  dataSource = new MatTableDataSource<Employee>([]);
  loading = signal(true);
  displayedColumns = ['firstName', 'lastName', 'email', 'department', 'actions'];

  ngOnInit() {
    this.loadEmployees();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.sortingDataAccessor = (employee, property) => {
      switch (property) {
        case 'department': return employee.department.name;
        default: return (employee as any)[property];
      }
    };
  }

  loadEmployees() {
    this.loading.set(true);
    this.http.get<Employee[]>('http://localhost:8080/api/v1/employees').subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.loading.set(false);
        setTimeout(() => {
          this.dataSource.sort = this.sort;
          this.dataSource.sortingDataAccessor = (employee, property) => {
            switch (property) {
              case 'department': return employee.department.name;
              default: return (employee as any)[property];
            }
          };
        });
      },
      error: () => this.loading.set(false),
    });
  }

  openCreate() {
    this.dialog.open(EmployeeDialog, { width: '500px', data: null })
      .afterClosed().subscribe((result) => {
        if (result) {
          this.http.post<Employee>('http://localhost:8080/api/v1/employees', result)
            .subscribe(() => this.loadEmployees());
        }
      });
  }

  openEdit(employee: Employee) {
    this.dialog.open(EmployeeDialog, { width: '500px', data: employee })
      .afterClosed().subscribe((result) => {
        if (result) {
          this.http.put<Employee>(`http://localhost:8080/api/v1/employees/${employee.id}`, result)
            .subscribe((updated) => {
              this.dataSource.data = this.dataSource.data.map(e => e.id === updated.id ? updated : e);
            });
        }
      });
  }

  delete(employee: Employee) {
    this.dialog.open(ConfirmDialog, {
      width: '400px',
      data: { message: `Are you sure you want to delete ${employee.firstName} ${employee.lastName}?` },
    }).afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.http.delete(`http://localhost:8080/api/v1/employees/${employee.id}`).subscribe({
          next: () => {
            this.dataSource.data = this.dataSource.data.filter(e => e.id !== employee.id);
          },
        });
      }
    });
  }
  
  applyFilter(event: Event) {
    const value = (event.target as HTMLInputElement).value;
    this.dataSource.filter = value.trim().toLowerCase();
  }
}