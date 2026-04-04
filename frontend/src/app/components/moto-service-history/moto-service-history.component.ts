import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface Manutencao {
  id: number;
  descritivo: string;
  km: number;
  dataServico: string;
  custo: number;
  prestador: string;
  tipoServico: string;
}

@Component({
  selector: 'app-moto-service-history',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="min-h-screen bg-ironbase text-white p-6 md:p-12 relative overflow-hidden">
      <!-- Decor Background -->
      <div class="fixed top-0 right-0 w-1/3 h-1/3 bg-ironaccent/5 blur-[120px] rounded-full -z-10"></div>
      <div class="fixed bottom-0 left-0 w-1/2 h-1/2 bg-white/5 blur-[150px] rounded-full -z-10"></div>

      <!-- Header -->
      <div class="max-w-4xl mx-auto mb-16 relative">
        <div class="flex items-center gap-4 mb-6">
          <a routerLink="/garage" class="text-gray-500 hover:text-white transition-colors text-sm font-black uppercase tracking-widest flex items-center gap-2">
            <span>←</span> Voltar para Garagem
          </a>
        </div>
        <h1 class="text-5xl font-black tracking-tighter uppercase italic font-brand leading-none">
          Diário de <span class="text-ironaccent">Manutenção</span>
        </h1>
        <div class="flex items-center justify-between mt-6">
          <p class="text-gray-500 font-bold uppercase tracking-widest text-[10px]">Histórico Cronológico de Serviços</p>
          <a [routerLink]="['/garage', motoId, 'services', 'new']" 
            class="bg-ironaccent hover:bg-orange-700 text-white font-black py-3 px-6 rounded-xl transition-all shadow-lg active:scale-95 uppercase text-xs">
            + Registrar Serviço
          </a>
        </div>
      </div>

      <!-- Timeline Area -->
      <div class="max-w-4xl mx-auto relative">
        @if (loading()) {
          <div class="flex flex-col justify-center items-center h-64 gap-4">
            <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-ironaccent"></div>
          </div>
        } @else if (services().length === 0) {
          <div class="glass rounded-3xl p-20 text-center border-dashed border-2 border-white/5">
            <h3 class="text-2xl font-black mb-3 uppercase italic font-brand">Nenhum serviço registrado</h3>
            <p class="text-gray-500 font-medium max-w-sm mx-auto">Sua máquina está nova em folha ou apenas aguardando a primeira revisão?</p>
          </div>
        } @else {
          <!-- Vertical Line -->
          <div class="absolute left-[23px] top-0 bottom-0 w-[2px] bg-white/5 shadow-[0_0_15px_rgba(255,255,255,0.05)]"></div>

          <div class="space-y-12 relative">
            @for (item of services(); track item.id) {
              <div class="flex gap-10 group">
                <!-- Icon Marker -->
                <div class="relative z-10 w-12 h-12 rounded-2xl bg-ironbase border-2 border-white/10 flex items-center justify-center group-hover:border-ironaccent group-hover:shadow-[0_0_15px_rgba(242,95,15,0.3)] transition-all duration-500 overflow-hidden">
                  <div class="absolute inset-0 bg-ironaccent/5 opacity-0 group-hover:opacity-100 transition-opacity"></div>
                  <span class="text-lg grayscale group-hover:grayscale-0 transition-all transform group-hover:scale-110">
                    {{ getIcon(item.tipoServico) }}
                  </span>
                </div>

                <!-- Content Card -->
                <div class="flex-1 glass rounded-3xl p-8 border border-white/5 hover:border-white/10 transition-all duration-500">
                  <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-6 gap-4">
                    <div>
                      <div class="flex items-center gap-3 mb-1">
                        <span class="text-[9px] bg-ironaccent/20 text-ironaccent px-2 py-0.5 rounded font-black uppercase tracking-widest">{{ item.tipoServico }}</span>
                        <span class="text-gray-600 text-[10px] font-bold">{{ item.dataServico | date:'dd MMM yyyy' }}</span>
                      </div>
                      <h3 class="text-xl font-black uppercase italic font-brand tracking-tighter text-white">{{ item.descritivo }}</h3>
                    </div>
                    <div class="bg-white/5 p-4 rounded-2xl text-right border border-white/5 min-w-[120px]">
                      <p class="text-[8px] text-gray-500 uppercase font-black mb-1">Custo</p>
                      <p class="text-lg font-black text-ironaccent">R$ {{ item.custo | number:'1.2-2' }}</p>
                    </div>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-6 pt-6 border-t border-white/5">
                    <div class="flex items-center gap-3">
                      <div class="w-8 h-8 rounded-lg bg-white/5 flex items-center justify-center text-xs">🛣️</div>
                      <div>
                        <p class="text-[8px] text-gray-500 uppercase font-black">Km no Serviço</p>
                        <p class="text-sm font-bold text-gray-300">{{ item.km | number }} KM</p>
                      </div>
                    </div>
                    @if (item.prestador) {
                      <div class="flex items-center gap-3">
                        <div class="w-8 h-8 rounded-lg bg-white/5 flex items-center justify-center text-xs">🛠️</div>
                        <div>
                          <p class="text-[8px] text-gray-500 uppercase font-black">Prestador</p>
                          <p class="text-sm font-bold text-gray-300 truncate">{{ item.prestador }}</p>
                        </div>
                      </div>
                    }
                  </div>
                </div>
              </div>
            }
          </div>
        }
      </div>
    </div>
  `
})
export class MotoServiceHistoryComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

  motoId: string | null = null;
  services = signal<Manutencao[]>([]);
  loading = signal<boolean>(true);

  ngOnInit() {
    this.motoId = this.route.snapshot.paramMap.get('id');
    if (this.motoId) {
      this.fetchHistory();
    }
  }

  fetchHistory() {
    this.http.get<Manutencao[]>(`/api/v1/motos/${this.motoId}/manutencoes`).subscribe({
      next: (data) => {
        this.services.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  getIcon(tipo: string): string {
    switch (tipo) {
      case 'OLEO': return '💧';
      case 'PNEUS': return '⭕';
      case 'REVISAO': return '🔍';
      case 'PECAS': return '⚙️';
      default: return '🛠️';
    }
  }
}
