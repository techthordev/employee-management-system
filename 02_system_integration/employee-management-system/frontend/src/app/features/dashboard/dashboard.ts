import { Component, inject, OnInit, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Employee } from '../../core/models/employee';

@Component({
  selector: 'app-dashboard',
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private http = inject(HttpClient);

  employees = signal<Employee[]>([]);
  loading = signal(true);
  displayedColumns = ['firstName', 'lastName', 'email', 'department', 'actions'];

  ngOnInit() {
    this.http.get<Employee[]>('http://localhost:8080/api/v1/employees').subscribe({
      next: (data) => {
        this.employees.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  delete(id: number) {
    this.http.delete(`http://localhost:8080/api/v1/employees/${id}`).subscribe({
      next: () => this.employees.update(list => list.filter(e => e.id !== id))
    });
  }
}