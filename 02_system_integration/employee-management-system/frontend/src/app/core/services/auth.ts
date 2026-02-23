import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/auth';
  
  isLoggedIn = signal(false);
  username = signal<string | null>(null);
  roles = signal<string[]>([]);
  
  login(username: string, password: string) {
    return this.http.post<{ token: string }>
      (`${this.apiUrl}/login`, { username, password }).pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
          this.isLoggedIn.set(true);
          this.username.set(username);
          this.roles.set(this.extractRoles(response.token));
        })
      );
  }
  
  register(username: string, password: string) {
    return this.http.post<void>(`${this.apiUrl}/register`, { username, password });
  }
  
  logout() {
    localStorage.removeItem('token');
    this.isLoggedIn.set(false);
    this.username.set(null);
    this.roles.set([]);
  }
  
  hasRole(role: string): boolean {
    return this.roles().includes(role);
  }
  
  public extractRoles(token: string): string[] {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.authorities ?? [];
  }
  
}
