import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-login',
  styleUrl: './login.css',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
})

export class LoginComponent {
    loginForm: FormGroup;

    private fb = inject(FormBuilder);
    private authService = inject(AuthService);
    private router = inject(Router);

    constructor() {
        this.loginForm = this.fb.group({
            username: ['', [Validators.required]],
            password: ['', [Validators.required, Validators.minLength(6)]]
        });
    }

    onSubmit() {
        if (this.loginForm.valid) {

            this.authService.login(this.loginForm.value).subscribe({
                next: () => {
                    this.router.navigate(['/recetas']);
                },
                error: (err) => alert('Credenciales incorrectas')
            });

        }
    }
}