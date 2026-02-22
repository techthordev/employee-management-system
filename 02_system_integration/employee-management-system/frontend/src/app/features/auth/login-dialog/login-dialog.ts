import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-login-dialog',
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  templateUrl: './login-dialog.html',
  styleUrl: './login-dialog.css',
})
export class LoginDialog {

  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<LoginDialog>);
  private authService = inject(AuthService)

  form = this.fb.group({
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });
  
  submit() {
    if (this.form.valid) {
      const { username, password } = this.form.value;
      this.authService.login(username!, password!).subscribe({
          next: () => this.dialogRef.close(true),
          error: () => { }
      });
    }
  }

  close () {
    this.dialogRef.close();
  }

}
