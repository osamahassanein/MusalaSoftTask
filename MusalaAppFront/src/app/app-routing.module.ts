import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { DeviceComponent } from './components/device/device.component';
import { GatewayComponent } from './components/gateway/gateway.component';
import { SpecificGatewayComponent } from './components/specific-gateway/specific-gateway.component';

const routes: Routes = [
  
  {
  // path: '',
  // component: AppComponent,
  // children: [
  //   { 
      path: '', redirectTo: 'gateway', pathMatch: 'full' },
      {
        path: 'gateway',
        component: GatewayComponent
      },
    { path: 'gateway/:id', component: SpecificGatewayComponent },
    { path: 'gateway/:id/devices', component: DeviceComponent }
  // ]
// }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
