import { Routes } from '@angular/router';
import { AuthSelectionComponent } from './auth-selection/auth-selection.component';
import { CustomerAuthComponent } from './customer-auth/customer-auth.component';
import { VendorAuthComponent } from './vendor-auth/vendor-auth.component';
import { AdminSignInComponent } from './admin-sign-in/admin-sign-in.component';
import { CustomerSignInComponent } from './customer-sign-in/customer-sign-in.component';
import { CustomerSignUpComponent } from './customer-sign-up/customer-sign-up.component';
import { VendorSignInComponent } from './vendor-sign-in/vendor-sign-in.component';
import { VendorSignUpComponent } from './vendor-sign-up/vendor-sign-up.component';

export const routes: Routes = [
  { path: '', component: AuthSelectionComponent },
  { path: 'customer-auth', component: CustomerAuthComponent },
  { path: 'vendor-auth', component: VendorAuthComponent },
  { path: 'admin-sign-in', component: AdminSignInComponent },
  { path: 'customer-sign-in', component: CustomerSignInComponent },
  { path: 'customer-sign-up', component: CustomerSignUpComponent },
  { path: 'vendor-sign-in', component: VendorSignInComponent },
  { path: 'vendor-sign-up', component: VendorSignUpComponent },
];