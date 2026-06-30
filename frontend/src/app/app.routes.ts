import { Routes } from '@angular/router';
import { guestGuard } from './core/guards/guest.guard';
import { PublicLayout } from './layout/public/public-layout/public-layout';

export const routes: Routes = [
    {
        path: '',
        component: PublicLayout,
        canActivate: [guestGuard],
        children: [
            {
                path: 'login',
                loadComponent: () => 
                    import('./features/auth/pages/login/login.component')
                        .then(c => c.LoginComponent)
            },
            {
                path: 'register',
                loadComponent: () => 
                    import('./features/auth/pages/register/register.component')
                        .then(c => c.RegisterComponent)
            }
        ]
    }
];