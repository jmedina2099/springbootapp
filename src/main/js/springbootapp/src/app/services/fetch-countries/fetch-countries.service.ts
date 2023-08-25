import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Country } from 'src/app/entities/country/country';

@Injectable({
  providedIn: 'root'
})
export class FetchCountriesService {

  private configUrl: string = environment.backEndUrl + environment.contextPath;

  constructor(private http: HttpClient) {
  }

  fetchCountries() {
    return this.http.get<Country[]>(this.configUrl + "/find" );
  }

  createPais(paisEditar: Country) {
    return this.http.post<boolean>(this.configUrl + "/create", paisEditar );
  }

  updatePais(paisEditar: Country) {
    return this.http.put<boolean>(this.configUrl + "/modify/"+paisEditar.id, paisEditar );
  }

  deletePais(paisBorrar: Country) {
    return this.http.delete<boolean>(this.configUrl + "/delete/"+paisBorrar.id );
  }

}