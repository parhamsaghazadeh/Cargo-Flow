import { Component, OnDestroy, OnInit, signal } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, OnDestroy {
  protected readonly bars = [32, 48, 40, 62, 52, 74, 66, 84, 70, 92, 78, 88];

  protected readonly trucks = [
    { name: 'Volvo FH16', tagline: 'پرچم‌دار مسیرهای طولانی', src: '/Photo/truck-1.jpg' },
    { name: 'Mercedes Actros', tagline: 'حامل سردخانه‌ای', src: '/Photo/truck-2.jpg' },
    { name: 'Scania R-Series', tagline: 'ماهر در بارهای سنگین', src: '/Photo/truck-3.jpg' },
    { name: 'MAN TGX', tagline: 'اسب کار منطقه‌ای', src: '/Photo/truck-4.jpg' },
    { name: 'DAF XF', tagline: 'کارآمد در مسافت‌های طولانی', src: '/Photo/truck-5.jpg' },
    { name: 'Iveco S-Way', tagline: 'حامل چندمنظوره', src: '/Photo/truck-6.jpg' },
  ];

  protected readonly activeSlide = signal(0);
  private timer?: ReturnType<typeof setInterval>;

  ngOnInit(): void {
    this.timer = setInterval(() => {
      this.activeSlide.update((current) => (current + 1) % this.trucks.length);
    }, 5000);
  }

  ngOnDestroy(): void {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }

  protected readonly trend = [
    { label: 'شنبه', value: 40 },
    { label: 'یکشنبه', value: 55 },
    { label: 'دوشنبه', value: 48 },
    { label: 'سه‌شنبه', value: 68 },
    { label: 'چهارشنبه', value: 60 },
    { label: 'پنجشنبه', value: 82 },
    { label: 'جمعه', value: 74 },
  ];

  protected readonly activity = [
    { label: 'کاربران فعال', value: '1,284', pct: 72 },
    { label: 'ثبت‌نام جدید', value: '342', pct: 48 },
    { label: 'نرخ تبدیل', value: '12.4%', pct: 84 },
  ];

  protected readonly orders = [
    { label: 'امروز', value: 189, pct: 62 },
    { label: 'این هفته', value: '1,208', pct: 76 },
    { label: 'در انتظار', value: 46, pct: 38 },
  ];

  protected readonly drivers = [
    { name: 'علی رضایی', loads: 142, score: 98 },
    { name: 'مهدی اکبری', loads: 131, score: 95 },
    { name: 'رضا نادری', loads: 119, score: 92 },
    { name: 'سامان ایمانی', loads: 106, score: 90 },
    { name: 'تارا لارسن', loads: 98, score: 88 },
  ];

  protected readonly hubs = [
    { country: 'ایالات متحده', loads: 214, cx: 22, cy: 34 },
    { country: 'آلمان', loads: 128, cx: 47, cy: 38 },
    { country: 'برزیل', loads: 96, cx: 32, cy: 64 },
    { country: 'چین', loads: 158, cx: 71, cy: 40 },
    { country: 'استرالیا', loads: 74, cx: 84, cy: 66 },
    { country: 'هند', loads: 111, cx: 65, cy: 55 },
    { country: 'آفریقای جنوبی', loads: 62, cx: 54, cy: 70 },
  ];

  protected readonly heatLegend = [
    'bg-emerald-500/30',
    'bg-lime-400/40',
    'bg-yellow-400/50',
    'bg-orange-400/60',
    'bg-red-500/70',
  ];

  protected readonly heatmap = [
    { label: 'AM', class: 'bg-red-500/70' },
    { label: 'AM', class: 'bg-orange-400/60' },
    { label: 'AM', class: 'bg-yellow-400/50' },
    { label: 'AM', class: 'bg-lime-400/40' },
    { label: 'AM', class: 'bg-emerald-500/30' },
    { label: 'AM', class: 'bg-lime-400/40' },
    { label: 'AM', class: 'bg-yellow-400/50' },
    { label: 'PM', class: 'bg-orange-400/60' },
    { label: 'PM', class: 'bg-red-500/70' },
    { label: 'PM', class: 'bg-red-500/70' },
    { label: 'PM', class: 'bg-orange-400/60' },
    { label: 'PM', class: 'bg-yellow-400/50' },
    { label: 'PM', class: 'bg-lime-400/40' },
    { label: 'PM', class: 'bg-emerald-500/30' },
    { label: 'EV', class: 'bg-emerald-500/30' },
    { label: 'EV', class: 'bg-lime-400/40' },
    { label: 'EV', class: 'bg-yellow-400/50' },
    { label: 'EV', class: 'bg-orange-400/60' },
    { label: 'EV', class: 'bg-red-500/70' },
    { label: 'EV', class: 'bg-orange-400/60' },
    { label: 'EV', class: 'bg-yellow-400/50' },
  ];
}