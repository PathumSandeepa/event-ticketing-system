import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ControlPanelComponent } from './control-panel/control-panel.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'control-panel', component: ControlPanelComponent }
];
