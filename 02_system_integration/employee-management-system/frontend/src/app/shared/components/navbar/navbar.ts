import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterLink } from '@angular/router';
import { LoginDialog } from '../../../features/auth/login-dialog/login-dialog';
import { AuthService } from '../../../core/services/auth';
import { RegisterDialog } from '../../../features/auth/register-dialog/register-dialog';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatButtonModule, RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private dialog = inject(MatDialog);
  private router = inject(Router);
  
  auth = inject(AuthService);

  openRegister() {
    this.dialog.open(RegisterDialog, { width: '480px' });
  }
  
  openLogin() {
    this.dialog.open(LoginDialog, {
      width: '480px',
    });
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/']);
  }
}
