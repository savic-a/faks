import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dog } from './dog.model';

@Injectable({
    providedIn: 'root',
})
export class DogsService {
    constructor(private http: HttpClient) { }

    private apiUrl = 'https://phrqd2he83.execute-api.eu-central-1.amazonaws.com';
    private stagePath = '/dev';
    private resourcePath = '/dogs';

    private url = this.apiUrl + this.stagePath + this.resourcePath;

    getDogs(): Observable<Dog[]> {
        return this.http.get<Dog[]>(this.url);
    }

    addDog(dog: Dog): Observable<Dog> {
        return this.http.post<Dog>(this.url, dog)
    }
    
    getDog(name: string): Observable<Dog> {
        return this.http.get<Dog>(this.url + '/' + name)
    }

    removeDog(name: string): Observable<any> {
        return this.http.delete<any>(this.url + '/' + name)
    }
}