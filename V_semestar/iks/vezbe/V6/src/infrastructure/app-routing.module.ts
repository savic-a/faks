import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateWineComponent } from 'src/app/components/create-wine/create-wine.component';
import { HomeComponent } from 'src/app/components/home/home.component';
import { WineComponent } from '../app/components/wine/wine.component';

const routes: Routes = [
  { path: 'wine', component: WineComponent },
  { path: 'home', component: HomeComponent },
  { path: 'create', component: CreateWineComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
