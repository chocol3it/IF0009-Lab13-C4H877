import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class RecetaService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/api/v1/recetas';

    obtenerRecetas(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }

    crearReceta(receta: any): Observable<any> {
        return this.http.post<any>(this.apiUrl, receta);
    }

    actualizarEstado(id: number, nuevoEstado: string): Observable<any> {
        return this.http.patch<any>(`${this.apiUrl}/${id}/estado?nuevoEstado=${nuevoEstado}`, {});
    }
}