/**
 * Interface representando o modelo de dados de uma Moto no Frontend.
 */
export interface Moto {
    id?: number;
    modelo: string;
    ano: number;
    km: number;
    placa?: string;
    vin?: string;
}
