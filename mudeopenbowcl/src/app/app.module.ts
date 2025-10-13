/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/


declare var $$: any;



import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouteReuseStrategy, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { environment } from 'buildfiles/environment.local-rp-01';
import { SidebarModule } from 'ng-sidebar';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';

import {
  AuthStoreService,
  MudeCommonModule,
  CustomReuseStrategy,
  BASE_PATH
} from 'mudeopen-common';
import { SharedBoModule } from './shared/shared-bo.module';
import { DettaglioIstanzaComponent } from './features/istanze/components/dettaglio-istanza/dettaglio-istanza.component';
import { InvioNotificaComponent } from './features/istanze/components/invio-notifica/invio-notifica.component';
import { DettaglioNuovaNotificaComponent } from './features/istanze/components/invio-notifica/dettaglio-nuova-notifica/dettaglio-nuova-notifica.component';
import { AppModuleFO } from '../../projects/frontoffice/src/app/app.module';
import { NuovoFascicoloModule } from '../../projects/frontoffice/src/app/features/fascicoli/fascicoli.module';


export function initializeServices(auth: AuthStoreService): Function {
  return () => auth.checkAuthentication();
}

@NgModule({
  declarations: [
    AppComponent,
   
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,

    HttpClientModule,
    MudeCommonModule,
    CoreModule,
    AppRoutingModule,
    FormsModule,
    SharedBoModule,

    SidebarModule.forRoot(),
    AppModuleFO.forRoot(),
    NuovoFascicoloModule.forRoot(),
  ],
  // entryComponents: [AppComponent],
  providers: [
    AuthStoreService,
    { provide: BASE_PATH, useValue: environment.basePath },
    { provide: APP_INITIALIZER, useFactory: initializeServices, deps: [AuthStoreService], multi: true },
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy}

  ],
  bootstrap: [AppComponent],
})
export class AppModuleBO {}
