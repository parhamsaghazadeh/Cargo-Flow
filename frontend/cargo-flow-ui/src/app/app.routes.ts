import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { Login } from './login/login';
import { CustomerGuard } from './auth/customer.guard';
import { DriverGuard } from './auth/driver.guard';
import { AuthGuard } from './auth/auth.guard';
import { CustomerDashboard } from './customer-dashboard/customer-dashboard';
import { DriverDashboard } from './driver-dashboard/driver-dashboard';
import { Profile } from './profile/profile';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'login', component: Login },
  { path: 'dashboard', component: Dashboard },
  { path: 'customer-dashboard', component: CustomerDashboard, canActivate: [CustomerGuard] },
  { path: 'driver-dashboard', component: DriverDashboard, canActivate: [DriverGuard] },
  { path: 'profile', component: Profile, canActivate: [AuthGuard] },
];