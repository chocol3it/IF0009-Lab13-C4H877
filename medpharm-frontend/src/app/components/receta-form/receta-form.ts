import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RecetaService } from '../../services/receta.service';

export function positivoValidator(control: AbstractControl): ValidationErrors | null {
  const valor = control.value;
  if (valor === null || valor === '') {
    return null;
  }
  if (!Number.isInteger(Number(valor)) || valor <= 0) {
    return { cantidadInvalida: true };
  }
  return null;
}

@Component({
  selector: 'app-receta-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './receta-form.html',
  styleUrls: ['./receta-form.css']
})
export class RecetaFormComponent {
  recetaForm: FormGroup;

  private fb = inject(FormBuilder);
  private recetaService = inject(RecetaService);
  private router = inject(Router);

  constructor() {
    this.recetaForm = this.fb.group({
      pacienteNombre: ['', [Validators.required, Validators.minLength(5)]],
      detalles: this.fb.array([
        this.crearRenglonMedicamento()
      ])
    });
  }

  get detalles(): FormArray {
    return this.recetaForm.get('detalles') as FormArray;
  }

  crearRenglonMedicamento(): FormGroup {
    return this.fb.group({
      medicamento: this.fb.group({
        id: ['', Validators.required]
      }),
      cantidad: ['', [Validators.required, positivoValidator]],
      dosisIndicada: ['', Validators.required]
    });
  }

  agregarMedicamento() {
    this.detalles.push(this.crearRenglonMedicamento());
  }

  removerMedicamento(index: number) {
    if (this.detalles.length > 1) {
      this.detalles.removeAt(index);
    }
  }

  onSubmit() {
    if (this.recetaForm.valid) {
      this.recetaService.crearReceta(this.recetaForm.value).subscribe({
        next: () => {
          alert('Receta creada con éxito');
          this.router.navigate(['/recetas']);
        },
        error: (err) => {
          console.error('Error al crear la receta:', err);
          alert('Error: ' + (err.error?.detail ?? 'no se pudo crear la receta'));
        }
      });
    }
  }
}