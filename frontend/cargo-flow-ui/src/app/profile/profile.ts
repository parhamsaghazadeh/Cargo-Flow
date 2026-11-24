import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, UserProfile } from '../auth/auth.service';
import { JalaliDatePicker } from '../shared/jalali-date-picker/jalali-date-picker';

interface StatMetric {
  label: string;
  value: string;
  sublabel: string;
  pct: number;
  accent: string;
}

interface MemberMetric {
  label: string;
  value: string;
  icon: string;
}

@Component({
  selector: 'app-profile',
  imports: [CommonModule, ReactiveFormsModule, JalaliDatePicker],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  protected readonly authService = inject(AuthService);

  protected readonly profile = signal<UserProfile | null>(null);
  protected readonly avatarUrl = signal<string | null>(null);
  protected readonly saved = signal(false);
  protected readonly saving = signal(false);
  protected readonly saveError = signal<string | null>(null);
  protected readonly stats = signal<StatMetric[]>([]);
  protected readonly statsTitle = signal('آمار عملکرد');
  protected readonly isCustomer = signal(false);
  protected readonly isDriver = signal(false);
  protected readonly memberStats = signal<MemberMetric[]>([]);
  protected readonly memberSince = signal('—');
  protected readonly showDeleteModal = signal(false);
  protected readonly deleteError = signal<string | null>(null);
  protected readonly deleting = signal(false);
  protected readonly deletePasswordControl = new FormControl('', Validators.required);

  protected readonly customerStats: StatMetric[] = [
    { label: 'تعداد کل سفارشات', value: '۱۲۸', sublabel: 'سفارش فعال در این ماه', pct: 72, accent: 'from-slate-400 to-slate-200' },
    { label: 'نرخ استفاده / فعالیت', value: '۸۴٪', sublabel: 'میانگین استفاده هفتگی اپ', pct: 84, accent: 'from-slate-400 to-slate-300' },
    { label: 'برترین مقاصد ارسال', value: 'تهران، اصفهان، مشهد', sublabel: '۳ مقصد پرتکرار', pct: 68, accent: 'from-slate-400 to-slate-300' },
  ];

  protected readonly driverStats: StatMetric[] = [
    { label: 'تعداد کل سفارشات تحویل‌شده', value: '۱٬۰۴۲', sublabel: 'تحویل موفق این ماه', pct: 78, accent: 'from-slate-400 to-slate-200' },
    { label: 'ساعات رانندگی در جاده', value: '۳۲۶ ساعت', sublabel: 'در ۳۰ روز گذشته', pct: 64, accent: 'from-slate-400 to-slate-300' },
    { label: 'تعداد مکان‌های تحویل', value: '۵۸۶', sublabel: 'مقصدهای منحصربه‌فرد', pct: 71, accent: 'from-slate-400 to-slate-300' },
  ];

  protected readonly form = this.fb.nonNullable.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    phone: ['', Validators.required],
    dateOfBirth: [''],
    address: [''],
    email: ['', [Validators.required, Validators.email]],
  });

  ngOnInit(): void {
    void this.initProfile();
  }

  private async initProfile(): Promise<void> {
    try {
      await this.authService.init();
    } catch {
      return;
    }

    let p = this.authService.getProfile();
    try {
      const me = await this.authService.getMe();
      if (me) {
        p = me;
      }
    } catch (error) {
      console.error('Failed to load profile from backend:', error);
    }

    this.profile.set(p);
    if (p) {
      this.form.patchValue({
        firstName: p.firstName,
        lastName: p.lastName,
        phone: p.phone,
        email: p.email,
        dateOfBirth: p.dateOfBirth ?? '',
        address: p.address ?? '',
      });
      const roles = p.roles ?? [];
      this.isCustomer.set(roles.includes('CUSTOMER'));
      this.isDriver.set(roles.includes('DRIVER'));
      if (this.isCustomer()) {
        this.statsTitle.set('آمار مشتری');
        this.stats.set(this.customerStats);
      } else if (this.isDriver()) {
        this.statsTitle.set('آمار راننده');
        this.stats.set(this.driverStats);
      }
      this.buildMemberStats();
    }
    this.avatarUrl.set(this.authService.avatarPicture ?? null);
  }

  private buildMemberStats(): void {
    const p = this.profile();
    if (!p) {
      return;
    }
    const roleLabel = this.isCustomer()
      ? 'مشتری'
      : this.isDriver()
        ? 'راننده'
        : 'کاربر عمومی';
    this.memberSince.set(roleLabel);

    const base: MemberMetric[] = [
      { label: 'عضویت از', value: roleLabel, icon: 'calendar' },
      { label: 'نام کاربری', value: p.username || '—', icon: 'user' },
      { label: 'کل سفارشات', value: this.isCustomer() ? '۱۲۸' : this.isDriver() ? '۱٬۰۴۲' : '—', icon: 'box' },
      { label: 'آخرین مرسوله', value: this.isCustomer() ? '۱۴۰۴/۰۷/۱۲' : this.isDriver() ? '۱۴۰۴/۰۷/۱۰' : '—', icon: 'truck' },
      { label: 'امتیاز عملکرد', value: this.isCustomer() ? '۴.۸ از ۵' : this.isDriver() ? '۹۴٪' : '—', icon: 'star' },
    ];
    this.memberStats.set(base);
  }

  get initials(): string {
    const p = this.profile();
    if (!p) {
      return 'OP';
    }
    const first = p.firstName?.charAt(0) ?? '';
    const last = p.lastName?.charAt(0) ?? '';
    return (first + last).toUpperCase() || p.username?.slice(0, 2).toUpperCase() || 'OP';
  }

  onAvatarClick(): void {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*';
    input.addEventListener('change', () => this.handleAvatarFile(input));
    input.click();
  }

  private handleAvatarFile(input: HTMLInputElement): void {
    const file = input.files?.[0];
    if (!file) {
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      this.avatarUrl.set(typeof reader.result === 'string' ? reader.result : null);
    };
    reader.readAsDataURL(file);
  }

  onSave(): void {
    if (this.form.invalid || this.saving()) {
      return;
    }
    const values = this.form.getRawValue();
    const current = this.profile();
    if (!current) {
      return;
    }

    this.saving.set(true);
    this.saveError.set(null);
    this.saved.set(false);

    const updated: UserProfile = {
      ...current,
      id: current.id,
      firstName: values.firstName,
      lastName: values.lastName,
      phone: values.phone,
      email: values.email,
      dateOfBirth: values.dateOfBirth || null,
      address: values.address,
    };

    void this.authService
      .updateProfile(updated)
      .then(() => {
        this.profile.set(updated);
        this.saved.set(true);
        setTimeout(() => this.saved.set(false), 3000);
      })
      .catch((error: unknown) => {
        console.error('Profile save failed:', error);
        this.saveError.set('ذخیره تغییرات انجام نشد. لطفاً دوباره تلاش کنید.');
      })
      .finally(() => {
        this.saving.set(false);
      });
  }

  onDiscard(): void {
    const p = this.profile();
    if (p) {
      this.form.patchValue({
        firstName: p.firstName,
        lastName: p.lastName,
        phone: p.phone,
        email: p.email,
        dateOfBirth: p.dateOfBirth ?? '',
        address: p.address ?? '',
      });
    }
    this.form.markAsPristine();
    this.saveError.set(null);
    this.saved.set(false);
  }

  get fullName(): string {
    const p = this.profile();
    return p ? `${p.firstName} ${p.lastName}`.trim() || p.username : 'کاربر';
  }

  onLogout(): void {
    void this.authService.logout().finally(() => {
      this.router.navigate(['/login']);
    });
  }

  openDeleteModal(): void {
    this.deletePasswordControl.setValue('');
    this.deleteError.set(null);
    this.showDeleteModal.set(true);
  }

  closeDeleteModal(): void {
    if (this.deleting()) {
      return;
    }
    this.showDeleteModal.set(false);
    this.deletePasswordControl.setValue('');
    this.deleteError.set(null);
  }

  async onDeleteAccount(): Promise<void> {
    const password = this.deletePasswordControl.value ?? '';
    if (!password || this.deleting()) {
      return;
    }
    this.deleting.set(true);
    this.deleteError.set(null);
    try {
      await this.authService.deleteAccount(password);
      this.showDeleteModal.set(false);
      this.authService.clearAuth();
      await this.router.navigate(['/login']);
    } catch (error: unknown) {
      this.deleteError.set(this.extractDeleteError(error));
    } finally {
      this.deleting.set(false);
    }
  }

  private extractDeleteError(error: unknown): string {
    const e = error as { status?: number; error?: { message?: string } };
    if (e?.status === 401 || e?.status === 400) {
      return 'رمز عبور فعلی صحیح نیست. لطفاً دوباره تلاش کنید.';
    }
    if (typeof e?.error?.message === 'string' && e.error.message.length > 0) {
      return e.error.message;
    }
    if (error instanceof Error && /failed to fetch/i.test(error.message)) {
      return 'اتصال به سرور برقرار نشد. لطفاً دوباره تلاش کنید.';
    }
    return 'حذف حساب انجام نشد. لطفاً دوباره تلاش کنید.';
  }
}