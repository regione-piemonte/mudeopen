/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormioModule } from '@formio/angular';
import { SharedModule } from 'mudeopen-common';
import { GestioneRoutingModule } from './gestione-routing.module';
import { CambioStatoComponent } from './istanze/cambio-stato/cambio-stato.component';
import { IstanzeComponent } from './istanze/istanze.component';


@NgModule({
  declarations: [
    IstanzeComponent,
    CambioStatoComponent
  ],
  imports: [ 
    CommonModule,
    FormsModule,
    GestioneRoutingModule,
    SharedModule, //MudeCommonModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
    FormioModule
  ],
  providers: [
  ],
  entryComponents: [
  ]
})
export class GestioneModule {}