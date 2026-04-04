import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

interface ItemSaude {
  tipoServico: string;
  kmRestante: number;
  percentualVidaUtil: number;
  statusVisual: string;
}

interface Moto {
  id: number;
  modelo: string;
  ano: number;
  km: number;
  placa: string;
  vin: string;
  dataUltimaAtualizacao: string;
  saude?: ItemSaude[];
}

import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-moto-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  template: `
    <div class="min-h-screen bg-ironbase text-white p-6 md:p-12 selection:bg-ironaccent selection:text-white">
      <!-- Decor Background -->
      <div class="fixed top-0 right-0 w-1/3 h-1/3 bg-ironaccent/5 blur-[120px] rounded-full -z-10"></div>
      <div class="fixed bottom-0 left-0 w-1/2 h-1/2 bg-white/5 blur-[150px] rounded-full -z-10"></div>

      <!-- Header -->
      <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-16 gap-6 relative">
        <div>
          <h1 class="text-5xl font-black tracking-tighter uppercase italic font-brand leading-none">
            Minha <span class="text-ironaccent">Garagem</span>
          </h1>
          <div class="flex items-center gap-2 mt-3">
            <div class="h-[2px] w-12 bg-ironaccent"></div>
            <p class="text-gray-500 font-bold uppercase tracking-widest text-[10px]">Gestão de Frota IronHorse Hub</p>
          </div>
        </div>
        
        <div class="flex items-center gap-4">
          <button routerLink="/garage/new" 
            class="bg-ironaccent hover:bg-orange-700 text-white font-black py-4 px-8 rounded-xl transition-all shadow-lg hover:shadow-ironaccent/30 active:scale-95 flex items-center gap-3 italic uppercase tracking-tight">
            <span>+</span> ADICIONAR MÁQUINA
          </button>
          <button (click)="logout()" 
            class="bg-white/5 hover:bg-white/10 text-gray-400 hover:text-white font-bold py-4 px-6 rounded-xl border border-white/10 transition-all uppercase text-xs tracking-widest">
            SAIR
          </button>
        </div>
      </div>

      <!-- Loading State -->
      @if (loading()) {
        <div class="flex flex-col justify-center items-center h-96 gap-4">
          <div class="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-ironaccent"></div>
          <p class="text-ironaccent font-black italic uppercase animate-pulse">Sincronizando Motores...</p>
        </div>
      }

      <!-- Empty State -->
      @if (!loading() && motos().length === 0) {
        <div class="glass rounded-3xl p-20 text-center border-dashed border-2 border-white/5">
          <div class="text-7xl mb-6 grayscale opacity-30">🏍️</div>
          <h3 class="text-3xl font-black mb-3 uppercase italic font-brand">Sua garagem está silenciosa</h3>
          <p class="text-gray-500 mb-10 max-w-md mx-auto font-medium">O asfalto está chamando. Comece cadastrando sua primeira Harley-Davidson agora mesmo.</p>
          <button routerLink="/garage/new" class="text-ironaccent font-black hover:text-white transition-colors uppercase tracking-widest border-b-2 border-ironaccent pb-1">Cadastrar Agora</button>
        </div>
      }

      <!-- Grid de Motos -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
        @for (moto of motos(); track moto.id) {
          <div [class.animate-glow]="hasCriticalAlert(moto)"
            [class.shadow-ironaccent-glow]="hasCriticalAlert(moto)"
            class="glass rounded-3xl overflow-hidden shadow-2xl border border-white/5 hover:border-ironaccent/40 transition-all duration-500 group relative">
            
            <!-- Card Header/Image Area -->
            <div class="h-56 bg-gradient-to-br from-ironbase to-ironmetal relative flex items-center justify-center p-8 overflow-hidden">
               <div class="absolute inset-0 bg-ironaccent/5 opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
               <div class="text-9xl opacity-10 filter grayscale group-hover:grayscale-0 group-hover:opacity-40 transition-all duration-700 transform group-hover:scale-110">🏍️</div>
               
               <div class="absolute top-4 left-4 bg-ironaccent text-white text-[10px] font-black px-3 py-1.5 rounded-lg italic uppercase tracking-tighter">
                 {{ moto.ano }}
               </div>
               
               <div class="absolute top-4 right-4 flex items-center gap-2">
                 <a [routerLink]="['/garage', moto.id, 'services']" 
                   class="bg-white/10 hover:bg-ironaccent backdrop-blur-md p-1.5 rounded-lg border border-white/10 transition-all duration-300 group/link"
                   title="Histórico de Manutenções">
                   <span class="text-[10px] text-gray-300 group-hover/link:text-white transition-colors uppercase font-black tracking-widest flex items-center gap-1.5">
                     <span class="text-sm">⚙️</span>
                   </span>
                 </a>
                 <a [routerLink]="['/garage', moto.id, 'health']" 
                   class="bg-white/10 hover:bg-green-600 backdrop-blur-md p-1.5 rounded-lg border border-white/10 transition-all duration-300 group/health"
                   title="Configurar Metas de Saúde">
                   <span class="text-[10px] text-gray-300 group-hover/health:text-white transition-colors uppercase font-black tracking-widest flex items-center gap-1.5">
                     <span class="text-sm">🩺</span>
                   </span>
                 </a>
                 <div class="bg-white/5 backdrop-blur-md text-gray-400 text-[10px] font-bold px-3 py-1.5 rounded-lg border border-white/10 uppercase tracking-widest">
                   HD-CORE
                 </div>
               </div>
            </div>
            
            <!-- Card Body -->
            <div class="p-8">
              <div class="mb-6">
                @if (hasCriticalAlert(moto)) {
                  <div class="text-ironaccent text-[9px] font-black uppercase tracking-[0.2em] mb-2 flex items-center gap-1.5 animate-pulse">
                    <span class="text-xs">⚠️</span> Manutenção Necessária
                  </div>
                }
                <h3 class="text-2xl font-black truncate mb-1 uppercase tracking-tighter italic font-brand group-hover:text-ironaccent transition-colors">{{ moto.modelo }}</h3>
                <div class="flex items-center gap-2">
                  <span class="w-2 h-2 rounded-full bg-green-500 animate-pulse"></span>
                  <p class="text-gray-500 text-[10px] font-bold tracking-widest uppercase">{{ moto.placa || 'Sem Placa Registrada' }}</p>
                </div>
              </div>
              
              <div class="flex items-center justify-between mt-8 bg-ironbase/50 p-5 rounded-2xl border border-white/5 group-hover:border-ironaccent/20 transition-all relative">
                @if (updatingId() === moto.id) {
                  <div class="flex-1 mr-4 animate-in fade-in slide-in-from-left-2 duration-300">
                    <p class="text-[9px] text-ironaccent uppercase font-black tracking-[0.2em] leading-none mb-2">Novo Hodômetro</p>
                    <input type="number" [(ngModel)]="newKmValue" 
                      class="bg-transparent text-2xl font-black text-white w-full border-none outline-none focus:ring-0 italic font-brand"
                      placeholder="0" autofocus (keyup.enter)="saveKm(moto)">
                  </div>
                  <button (click)="saveKm(moto)" 
                    class="bg-ironaccent hover:bg-orange-700 p-4 rounded-xl transition-all duration-300 shadow-[0_0_20px_rgba(242,95,15,0.4)] animate-pulse">
                    <span class="text-white">✓</span>
                  </button>
                  <button (click)="cancelEdit()" class="absolute -top-3 -right-3 bg-white/10 hover:bg-red-500/20 text-[8px] p-1.5 rounded-full border border-white/10 text-gray-500 hover:text-red-400">✕</button>
                } @else {
                  <div>
                    <p class="text-[9px] text-gray-500 uppercase font-black tracking-[0.2em] leading-none mb-2">Quilometragem</p>
                    <p class="text-2xl font-black text-white leading-none italic font-brand">{{ moto.km | number }} <span class="text-xs text-ironaccent ml-1 font-bold">KM</span></p>
                    @if (moto.dataUltimaAtualizacao) {
                      <p class="text-[8px] text-gray-600 uppercase font-bold mt-2 tracking-widest leading-none">Atualizado: {{ moto.dataUltimaAtualizacao | date:'dd/MM/yyyy' }}</p>
                    }
                  </div>
                  <button (click)="startEdit(moto)" 
                    class="bg-white/5 hover:bg-ironaccent p-4 rounded-xl transition-all duration-300 group-hover:shadow-ironaccent/20 group-hover:shadow-lg">
                    <span class="text-ironaccent group-hover:text-white transition-colors">➔</span>
                  </button>
                }
              </div>
            </div>

            <!-- Detail Line -->
            <div class="h-1 w-0 bg-ironaccent group-hover:w-full transition-all duration-700"></div>
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
  updatingId = signal<number | null>(null);
  newKmValue: number = 0;

  ngOnInit() {
    this.fetchMotos();
  }

  fetchMotos() {
    this.http.get<Moto[]>('/api/v1/motos').subscribe({
      next: (data) => {
        this.motos.set(data);
        this.loading.set(false);
        // Buscar saúde para cada moto
        data.forEach(m => this.fetchHealth(m.id));
      },
      error: () => this.loading.set(false)
    });
  }

  fetchHealth(motoId: number) {
    this.http.get<{ itens: ItemSaude[] }>(`/api/v1/motos/${motoId}/alertas/saude`).subscribe({
      next: (res) => {
        this.motos.update(prev => prev.map(m => m.id === motoId ? { ...m, saude: res.itens } : m));
      }
    });
  }

  hasCriticalAlert(moto: Moto): boolean {
    return moto.saude?.some(s => s.statusVisual === 'CRITICO' || s.statusVisual === 'ALERTA') || false;
  }

  startEdit(moto: Moto) {
    this.updatingId.set(moto.id);
    this.newKmValue = moto.km;
  }

  cancelEdit() {
    this.updatingId.set(null);
  }

  saveKm(moto: Moto) {
    if (this.newKmValue < moto.km) {
      alert('A nova quilometragem não pode ser inferior à atual!');
      return;
    }

    this.http.patch<Moto>(`/api/v1/motos/${moto.id}/hodometro`, { km: this.newKmValue }).subscribe({
      next: (updatedMoto) => {
        // Atualiza a lista localmente para efeito instantâneo
        this.motos.update(prev => prev.map(m => m.id === updatedMoto.id ? updatedMoto : m));
        this.updatingId.set(null);
      },
      error: (err) => {
        alert(err.error?.message || 'Erro ao atualizar hodômetro');
      }
    });
  }

  logout() {
    this.authService.logout();
  }
}
