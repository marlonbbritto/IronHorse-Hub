import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface AlertaConfig {
  tipoServico: string;
  periodicidadeKm: number;
  ativo: boolean;
}

@Component({
  selector: 'app-moto-health-settings',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  template: `
    <div class="min-h-screen bg-ironbase text-white p-6 md:p-12 relative overflow-hidden">
      <!-- Decor Background -->
      <div class="fixed top-0 right-0 w-1/3 h-1/3 bg-ironaccent/5 blur-[120px] rounded-full -z-10"></div>
      <div class="fixed bottom-0 left-0 w-1/2 h-1/2 bg-white/5 blur-[150px] rounded-full -z-10"></div>

      <div class="max-w-3xl mx-auto">
        <header class="mb-12 relative">
          <div class="flex items-center gap-4 mb-6">
            <a routerLink="/garage" class="text-gray-500 hover:text-white transition-colors text-sm font-black uppercase tracking-widest flex items-center gap-2">
              <span>←</span> Voltar para Garagem
            </a>
          </div>
          <h1 class="text-4xl font-black tracking-tighter uppercase italic font-brand leading-none">
            Configuração de <span class="text-ironaccent">Saúde</span>
          </h1>
          <p class="text-gray-500 font-bold uppercase tracking-widest text-[10px] mt-4">Metas Manuais de Manutenção Preventiva</p>
        </header>

        <div class="space-y-6">
          @for (tipo of tiposServico; track tipo.id) {
            <div class="glass rounded-3xl p-8 border border-white/5 hover:border-white/10 transition-all duration-500 flex flex-col md:flex-row items-center gap-8 group">
              <div class="w-16 h-16 rounded-2xl bg-ironbase border border-white/10 flex items-center justify-center text-2xl group-hover:border-ironaccent/50 transition-colors">
                {{ tipo.icon }}
              </div>
              
              <div class="flex-1 text-center md:text-left">
                <h3 class="text-xl font-black uppercase italic font-brand tracking-tighter text-white">{{ tipo.label }}</h3>
                <p class="text-gray-500 text-[10px] uppercase font-black tracking-widest mt-1">Periodicidade de Manutenção</p>
              </div>

              <div class="flex flex-col md:flex-row items-center gap-6 w-full md:w-auto">
                <div class="relative w-full md:w-32">
                  <input type="number" 
                    [(ngModel)]="getConfig(tipo.id).periodicidadeKm"
                    class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent outline-none text-center font-black italic text-ironaccent transition-all"
                    placeholder="KM">
                  <span class="absolute -bottom-5 left-0 right-0 text-center text-[8px] text-gray-600 font-black uppercase">KM</span>
                </div>

                <div class="flex items-center gap-4">
                  <button (click)="toggleAtivo(tipo.id)" 
                    [class]="getConfig(tipo.id).ativo ? 'bg-ironaccent text-white' : 'bg-white/5 text-gray-600 border border-white/10'"
                    class="w-12 h-12 rounded-xl transition-all duration-300 font-black flex items-center justify-center">
                    {{ getConfig(tipo.id).ativo ? 'ON' : 'OFF' }}
                  </button>
                  
                  <button (click)="save(tipo.id)" 
                    class="bg-white/10 hover:bg-white text-white hover:text-ironbase font-black px-6 py-4 rounded-xl transition-all uppercase text-[10px] tracking-widest border border-white/5">
                    Salvar
                  </button>
                </div>
              </div>
            </div>
          }
        </div>
      </div>
    </div>
  `
})
export class MotoHealthSettingsComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

  motoId: string | null = null;
  configs = signal<AlertaConfig[]>([]);

  tiposServico = [
    { id: 'OLEO', label: 'Troca de Óleo', icon: '💧' },
    { id: 'REVISAO', label: 'Revisão Geral', icon: '🔍' },
    { id: 'PNEUS', label: 'Pneus e Rodagem', icon: '⭕' },
    { id: 'PECAS', label: 'Peças e Desgaste', icon: '⚙️' }
  ];

  ngOnInit() {
    this.motoId = this.route.snapshot.paramMap.get('id');
    if (this.motoId) {
      this.fetchConfigs();
    }
  }

  fetchConfigs() {
    this.http.get<AlertaConfig[]>(`/api/v1/motos/${this.motoId}/alertas/config`).subscribe({
      next: (data) => this.configs.set(data),
      error: () => {}
    });
  }

  getConfig(tipoId: string): AlertaConfig {
    return this.configs().find(c => c.tipoServico === tipoId) || { tipoServico: tipoId, periodicidadeKm: 0, ativo: false };
  }

  toggleAtivo(tipoId: string) {
    const config = this.getConfig(tipoId);
    config.ativo = !config.ativo;
    // Forçar atualização do sinal
    this.configs.update(c => [...c.filter(x => x.tipoServico !== tipoId), config]);
  }

  save(tipoId: string) {
    const config = this.getConfig(tipoId);
    if (!config.periodicidadeKm || config.periodicidadeKm <= 0) {
      alert('Defina uma quilometragem válida');
      return;
    }

    this.http.put(`/api/v1/motos/${this.motoId}/alertas`, config).subscribe({
      next: () => {
        alert(`Meta de ${tipoId} atualizada!`);
        this.fetchConfigs(); // Sincroniza com a verdade do bando (Backend)
      },
      error: (err) => alert(err.error?.message || 'Erro ao salvar meta')
    });
  }
}
