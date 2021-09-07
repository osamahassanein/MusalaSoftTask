import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { GatewayComponent } from './components/gateway/gateway.component';
import { SpecificGatewayComponent } from './components/specific-gateway/specific-gateway.component';
import { GatewayformComponent } from './components/gatewayform/gatewayform.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DeviceComponent } from './components/device/device.component';
import { DeviceformComponent } from './components/deviceform/deviceform.component';

@NgModule({
  declarations: [
    AppComponent,
    GatewayComponent,
    SpecificGatewayComponent,
    GatewayformComponent,
    DeviceComponent,
    DeviceformComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
