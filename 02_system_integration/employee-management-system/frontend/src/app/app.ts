import { Component, signal, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './shared/components/navbar/navbar';
import { AuthService } from './core/services/auth';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('EMS');

  private auth = inject(AuthService);

  constructor() {
    const token = localStorage.getItem('token');
    if (token && !this.isTokenExpired(token)) {
      this.auth.isLoggedIn.set(true);
      this.auth.username.set(this.extractUsername(token));
      this.auth.roles.set(this.auth.extractRoles(token));
    } else {
      this.auth.logout();
    }
  }

  private isTokenExpired(token: string): boolean {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp * 1000 < Date.now();
  }

  private extractUsername(token: string): string {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.sub;
  }
}
