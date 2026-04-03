import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MotoService } from '../../services/moto.service';
import { Moto } from '../../models/moto.model';

/**
 * Componente standalone para o formulário de cadastro de motos.
 * Segue design premium com Tailwind CSS.
 */
@Component({
  selector: 'app-moto-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './moto-form.component.html',
  styleUrls: ['./moto-form.component.css']
})
export class MotoFormComponent {
  private readonly fb = inject(FormBuilder);
  private readonly motoService = inject(MotoService);

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

  /**
   * Método disparado no submit do formulário.
   */
  public onSubmit(): void {
    if (this.motoForm.invalid) return;

    this.isSubmitting.set(true);
    this.successMessage.set(null);
    this.errorMessage.set(null);

    const newMoto: Moto = this.motoForm.getRawValue() as Moto;

    this.motoService.cadastrar(newMoto).subscribe({
      next: (moto) => {
        this.successMessage.set(`Moto ${moto.modelo} cadastrada com sucesso!`);
        this.motoForm.reset({ ano: new Date().getFullYear(), km: 0 });
        this.isSubmitting.set(false);
      },
      error: (err) => {
        this.errorMessage.set('Erro ao cadastrar moto. Verifique os dados.');
        this.isSubmitting.set(false);
      }
    });
  }
}
