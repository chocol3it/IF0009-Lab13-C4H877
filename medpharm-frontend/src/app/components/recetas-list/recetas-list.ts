import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { RecetaService } from '../../services/receta.service';
@Component({
  imports: [RouterLink],
  selector: 'app-recetas-list',
  styleUrl: './recetas-list.css',
  templateUrl: './recetas-list.html',
})

export class RecetasListComponent implements OnInit {
    private recetaService = inject(RecetaService);

    recetas = signal<any[]>([]);

    filtroActual = signal<string>('TODAS');

    recetasFiltradas = computed(() => {
        const listaCompleta = this.recetas();
        const filtro = this.filtroActual();

        if (filtro === 'TODAS') {
            return listaCompleta;
        } else {
            return listaCompleta.filter(receta => receta.estado === filtro);
        }
    });

    ngOnInit() {
        this.recetaService.obtenerRecetas().subscribe({
            next: (datos) => this.recetas.set(datos)
        });
    }

    cambiarFiltro(nuevoFiltro: string) {
        this.filtroActual.set(nuevoFiltro);
    }

    cambiarEstado(id: number, nuevoEstado: string) {
        this.recetaService.actualizarEstado(id, nuevoEstado).subscribe({
            next: (actualizada) => this.recetas.update(lista =>
                lista.map(receta => receta.id === actualizada.id ? actualizada : receta))
        });
    }
}