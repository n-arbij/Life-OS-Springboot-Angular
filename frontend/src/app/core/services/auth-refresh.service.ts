import { inject, Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { catchError, filter, finalize, Observable, ReplaySubject, Subject, switchMap, take, tap, throwError } from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthRefreshService {
    private authService = inject(AuthService);
    private refreshing = false;
    private refreshSubject = new ReplaySubject<string>(1);

    refresh(
        retry: (token: string) => Observable<any>
    ): Observable<any> {
        
        if(this.refreshing) {
            return this.refreshSubject.pipe(
                filter(token => !!token),
                take(1),
                switchMap(token => retry(token))
            );
        }

        this.refreshing = true;

        return this.authService.refreshToken().pipe(
            tap(response => {
                this.refreshSubject.next(response.token);
            }),

            switchMap(response => retry(response.token)),

            catchError(error => {
                this.authService.logout();

                return throwError(() => error);
            }),

            finalize(() => {
                this.refreshing = false;
            })
        );
    }
}