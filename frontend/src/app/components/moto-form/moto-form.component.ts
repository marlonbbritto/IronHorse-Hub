import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MotoService } from '../../services/moto.service';
import { Moto } from '../../models/moto.model';

/**
 * Componente standalone para o formulário de cadastro de motos.
 * Segue design premium com Tailwind CSS.
 */
@Component({
  selector: 'app-moto-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  template: `
    <div class="min-h-screen bg-ironbase text-white p-6 md:p-12 relative overflow-hidden">
      <!-- Decor Background -->
      <div class="fixed top-0 left-0 w-1/3 h-1/3 bg-ironaccent/5 blur-[120px] rounded-full -z-10"></div>

      <div class="max-w-2xl mx-auto relative z-10">
        <!-- Header -->
        <div class="mb-12 flex items-center gap-6">
          <button routerLink="/garage" class="w-12 h-12 glass flex items-center justify-center rounded-full text-gray-400 hover:text-ironaccent hover:border-ironaccent/50 transition-all active:scale-90">
            <span class="text-xl">←</span>
          </button>
          <div>
            <h1 class="text-4xl font-black uppercase italic tracking-tighter font-brand leading-none">
              Cadastrar <span class="text-ironaccent">Máquina</span>
            </h1>
            <p class="text-[10px] text-gray-500 font-bold uppercase tracking-[0.2em] mt-2">Adicionar novo exemplar ao bando</p>
          </div>
        </div>

        <div class="glass rounded-3xl shadow-2xl p-10 border border-white/5">
          <form [formGroup]="motoForm" (ngSubmit)="onSubmit()" class="space-y-8">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
              <!-- Modelo -->
              <div class="md:col-span-2 group">
                <label class="block text-[10px] font-black uppercase tracking-widest text-gray-500 mb-2 ml-1 group-focus-within:text-ironaccent transition-colors">Modelo da Moto</label>
                <input formControlName="modelo" placeholder="Ex: Road King Special"
                  class="w-full bg-ironbase/60 border border-white/10 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-ironaccent text-white placeholder-white/10 uppercase italic font-bold transition-all shadow-inner">
              </div>

              <!-- Ano -->
              <div class="group">
                <label class="block text-[10px] font-black uppercase tracking-widest text-gray-500 mb-2 ml-1 group-focus-within:text-ironaccent transition-colors">Ano de Fabricação</label>
                <input type="number" formControlName="ano"
                  class="w-full bg-ironbase/60 border border-white/10 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-ironaccent text-white font-bold transition-all shadow-inner">
              </div>

              <!-- KM -->
              <div class="group">
                <label class="block text-[10px] font-black uppercase tracking-widest text-gray-500 mb-2 ml-1 group-focus-within:text-ironaccent transition-colors">Quilometragem Atual</label>
                <input type="number" formControlName="km"
                  class="w-full bg-ironbase/60 border border-white/10 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-ironaccent text-white font-bold transition-all shadow-inner">
              </div>

              <!-- Placa -->
              <div class="group">
                <label class="block text-[10px] font-black uppercase tracking-widest text-gray-500 mb-2 ml-1 group-focus-within:text-ironaccent transition-colors">Placa (Opcional)</label>
                <input formControlName="placa" placeholder="ABC-1234"
                  class="w-full bg-ironbase/60 border border-white/10 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-ironaccent text-white uppercase placeholder-white/10 font-bold transition-all shadow-inner">
              </div>

              <!-- VIN -->
              <div class="group">
                <label class="block text-[10px] font-black uppercase tracking-widest text-gray-500 mb-2 ml-1 group-focus-within:text-ironaccent transition-colors">Número do Chassi (VIN)</label>
                <input formControlName="vin" placeholder="17 caracteres"
                  class="w-full bg-ironbase/60 border border-white/10 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-ironaccent text-white uppercase placeholder-white/10 font-bold transition-all shadow-inner">
              </div>
            </div>

            @if (errorMessage()) {
              <div class="bg-red-500/10 border border-red-500/20 p-5 rounded-2xl text-red-500 text-xs flex items-center gap-3">
                <span class="text-xl">⚠️</span> {{ errorMessage() }}
              </div>
            }

            @if (successMessage()) {
              <div class="bg-green-500/10 border border-green-500/20 p-5 rounded-2xl text-green-500 text-xs flex items-center gap-3">
                 <span class="text-xl">✅</span> {{ successMessage() }}
              </div>
            }

            <div class="pt-10 border-t border-white/5 flex items-center justify-between">
              <button type="button" routerLink="/garage" class="text-xs font-black text-gray-500 hover:text-white transition-colors uppercase tracking-[0.2em]">
                CANCELAR
              </button>
              <button type="submit" [disabled]="motoForm.invalid || isSubmitting()"
                class="bg-ironaccent hover:bg-orange-700 disabled:opacity-30 text-white font-black py-4 px-12 rounded-2xl transition-all shadow-xl shadow-orange-900/20 hover:shadow-ironaccent/30 active:scale-95 italic uppercase tracking-tighter text-lg">
                @if (isSubmitting()) {
                  <span class="flex items-center gap-2">
                    <svg class="animate-spin h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"><circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle><path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path></svg>
                    GRAVANDO...
                  </span>
                } @else {
                  GRAVAR NA GARAGEM
                }
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  `
})
export class MotoFormComponent {
  private readonly fb = inject(FormBuilder);
  private readonly motoService = inject(MotoService);
  private readonly router = inject(Router);

  // Sinais (Signals) para controle de estado da UI
  public isSubmitting = signal(false);
  public successMessage = signal<string | null>(null);
  public errorMessage = signal<string | null>(null);

  // Definição do Formulário Reativo
  public motoForm = this.fb.group({
    modelo: ['', [Validators.required, Validators.minLength(2)]],
    ano: [new Date().getFullYear(), [Validators.required, Validators.min(1900)]],
    km: [0, [Validators.required, Validators.min(0)]],
    placa: [''],
    vin: ['']
  });

  public onSubmit(): void {
    if (this.motoForm.invalid) return;

    this.isSubmitting.set(true);
    this.successMessage.set(null);
    this.errorMessage.set(null);

    const newMoto: any = this.motoForm.getRawValue();

    this.motoService.cadastrar(newMoto).subscribe({
      next: (moto) => {
        this.successMessage.set(`Moto ${moto.modelo} cadastrada com sucesso!`);
        setTimeout(() => this.router.navigate(['/garage']), 1500);
      },
      error: (err) => {
        this.errorMessage.set('Erro ao cadastrar moto. Verifique os dados.');
        this.isSubmitting.set(false);
      }
    });
  }
}
