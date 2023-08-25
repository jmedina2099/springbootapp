import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { FetchCountriesService } from 'src/app/services/fetch-countries/fetch-countries.service';
import { ModalService } from 'src/app/services/modal/modal.service';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { ModalComponent } from './components/modal/modal.component';
import { VentanaComponent } from './components/ventana/ventana.component';

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
  providers: [FetchCountriesService,ModalService,LoggerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
