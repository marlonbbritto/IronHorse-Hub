import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-moto-service-form',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  template: `
    <div class="min-h-screen bg-ironbase text-white p-6 md:p-12 relative overflow-hidden">
      <!-- Decor Background -->
      <div class="fixed top-0 right-0 w-1/3 h-1/3 bg-ironaccent/5 blur-[120px] rounded-full -z-10"></div>
      <div class="fixed bottom-0 left-0 w-1/2 h-1/2 bg-white/5 blur-[150px] rounded-full -z-10"></div>

      <div class="max-w-2xl mx-auto">
        <header class="mb-12 relative">
          <div class="flex items-center gap-4 mb-6">
            <a [routerLink]="['/garage', motoId, 'services']" class="text-gray-500 hover:text-white transition-colors text-sm font-black uppercase tracking-widest flex items-center gap-2">
              <span>←</span> Cancelar Registro
            </a>
          </div>
          <h1 class="text-4xl font-black tracking-tighter uppercase italic font-brand leading-none">
            Registrar <span class="text-ironaccent">Manutenção</span>
          </h1>
          <p class="text-gray-500 font-bold uppercase tracking-widest text-[10px] mt-4">Documentação Técnica da Máquina</p>
        </header>

        <form (ngSubmit)="save()" #serviceForm="ngForm" class="glass rounded-3xl p-10 border border-white/5 space-y-8 relative">
          <!-- Main Info -->
          <div class="space-y-6">
            <div>
              <label class="text-[10px] text-gray-500 uppercase font-black tracking-widest mb-2 block italic text-ironaccent">Descritivo do Serviço</label>
              <input type="text" name="descritivo" [(ngModel)]="formData.descritivo" required
                class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent focus:ring-1 focus:ring-ironaccent outline-none transition-all placeholder:text-gray-700 font-bold"
                placeholder="Ex: Troca de Óleo Motul 7100 e Filtro K&N">
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="text-[10px] text-gray-500 uppercase font-black tracking-widest mb-2 block italic text-ironaccent">Quilometragem (KM)</label>
                <input type="number" name="km" [(ngModel)]="formData.km" required
                  class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent focus:ring-1 focus:ring-ironaccent outline-none transition-all font-bold">
              </div>
              <div>
                <label class="text-[10px] text-gray-500 uppercase font-black tracking-widest mb-2 block italic text-ironaccent">Data do Serviço</label>
                <input type="date" name="dataServico" [(ngModel)]="formData.dataServico" required
                  class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent focus:ring-1 focus:ring-ironaccent outline-none transition-all font-bold">
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="text-[10px] text-gray-500 uppercase font-black tracking-widest mb-2 block italic text-ironaccent">Custo (R$)</label>
                <input type="number" name="custo" [(ngModel)]="formData.custo"
                  class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent focus:ring-1 focus:ring-ironaccent outline-none transition-all font-bold"
                  placeholder="0,00">
              </div>
              <div>
                <label class="text-[10px] text-gray-500 uppercase font-black tracking-widest mb-2 block italic text-ironaccent">Tipo de Serviço</label>
                <select name="tipoServico" [(ngModel)]="formData.tipoServico" required
                  class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent focus:ring-1 focus:ring-ironaccent outline-none transition-all font-bold appearance-none">
                  <option value="REVISAO" class="bg-ironbase">Revisão Geral</option>
                  <option value="OLEO" class="bg-ironbase">Troca de Óleo</option>
                  <option value="PNEUS" class="bg-ironbase">Pneus</option>
                  <option value="PECAS" class="bg-ironbase">Troca de Peças</option>
                  <option value="OUTROS" class="bg-ironbase">Outros</option>
                </select>
              </div>
            </div>

            <div>
              <label class="text-[10px] text-gray-500 uppercase font-black tracking-widest mb-2 block italic text-ironaccent">Prestador (Oficina / Concessionária)</label>
              <input type="text" name="prestador" [(ngModel)]="formData.prestador"
                class="bg-white/5 border border-white/10 rounded-xl p-4 w-full focus:border-ironaccent focus:ring-1 focus:ring-ironaccent outline-none transition-all placeholder:text-gray-700 font-bold"
                placeholder="Ex: Rio Custom HD">
            </div>
          </div>

          <!-- Actions -->
          <div class="pt-8 border-t border-white/5 flex gap-4">
            <button type="submit" [disabled]="!serviceForm.valid || loading()"
              class="flex-1 bg-ironaccent hover:bg-orange-700 disabled:opacity-50 text-white font-black py-4 rounded-xl transition-all shadow-lg hover:shadow-ironaccent/30 active:scale-95 italic uppercase tracking-tight">
              {{ loading() ? 'SALVANDO...' : 'CONFIRMAR REGISTRO' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  `
})
export class MotoServiceFormComponent implements OnInit {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  motoId: string | null = null;
  loading = signal<boolean>(false);
  
  formData = {
    descritivo: '',
    km: 0,
    dataServico: new Date().toISOString().split('T')[0],
    custo: 0,
    prestador: '',
    tipoServico: 'REVISAO'
  };

  ngOnInit() {
    this.motoId = this.route.snapshot.paramMap.get('id');
  }

  save() {
    this.loading.set(true);
    this.http.post(`/api/v1/motos/${this.motoId}/manutencoes`, this.formData).subscribe({
      next: () => {
        this.router.navigate(['/garage', this.motoId, 'services']);
      },
      error: (err) => {
        alert(err.error?.message || 'Erro ao registrar manutenção');
        this.loading.set(false);
      }
    });
  }
}
