import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { Country } from '../common/country';
import { State } from '../common/state';

@Injectable({
  providedIn: 'root',
})
export class FormService {

  private countriesUrl: string = "http://localhost:8080/api/countries?page=0&size=250"
  private statesUrl: string = "http://localhost:8080/api/states"

  constructor(private httpClient: HttpClient) { }

  getCreditCardMonths(startmonth: number): Observable<number[]> {
    let data: number[] = [];
    for (let i = startmonth; i <= 12; i++) {
      data.push(i);
    }
    return of(data);
  }

  getCreditCardYears(): Observable<number[]> {
    let data: number[] = [];
    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 10;
    for (let i = startYear; i <= endYear; i++) {
      data.push(i);
    }
    return of(data);
  }

  getCountries(): Observable<Country[]> {
    return this.httpClient.get<GetResponseCountries>(this.countriesUrl).pipe(
      map(response => response._embedded.countries))
  }

  getStates(countryCode: string): Observable<State[]> {

    const searchUrl = `${this.statesUrl}/search/findByCountryCode?code=${countryCode}`;
    return this.httpClient.get<GetResponseStates>(searchUrl).pipe(
      map(response => response._embedded.states))
  }
}

interface GetResponseCountries {
  _embedded: {
    countries: Country[];
  }
}

interface GetResponseStates {
  _embedded: {
    states: State[];
  }
}
