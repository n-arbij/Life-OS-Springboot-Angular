import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http"
import { inject } from "@angular/core"
import { AuthService } from "../services/auth.service"
import { AuthRefreshService } from "../services/auth-refresh.service"
import { catchError, throwError } from "rxjs"
import { RETRY_REQUEST } from "./retry-context"

const AUTH_ENDPOINTS = [
    'auth/login',
    'auth/register',
    'auth/refresh'
]

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const refreshService = inject(AuthRefreshService);
    const isAuthenticationRequest = AUTH_ENDPOINTS.some(endpoint => 
        req.url.includes(endpoint)
    );

    if(isAuthenticationRequest) {
        return next(req);
    }

    const token = authService.getAccessToken();

    if (token && authService.isAccessTokenExpired()) {

        return refreshService.refresh(newToken => {

            const retryRequest = req.clone({
                context: req.context.set(RETRY_REQUEST, true),
                setHeaders: {
                    Authorization: `Bearer ${newToken}`
                }
            });

            return next(retryRequest);

        });

    }

    const request = token
        ? req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        })
    : req;

    return next(request).pipe(
        catchError((error: HttpErrorResponse) => {

            if(error.status !== 401) {
                return throwError(() => error);
            }

            if(req.context.get(RETRY_REQUEST)){
                authService.logout();

                return throwError(() => error);
            }
            
            return refreshService.refresh(newToken => {
                const retryRequest = req.clone({
                    context: req.context.set(RETRY_REQUEST, true),
                    setHeaders: {
                        Authorization: `Bearer ${newToken}`
                    }
                });

                return next(retryRequest);
            });
        })
    );
}