import { Component, signal, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService, UserProfile } from './auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App implements OnInit, OnDestroy {
  protected readonly showShell = signal(true);
  protected readonly authService = inject(AuthService);
  protected readonly profileOpen = signal(false);
  protected readonly profile = signal<UserProfile | null>(null);
  protected readonly isAuthed = signal(false);

  protected readonly navItems: Array<{
    label: string;
    link: string;
    icon: string;
    roles?: string[];
  }> = [
    { label: 'داشبورد', link: '/dashboard', icon: 'dashboard' },
    { label: 'مرسوله‌ها', link: '/shipments', icon: 'shipments', roles: ['CUSTOMER'] },
    { label: 'ناوگان', link: '/fleet', icon: 'fleet' },
    { label: 'تحویل‌ها', link: '/deliveries', icon: 'deliveries' },
    { label: 'هاب‌ها', link: '/hubs', icon: 'hubs' },
    { label: 'ثبت سفارش جدید', link: '/customer/new-order', icon: 'new-order', roles: ['CUSTOMER'] },
    { label: 'مدیریت و پیگیری سفارش‌ها', link: '/customer/order-tracking', icon: 'tracking', roles: ['CUSTOMER'] },
    { label: 'مدیریت آدرس‌های پرکاربرد', link: '/customer/addresses', icon: 'addresses', roles: ['CUSTOMER'] },
    { label: 'صورت‌حساب و پرداخت‌ها', link: '/customer/billing', icon: 'billing', roles: ['CUSTOMER'] },
    { label: 'بازارچه بارهای موجود', link: '/driver/marketplace', icon: 'marketplace', roles: ['DRIVER'] },
    { label: 'سفر فعال و نقشه', link: '/driver/active-trip', icon: 'active-trip', roles: ['DRIVER'] },
    { label: 'وضعیت آماده‌به‌کار', link: '/driver/ready-status', icon: 'ready-status', roles: ['DRIVER'] },
    { label: 'کیف پول و درآمدها', link: '/driver/wallet', icon: 'wallet', roles: ['DRIVER'] },
  ];

  private subscription?: { unsubscribe: () => void };

  constructor(private readonly router: Router) {}

  ngOnInit(): void {
    this.updateShell();
    this.subscription = this.router.events.subscribe(() => this.updateShell());
    void this.refreshAuthState();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  private async refreshAuthState(): Promise<void> {
    const authenticated = await this.authService.init();
    this.isAuthed.set(authenticated);
    this.profile.set(authenticated ? this.authService.getProfile() : null);
  }

  private updateShell(): void {
    const url = this.router.url.split('?')[0];
    this.showShell.set(url !== '/login' && url !== '/business' && url !== '/gateway');
  }

  get profileInitials(): string {
    const p = this.profile();
    if (!p) {
      return 'OP';
    }
    const first = p.firstName?.charAt(0) ?? '';
    const last = p.lastName?.charAt(0) ?? '';
    return (first + last) || p.username?.slice(0, 2).toUpperCase() || 'OP';
  }

  toggleProfile(): void {
    this.profileOpen.update((value) => !value);
  }

  onProfileClick(): void {
    if (!this.authService.isAuthenticated) {
      this.router.navigate(['/login']);
      return;
    }
    this.router.navigate(['/profile']);
  }

  logout(): void {
    this.profileOpen.set(false);
    void this.authService.logout().finally(() => {
      this.isAuthed.set(false);
      this.profile.set(null);
      this.router.navigate(['/login']);
    });
  }
}