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

  protected readonly navItems = [
    { label: 'داشبورد', link: '/dashboard', icon: 'dashboard', customerOnly: false },
    { label: 'مرسوله‌ها', link: '/shipments', icon: 'shipments', customerOnly: true },
    { label: 'ناوگان', link: '/fleet', icon: 'fleet', customerOnly: false },
    { label: 'تحویل‌ها', link: '/deliveries', icon: 'deliveries', customerOnly: false },
    { label: 'هاب‌ها', link: '/hubs', icon: 'hubs', customerOnly: false },
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
    this.showShell.set(url !== '/login');
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
    if (!this.isAuthed()) {
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