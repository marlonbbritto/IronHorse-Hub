import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-[#0a0a0a] px-4">
      <div class="max-w-md w-full space-y-8 bg-[#1a1a1a] p-10 rounded-xl shadow-2xl border border-white/10">
        <div class="text-center">
          <h1 class="text-3xl font-bold tracking-tight text-white mb-2 uppercase italic">
            NOVA <span class="text-orange-600">JORNADA</span>
          </h1>
          <p class="text-gray-400">Crie sua conta no IronHorse Hub</p>
        </div>
        
        <form class="mt-8 space-y-6" (ngSubmit)="onSubmit()">
          <div class="space-y-4">
            <div>
              <label for="email" class="block text-sm font-medium text-gray-400 mb-1">Email</label>
              <input id="email" name="email" type="email" required [(ngModel)]="userData.email"
                class="appearance-none rounded-lg relative block w-full px-3 py-3 border border-white/10 bg-[#0a0a0a] text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-orange-600 focus:border-transparent sm:text-sm" 
                placeholder="seu@email.com">
            </div>
            <div>
              <label for="password" class="block text-sm font-medium text-gray-400 mb-1">Senha</label>
              <input id="password" name="password" type="password" required [(ngModel)]="userData.password"
                class="appearance-none rounded-lg relative block w-full px-3 py-3 border border-white/10 bg-[#0a0a0a] text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-orange-600 focus:border-transparent sm:text-sm" 
                placeholder="Mínimo 8 caracteres">
            </div>
          </div>

          @if (error) {
            <div class="text-red-500 text-sm bg-red-500/10 p-3 rounded-lg border border-red-500/20">
              {{ error }}
            </div>
          }

          <div>
            <button type="submit" [disabled]="loading"
              class="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-bold rounded-lg text-white bg-orange-600 hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-600 transition-all transform hover:scale-[1.02] active:scale-95 disabled:opacity-50 italic uppercase">
              @if (loading) {
                <span class="animate-pulse">CRIANDO...</span>
              } @else {
                CRIAR CONTA
              }
            </button>
          </div>

          <div class="text-center">
            <p class="text-gray-400 text-sm">
              Já faz parte do bando? 
              <a routerLink="/login" class="text-orange-600 hover:underline font-bold">Entre aqui</a>
            </p>
          </div>
        </form>
      </div>
    </div>
  `
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  userData = { email: '', password: '' };
  loading = false;
  error = '';

  onSubmit() {
    this.loading = true;
    this.error = '';
    
    this.authService.register(this.userData).subscribe({
      next: () => this.router.navigate(['/login']),
      error: (err) => {
        this.error = 'Erro ao criar conta. Verifique os dados.';
        this.loading = false;
      }
    });
  }
}
