import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { LoginDialog } from '../../../features/auth/login-dialog/login-dialog';
import { AuthService } from '../../../core/services/auth';
import { RegisterDialog } from '../../../features/auth/register-dialog/register-dialog';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private dialog = inject(MatDialog);
  private router = inject(Router);
  
  auth = inject(AuthService);
  
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
