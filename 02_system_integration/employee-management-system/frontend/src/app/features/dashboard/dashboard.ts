import { Component, inject, OnInit, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { Employee } from '../../core/models/employee';
import { EmployeeDialog } from './employee-dialog/employee-dialog';

@Component({
  selector: 'app-dashboard',
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, MatDialogModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  employees = signal<Employee[]>([]);
  loading = signal(true);
  displayedColumns = ['firstName', 'lastName', 'email', 'department', 'actions'];

  ngOnInit() {
    this.loadEmployees();
  }

  loadEmployees() {
    this.loading.set(true);
    this.http.get<Employee[]>('http://localhost:8080/api/v1/employees').subscribe({
      next: (data) => {
        this.employees.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  openCreate() {
    this.dialog.open(EmployeeDialog, { width: '500px', data: null })
      .afterClosed().subscribe(result => {
        if (result) {
          this.http.post<Employee>('http://localhost:8080/api/v1/employees', result)
            .subscribe(() => this.loadEmployees());
        }
      });
  }

  openEdit(employee: Employee) {
    this.dialog.open(EmployeeDialog, { width: '500px', data: employee })
      .afterClosed().subscribe(result => {
        if (result) {
          this.http.put<Employee>(`http://localhost:8080/api/v1/employees/${employee.id}`, result)
            .subscribe(() => this.loadEmployees());
        }
      });
  }

  delete(id: number) {
    this.http.delete(`http://localhost:8080/api/v1/employees/${id}`).subscribe({
      next: () => this.employees.update(list => list.filter(e => e.id !== id))
    });
  }
}