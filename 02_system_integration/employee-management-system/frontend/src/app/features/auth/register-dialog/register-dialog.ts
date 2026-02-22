import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from '../../../core/services/auth';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-register-dialog',
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  templateUrl: './register-dialog.html',
  styleUrl: './register-dialog.css',
})
export class RegisterDialog {

  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<RegisterDialog>);
  private authService = inject(AuthService);
  
  form = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });
  
  submit() {
    if (this.form.valid) {
      const { username, password } = this.form.value;
      this.authService.register(username!, password!).subscribe({
        next: () => this.dialogRef.close(true),
        error: () => { }
      });
    }
  }
  
  close() {
    this.dialogRef.close();
  }
  
}
