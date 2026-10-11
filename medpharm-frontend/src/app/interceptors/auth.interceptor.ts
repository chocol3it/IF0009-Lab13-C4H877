import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn =
    (req, next) => {

        const tokenService = inject(AuthService);
        const token = tokenService.getToken();
        const authReq = token ? req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        }) : req;
        return next(authReq).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    console.error('Sesión no autorizada');
                }
                return throwError(() => error);
            })
        );
    };
