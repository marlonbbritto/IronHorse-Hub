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
    <div class="min-h-screen bg-[#0a0a0a] text-white p-6 md:p-12">
      <div class="max-w-2xl mx-auto">
        <!-- Header -->
        <div class="mb-10 flex items-center gap-4">
          <button routerLink="/garage" class="text-gray-400 hover:text-white transition-colors">
            <span class="text-2xl">←</span>
          </button>
          <h1 class="text-3xl font-black uppercase italic tracking-tighter">
            Cadastrar <span class="text-orange-600">Máquina</span>
          </h1>
        </div>

        <div class="bg-[#1a1a1a] rounded-2xl shadow-2xl p-8 border border-white/10">
          <form [formGroup]="motoForm" (ngSubmit)="onSubmit()" class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <!-- Modelo -->
              <div class="md:col-span-2">
                <label class="block text-xs font-bold uppercase tracking-widest text-gray-400 mb-2">Modelo da Moto</label>
                <input formControlName="modelo" placeholder="Ex: Road King Special"
                  class="w-full bg-[#0a0a0a] border border-white/10 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-orange-600 text-white placeholder-white/20 uppercase italic font-bold">
              </div>

              <!-- Ano -->
              <div>
                <label class="block text-xs font-bold uppercase tracking-widest text-gray-400 mb-2">Ano</label>
                <input type="number" formControlName="ano"
                  class="w-full bg-[#0a0a0a] border border-white/10 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-orange-600 text-white font-bold">
              </div>

              <!-- KM -->
              <div>
                <label class="block text-xs font-bold uppercase tracking-widest text-gray-400 mb-2">KM Atual</label>
                <input type="number" formControlName="km"
                  class="w-full bg-[#0a0a0a] border border-white/10 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-orange-600 text-white font-bold">
              </div>

              <!-- Placa -->
              <div>
                <label class="block text-xs font-bold uppercase tracking-widest text-gray-400 mb-2">Placa (Opcional)</label>
                <input formControlName="placa" placeholder="ABC-1234"
                  class="w-full bg-[#0a0a0a] border border-white/10 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-orange-600 text-white uppercase placeholder-white/20 font-bold">
              </div>

              <!-- VIN -->
              <div>
                <label class="block text-xs font-bold uppercase tracking-widest text-gray-400 mb-2">Número do Chassi (VIN)</label>
                <input formControlName="vin" placeholder="17 caracteres"
                  class="w-full bg-[#0a0a0a] border border-white/10 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-orange-600 text-white uppercase placeholder-white/20 font-bold">
              </div>
            </div>

            @if (errorMessage()) {
              <div class="bg-red-500/10 border border-red-500/20 p-4 rounded-xl text-red-500 text-sm">
                {{ errorMessage() }}
              </div>
            }

            @if (successMessage()) {
              <div class="bg-green-500/10 border border-green-500/20 p-4 rounded-xl text-green-500 text-sm">
                {{ successMessage() }}
              </div>
            }

            <div class="pt-6 border-t border-white/5 flex justify-end gap-4">
              <button type="button" routerLink="/garage" class="px-6 py-3 text-gray-400 font-bold hover:text-white transition-colors uppercase">
                CANCELAR
              </button>
              <button type="submit" [disabled]="motoForm.invalid || isSubmitting()"
                class="bg-orange-600 hover:bg-orange-700 disabled:opacity-50 text-white font-black py-3 px-10 rounded-full transition-all shadow-lg hover:shadow-orange-500/40 active:scale-95 italic uppercase">
                @if (isSubmitting()) {
                  <span class="animate-pulse">PROCESSANDO...</span>
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
