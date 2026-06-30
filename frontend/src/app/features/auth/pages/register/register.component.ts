import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
  AbstractControl,
  ValidationErrors
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from '../../../../core/services/auth.service';
import { RegisterRequest } from '../../../../core/models/auth.model';

function passwordsMatch(control: AbstractControl): ValidationErrors | null {

  const password = control.get('password')?.value;
  const confirmPassword = control.get('confirmPassword')?.value;

  return password === confirmPassword
    ? null
    : { passwordMismatch: true };

}

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loading = false;

  showPassword = false;
  showConfirmPassword = false;

  registerForm = this.fb.group({

    firstName: ['', Validators.required],

    lastName: ['', Validators.required],

    username: ['', Validators.required],

    email: ['', [Validators.required, Validators.email]],

    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8)
      ]
    ],

    confirmPassword: ['', Validators.required],

    acceptTerms: [false, Validators.requiredTrue]

  }, {
    validators: passwordsMatch
  });

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  register() {

    if (this.registerForm.invalid) {

      this.registerForm.markAllAsTouched();

      return;

    }

    this.loading = true;

    const request: RegisterRequest = {

      firstName: this.registerForm.value.firstName!,

      lastName: this.registerForm.value.lastName!,

      username: this.registerForm.value.username!,

      email: this.registerForm.value.email!,

      password: this.registerForm.value.password!,

      timezone: Intl.DateTimeFormat().resolvedOptions().timeZone

    };

    this.authService.register(request)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({

        next: () => {

          this.router.navigate(['/login'], {

            queryParams: {

              registered: true

            }

          });

        },

        error: error => {

          console.error(error);

        }

      });

  }

}