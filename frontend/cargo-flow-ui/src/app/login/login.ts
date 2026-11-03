import { Component, inject, OnDestroy, signal } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnDestroy {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly mode = signal<'login' | 'signup'>('login');
  protected readonly showPassword = signal(false);
  protected readonly showConfirmPassword = signal(false);
  protected readonly submitting = signal(false);
  protected readonly errorMessage = signal<string | null>(null);
  protected readonly successMessage = signal<string | null>(null);

  protected readonly loginForm = this.fb.nonNullable.group({
    identifier: ['', Validators.required],
    password: ['', Validators.required],
  });

  protected readonly signupForm = this.fb.nonNullable.group(
    {
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.minLength(9)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
    },
    { validators: Login.mustMatch('password', 'confirmPassword') },
  );

  private timer?: ReturnType<typeof setTimeout>;

  ngOnDestroy(): void {
    if (this.timer) {
      clearTimeout(this.timer);
    }
  }

  protected togglePasswordVisibility(): void {
    this.showPassword.update((value) => !value);
  }

  protected toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword.update((value) => !value);
  }

  protected setMode(mode: 'login' | 'signup'): void {
    this.mode.set(mode);
    this.errorMessage.set(null);
    this.successMessage.set(null);
  }

  protected async onLogin(): Promise<void> {
    if (this.loginForm.invalid || this.submitting()) {
      return;
    }
    const identifier = this.loginForm.get('identifier')?.value ?? '';
    const password = this.loginForm.get('password')?.value ?? '';
    this.submitting.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);
    try {
      await this.authService.loginWithPassword(identifier, password);
      this.authService.setLoginSuccess();
      this.successMessage.set('ورود با موفقیت انجام شد');
      this.timer = setTimeout(() => this.router.navigate(['/']), 1200);
    } catch (error: unknown) {
      this.errorMessage.set(this.extractError(error));
    } finally {
      this.submitting.set(false);
    }
  }

  protected async onSignup(): Promise<void> {
    if (this.signupForm.invalid || this.submitting()) {
      return;
    }
    const values = this.signupForm.getRawValue();
    this.submitting.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);
    try {
      await this.authService.signup({
        username: values.username,
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        phone: values.phone,
        password: values.password,
        age: 0,
      });
      this.successMessage.set('ثبت‌نام با موفقیت انجام شد. اکنون وارد شوید.');
      this.setMode('login');
      this.loginForm.patchValue({ identifier: values.username });
    } catch (error: unknown) {
      this.errorMessage.set(this.extractError(error));
    } finally {
      this.submitting.set(false);
    }
  }

  private extractError(error: unknown): string {
    const e = error as { status?: number; error?: { message?: string } };
    if (typeof e?.error?.message === 'string' && e.error.message.length > 0) {
      return e.error.message;
    }
    if (error instanceof Error && /failed to fetch/i.test(error.message)) {
      return 'اتصال به سرور برقرار نشد. لطفاً دوباره تلاش کنید.';
    }
    return 'عملیات انجام نشد. لطفاً دوباره تلاش کنید.';
  }

  static mustMatch(controlName: string, matchingName: string) {
    return (group: AbstractControl): ValidationErrors | null => {
      const control = group.get(controlName);
      const matching = group.get(matchingName);
      if (!control || !matching) {
        return null;
      }
      if (control.value !== matching.value) {
        matching.setErrors({ mismatch: true });
        return { mismatch: true };
      }
      matching.setErrors(null);
      return null;
    };
  }
}