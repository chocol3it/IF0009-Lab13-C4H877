import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { RecetasListComponent } from './components/recetas-list/recetas-list';
import { LoginComponent } from './components/login/login';
import { RecetaFormComponent } from './components/receta-form/receta-form';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'recetas', component: RecetasListComponent, canActivate: [authGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'nueva-receta', component: RecetaFormComponent, canActivate: [authGuard] },
    { path: '**', redirectTo: 'login' }
];
