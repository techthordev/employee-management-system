import { Component, Inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { Employee } from '../../api/models/employee';
import { EmployeeApiService } from '../../api/services/employee-api';

export interface EmployeeDialogData {
  mode: 'edit' | 'delete';
  employee: Employee;
}

@Component({
  selector: 'app-employee-dialog',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './employee-dialog.html',
  styleUrl: './employee-dialog.css',
})
export class EmployeeDialog {
  form: FormGroup;
  isLoading = signal(false);

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeApiService,
    public dialogRef: MatDialogRef<EmployeeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: EmployeeDialogData,
  ) {
    this.form = this.fb.group({
      firstName: [data.employee.firstName || '', Validators.required],
      lastName: [data.employee.lastName || '', Validators.required],
      email: [data.employee.email || '', [Validators.required, Validators.email]]
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {
    if (this.isLoading()) return;

    this.isLoading.set(true);

    if (this.data.mode === 'edit') {
      const updatedEmployee: Employee = {
        ...this.data.employee,
        ...this.form.value
      };

      this.employeeService.updateEmployee(this.data.employee.id!, updatedEmployee)
        .subscribe({
          next: (result: Employee) => {
            this.isLoading.set(false);
            this.dialogRef.close(result);
          },
          error: (err: unknown) => {
            this.isLoading.set(false);
            console.error('Error updating employee:', err);
          }
        });
    } else {
      this.employeeService.deleteEmployee(this.data.employee.id!)
        .subscribe({
          next: () => {
            this.isLoading.set(false);
            this.dialogRef.close(true);
          },
          error: (err: unknown) => {
            this.isLoading.set(false);
            console.error('Error deleting employee:', err);
          }
        });
    }
  }
}