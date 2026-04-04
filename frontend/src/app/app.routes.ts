import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login.component';
import { RegisterComponent } from './components/auth/register.component';
import { MotoListComponent } from './components/moto-list/moto-list.component';
import { MotoFormComponent } from './components/moto-form/moto-form.component';
import { MotoServiceHistoryComponent } from './components/moto-service-history/moto-service-history.component';
import { MotoServiceFormComponent } from './components/moto-service-form/moto-service-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'garage', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'garage', component: MotoListComponent },
  { path: 'garage/new', component: MotoFormComponent },
  { path: 'garage/:id/services', component: MotoServiceHistoryComponent },
  { path: 'garage/:id/services/new', component: MotoServiceFormComponent }
];
