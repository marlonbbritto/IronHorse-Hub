import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

interface Moto {
  id: number;
  modelo: string;
  ano: number;
  km: number;
  placa: string;
  vin: string;
}

@Component({
  selector: 'app-moto-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="min-h-screen bg-[#0a0a0a] text-white p-6 md:p-12">
      <!-- Header -->
      <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-10 gap-4">
        <div>
          <h1 class="text-4xl font-black tracking-tighter uppercase italic">
            Minha <span class="text-orange-600 text-5xl">Garagem</span>
          </h1>
          <p class="text-gray-400 mt-2">Gestão de frota IronHorse</p>
        </div>
        
        <div class="flex gap-4">
          <button routerLink="/garage/new" 
            class="bg-orange-600 hover:bg-orange-700 text-white font-bold py-3 px-6 rounded-full transition-all shadow-lg hover:shadow-orange-500/20 active:scale-95 flex items-center gap-2">
            <span>+</span> ADICIONAR MOTO
          </button>
          <button (click)="logout()" 
            class="bg-[#1a1a1a] hover:bg-white/10 text-gray-400 font-bold py-3 px-6 rounded-full border border-white/10 transition-all">
            SAIR
          </button>
        </div>
      </div>

      <!-- Loading State -->
      @if (loading()) {
        <div class="flex justify-center items-center h-64">
          <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-orange-600"></div>
        </div>
      }

      <!-- Empty State -->
      @if (!loading() && motos().length === 0) {
        <div class="bg-[#1a1a1a] rounded-2xl p-12 text-center border border-dashed border-white/10">
          <div class="text-gray-400 text-6xl mb-4">🏍️</div>
          <h3 class="text-2xl font-bold mb-2 uppercase">Sua garagem está vazia</h3>
          <p class="text-gray-400 mb-6">Comece cadastrando sua primeira Harley-Davidson agora mesmo.</p>
          <button routerLink="/garage/new" class="text-orange-600 font-bold hover:underline">Cadastrar Agora</button>
        </div>
      }

      <!-- Grid de Motos -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        @for (moto of motos(); track moto.id) {
          <div class="bg-[#1a1a1a] rounded-2xl overflow-hidden shadow-xl border border-white/10 hover:border-orange-600/50 transition-all group">
            <div class="h-48 bg-gradient-to-br from-[#0a0a0a] to-[#1a1a1a] relative flex items-center justify-center p-8">
               <div class="text-8xl opacity-20 filter grayscale group-hover:grayscale-0 transition-all duration-500">🏍️</div>
               <div class="absolute bottom-4 left-4 bg-orange-600 text-white text-[10px] font-black px-2 py-1 rounded italic uppercase">
                 {{ moto.ano }}
               </div>
            </div>
            
            <div class="p-6">
              <h3 class="text-xl font-bold truncate mb-1 uppercase tracking-tight italic">{{ moto.modelo }}</h3>
              <p class="text-gray-400 text-sm mb-4 font-mono">{{ moto.placa || 'SEM PLACA' }}</p>
              
              <div class="flex items-center justify-between mt-6 bg-[#0a0a0a] p-4 rounded-xl">
                <div>
                  <p class="text-[10px] text-gray-400 uppercase font-bold tracking-widest leading-none mb-1">Quilometragem</p>
                  <p class="text-xl font-black text-white leading-none italic">{{ moto.km | number }} <span class="text-xs text-orange-600">KM</span></p>
                </div>
                <button class="bg-white/5 hover:bg-orange-600 p-3 rounded-lg transition-colors">
                  <span class="text-orange-600 group-hover:text-white">➔</span>
                </button>
              </div>
            </div>
          </div>
        }
      </div>
    </div>
  `
})
export class MotoListComponent implements OnInit {
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  motos = signal<Moto[]>([]);
  loading = signal<boolean>(true);

  ngOnInit() {
    this.fetchMotos();
  }

  fetchMotos() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`);
    this.http.get<Moto[]>('/api/v1/motos', { headers }).subscribe({
      next: (data) => {
        this.motos.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  logout() {
    this.authService.logout();
  }
}
