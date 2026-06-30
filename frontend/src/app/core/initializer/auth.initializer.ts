import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { firstValueFrom } from "rxjs";

export function initializeAuth(): Promise<void> {
    const authService = inject(AuthService);

    return firstValueFrom(
        authService.initialize()
    );
}