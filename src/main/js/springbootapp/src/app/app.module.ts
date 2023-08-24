import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VentanaComponent } from './ventana/ventana.component';
import { FetchCountriesService } from './fetch-countries/fetch-countries.service';
import { HttpClientModule } from '@angular/common/http';
import { ModalService } from './modal-service/modal-service.service';
import { ModalComponent } from './modal/modal.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    VentanaComponent,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [FetchCountriesService,ModalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
