import { Component, OnInit, signal, inject } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { Employee } from '../../core/models/employee';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-staff',
  imports: [
    MatTableModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './staff.html',
  styleUrl: './staff.css',
})
export class Staff implements OnInit {

  private http = inject(HttpClient);
  
  employees = signal<Employee[]>([]);
  loading = signal(true);
  displayedColumns = [
    'firstName', 'lastName', 'email', 'department'
  ];
  
  ngOnInit(): void {
    this.http.get<Employee[]>(
      'http://localhost:8080/api/v1/employees'
    ).subscribe({
      next: (data) => {
        this.employees.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
  
}
