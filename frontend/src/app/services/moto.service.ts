import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Moto } from '../models/moto.model';

/**
 * Serviço responsável pela comunicação com a API REST de Motos.
 * Utiliza o padrão singleton @Injectable.
 */
@Injectable({
  providedIn: 'root'
})
export class MotoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/motos';

  /**
   * Método para cadastrar uma nova moto.
   * @param moto Objeto Moto com os dados preenchidos.
   * @returns Observable da Moto cadastrada retornada pela API.
   */
  cadastrar(moto: Moto): Observable<Moto> {
    return this.http.post<Moto>(this.apiUrl, moto);
  }

  /**
   * Método para listar todas as motos cadastradas.
   * @returns Observable da lista de motos.
   */
  listar(): Observable<Moto[]> {
    return this.http.get<Moto[]>(this.apiUrl);
  }
}
