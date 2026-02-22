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
  
  login(username: string, password: string) {
    return this.http.post<{ token: string }>
      (`${this.apiUrl}/login`, { username, password }).pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
          this.isLoggedIn.set(true);
          this.username.set(username);
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
  }
  
}
