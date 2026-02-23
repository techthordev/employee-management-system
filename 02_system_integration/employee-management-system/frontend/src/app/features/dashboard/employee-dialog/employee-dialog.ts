import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { HttpClient } from '@angular/common/http';
import { Department } from '../../../core/models/department';
import { Employee } from '../../../core/models/employee';

@Component({
  selector: 'app-employee-dialog',
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule
  ],
  templateUrl: './employee-dialog.html',
  styleUrl: './employee-dialog.css',
})
export class EmployeeDialog implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private dialogRef = inject(MatDialogRef<EmployeeDialog>);
  data: Employee | null = inject(MAT_DIALOG_DATA);

  departments = signal<Department[]>([]);

  form = this.fb.group({
    firstName: ['', [Validators.required, Validators.maxLength(45)]],
    lastName: ['', [Validators.required, Validators.maxLength(45)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
    departmentId: [null as number | null, Validators.required]
  });

  get title(): string {
    return this.data ? 'Edit Employee' : 'New Employee';
  }

  get submitLabel(): string {
    return this.data ? 'Update' : 'Create';
  }

  ngOnInit() {
    this.http.get<Department[]>('http://localhost:8080/api/v1/departments').subscribe({
      next: (data) => this.departments.set(data)
    });

    if (this.data) {
      this.form.patchValue({
        firstName: this.data.firstName,
        lastName: this.data.lastName,
        email: this.data.email,
        departmentId: this.data.department.id
      });
    }
  }

  submit() {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value);
    }
  }

  close() {
    this.dialogRef.close();
  }
}