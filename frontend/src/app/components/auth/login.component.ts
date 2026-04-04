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
    <div class="min-h-screen flex items-center justify-center bg-ironbase relative overflow-hidden">
      <!-- Background Image with Overlay -->
      <div class="absolute inset-0 bg-[url('/assets/login-bg.png')] bg-cover bg-center scale-105 blur-sm opacity-40"></div>
      <div class="absolute inset-0 bg-gradient-to-b from-ironbase/50 via-transparent to-ironbase"></div>

      <!-- Login Card -->
      <div class="max-w-md w-full glass p-10 rounded-3xl shadow-2xl relative z-10 border border-white/5 animate-glow">
        <div class="text-center mb-8">
          <h1 class="text-5xl font-black tracking-tighter text-white mb-2 uppercase italic font-brand">
            IRONHORSE <span class="text-ironaccent">HUB</span>
          </h1>
          <div class="h-1 w-24 bg-ironaccent mx-auto rounded-full mb-4"></div>
          <p class="text-gray-400 font-medium tracking-wide uppercase text-xs">Acesse sua garagem lendária</p>
        </div>
        
        <form class="space-y-6" (ngSubmit)="onSubmit()">
          <div class="space-y-4">
            <div class="group">
              <label for="email" class="block text-[10px] font-bold text-gray-500 uppercase tracking-widest mb-1 ml-1 group-focus-within:text-ironaccent transition-colors">E-mail do Piloto</label>
              <input id="email" name="email" type="email" required [(ngModel)]="credentials.email"
                class="w-full px-4 py-4 rounded-xl bg-ironbase/60 border border-white/10 text-white placeholder-gray-600 focus:outline-none focus:ring-2 focus:ring-ironaccent focus:border-transparent transition-all font-medium" 
                placeholder="exemplo@ironhorse.com">
            </div>
            <div class="group">
              <label for="password" class="block text-[10px] font-bold text-gray-500 uppercase tracking-widest mb-1 ml-1 group-focus-within:text-ironaccent transition-colors">Senha Secreta</label>
              <input id="password" name="password" type="password" required [(ngModel)]="credentials.password"
                class="w-full px-4 py-4 rounded-xl bg-ironbase/60 border border-white/10 text-white placeholder-gray-600 focus:outline-none focus:ring-2 focus:ring-ironaccent focus:border-transparent transition-all font-medium" 
                placeholder="••••••••">
            </div>
          </div>

          @if (error) {
            <div class="text-red-400 text-xs bg-red-400/10 p-4 rounded-xl border border-red-400/20 flex items-center gap-2">
              <span class="text-lg">⚠️</span> {{ error }}
            </div>
          }

          <div class="flex items-center justify-end">
            <a routerLink="/recovery" class="text-xs font-bold text-gray-500 hover:text-ironaccent transition-colors uppercase tracking-widest">
              Esqueceu a senha?
            </a>
          </div>

          <div>
            <button type="submit" [disabled]="loading"
              class="w-full py-4 px-6 bg-ironaccent hover:bg-orange-700 text-white font-black rounded-xl transition-all shadow-lg shadow-orange-900/20 hover:shadow-ironaccent/40 active:scale-[0.98] disabled:opacity-50 italic uppercase tracking-tighter text-lg">
              @if (loading) {
                <span class="flex items-center justify-center gap-2">
                  <svg class="animate-spin h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"><circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle><path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path></svg>
                  ACELERANDO...
                </span>
              } @else {
                INICIAR JORNADA
              }
            </button>
          </div>

          <div class="text-center pt-4 border-t border-white/5">
            <p class="text-gray-500 text-xs font-medium uppercase tracking-wide">
              Novo no bando? 
              <a routerLink="/register" class="text-ironaccent hover:text-white transition-colors font-bold ml-1">Crie sua conta</a>
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
