import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-[#0a0a0a] px-4">
      <div class="max-w-md w-full space-y-8 bg-[#1a1a1a] p-10 rounded-xl shadow-2xl border border-white/10">
        <div class="text-center">
          <h1 class="text-4xl font-bold tracking-tight text-white mb-2 uppercase italic">
            IRONHORSE <span class="text-orange-600">HUB</span>
          </h1>
          <p class="text-gray-400">Acesse sua garagem lendária</p>
        </div>
        
        <form class="mt-8 space-y-6" (ngSubmit)="onSubmit()">
          <div class="rounded-md shadow-sm -space-y-px">
            <div class="mb-4">
              <label for="email" class="sr-only">Email</label>
              <input id="email" name="email" type="email" required [(ngModel)]="credentials.email"
                class="appearance-none rounded-lg relative block w-full px-3 py-3 border border-white/10 bg-[#0a0a0a] text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-orange-600 focus:border-transparent sm:text-sm" 
                placeholder="Seu email">
            </div>
            <div>
              <label for="password" class="sr-only">Senha</label>
              <input id="password" name="password" type="password" required [(ngModel)]="credentials.password"
                class="appearance-none rounded-lg relative block w-full px-3 py-3 border border-white/10 bg-[#0a0a0a] text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-orange-600 focus:border-transparent sm:text-sm" 
                placeholder="Sua senha">
            </div>
          </div>

          @if (error) {
            <div class="text-red-500 text-sm bg-red-500/10 p-3 rounded-lg border border-red-500/20">
              {{ error }}
            </div>
          }

          <div class="flex items-center justify-between">
            <div class="text-sm">
              <a routerLink="/recovery" class="font-medium text-gray-400 hover:text-orange-600 transition-colors">
                Esqueceu a senha?
              </a>
            </div>
          </div>

          <div>
            <button type="submit" [disabled]="loading"
              class="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-bold rounded-lg text-white bg-orange-600 hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-600 transition-all transform hover:scale-[1.02] active:scale-95 disabled:opacity-50 disabled:hover:scale-100 italic uppercase">
              @if (loading) {
                <span class="animate-pulse">ENTRANDO...</span>
              } @else {
                INICIAR JORNADA
              }
            </button>
          </div>

          <div class="text-center mt-4">
            <p class="text-gray-400 text-sm">
              Não tem uma conta? 
              <a routerLink="/register" class="text-orange-600 hover:underline font-bold">Cadastre-se</a>
            </p>
          </div>
        </form>
      </div>
    </div>
  `
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  credentials = { email: '', password: '' };
  loading = false;
  error = '';

  onSubmit() {
    this.loading = true;
    this.error = '';
    
    this.authService.login(this.credentials).subscribe({
      next: () => this.router.navigate(['/garage']),
      error: (err) => {
        this.error = 'Credenciais inválidas ou erro no servidor.';
        this.loading = false;
      }
    });
  }
}
