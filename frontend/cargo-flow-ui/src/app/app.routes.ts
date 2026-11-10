import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { Login } from './login/login';
import { CustomerGuard } from './auth/customer.guard';
import { DriverGuard } from './auth/driver.guard';
import { AuthGuard } from './auth/auth.guard';
import { CustomerDashboard } from './customer-dashboard/customer-dashboard';
import { DriverDashboard } from './driver-dashboard/driver-dashboard';
import { Profile } from './profile/profile';
import { Gateway } from './gateway/gateway';
import { CustomerNewOrder } from './pages/customer-pages/new-order/customer-new-order';
import { CustomerOrderTracking } from './pages/customer-pages/order-tracking/customer-order-tracking';
import { CustomerAddresses } from './pages/customer-pages/addresses/customer-addresses';
import { CustomerBilling } from './pages/customer-pages/billing/customer-billing';
import { DriverMarketplace } from './pages/driver-pages/marketplace/driver-marketplace';
import { DriverActiveTrip } from './pages/driver-pages/active-trip/driver-active-trip';
import { DriverReadyStatus } from './pages/driver-pages/ready-status/driver-ready-status';
import { DriverWallet } from './pages/driver-pages/wallet/driver-wallet';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: Dashboard },
  { path: 'login', component: Login },
  { path: 'dashboard', component: Dashboard },
  { path: 'business', component: Gateway },
  { path: 'gateway', component: Gateway },
  { path: 'customer-dashboard', component: CustomerDashboard, canActivate: [CustomerGuard] },
  { path: 'driver-dashboard', component: DriverDashboard, canActivate: [DriverGuard] },
  { path: 'profile', component: Profile, canActivate: [AuthGuard] },
  { path: 'customer/new-order', component: CustomerNewOrder, canActivate: [CustomerGuard] },
  { path: 'customer/order-tracking', component: CustomerOrderTracking, canActivate: [CustomerGuard] },
  { path: 'customer/addresses', component: CustomerAddresses, canActivate: [CustomerGuard] },
  { path: 'customer/billing', component: CustomerBilling, canActivate: [CustomerGuard] },
  { path: 'driver/marketplace', component: DriverMarketplace, canActivate: [DriverGuard] },
  { path: 'driver/active-trip', component: DriverActiveTrip, canActivate: [DriverGuard] },
  { path: 'driver/ready-status', component: DriverReadyStatus, canActivate: [DriverGuard] },
  { path: 'driver/wallet', component: DriverWallet, canActivate: [DriverGuard] },
];