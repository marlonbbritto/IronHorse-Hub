import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs';

interface AuthResponse {
  token: string;
  type: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private readonly AUTH_TOKEN = 'auth_token';
  private readonly AUTH_EMAIL = 'auth_email';

  // State via Signals
  currentUser = signal<string | null>(localStorage.getItem(this.AUTH_EMAIL));
  isAuthenticated = signal<boolean>(!!localStorage.getItem(this.AUTH_TOKEN));

  register(userData: any) {
    return this.http.post('/api/v1/users/register', userData);
  }

  login(credentials: any) {
    return this.http.post<AuthResponse>('/api/v1/users/login', credentials).pipe(
      tap(res => {
        localStorage.setItem(this.AUTH_TOKEN, res.token);
        localStorage.setItem(this.AUTH_EMAIL, res.email);
        this.currentUser.set(res.email);
        this.isAuthenticated.set(true);
      })
    );
  }

  logout() {
    localStorage.removeItem(this.AUTH_TOKEN);
    localStorage.removeItem(this.AUTH_EMAIL);
    this.currentUser.set(null);
    this.isAuthenticated.set(false);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.AUTH_TOKEN);
  }
}
