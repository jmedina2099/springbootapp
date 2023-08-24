import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Country } from '../country/country';

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

  updatePais(paisEditar: Country) {
    return this.http.put<Country[]>(this.configUrl + "/modify/"+paisEditar.id, paisEditar );
  }

}
