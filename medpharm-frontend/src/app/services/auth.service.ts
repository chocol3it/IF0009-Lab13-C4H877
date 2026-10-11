import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/api/v1/auth/login';

    public isLoggedIn = signal<boolean>(false);

    constructor(private http: HttpClient) {

        if (this.getToken()) {
            this.isLoggedIn.set(true);
        }
    }

    login(credentials: any): Observable<any> {
        return this.http.post<any>(this.apiUrl, credentials).pipe(
            tap(response => {
                localStorage.setItem('token', response.token);

                this.isLoggedIn.set(true);
            })
        );
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    logout(): void {
        localStorage.removeItem('token');
        this.isLoggedIn.set(false);
    }
}