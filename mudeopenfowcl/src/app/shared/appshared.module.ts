/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/


import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { NgModule } from '@angular/core';
import * as comps from './components';
import { SharedModule } from 'mudeopen-common';

const sharedComponents = [
  comps.InserisciPersonaFisicaComponent,
  comps.InserisciPersonaGiuridicaComponent,
  comps.RicercaInserisciPersonaFisicaComponent,
  comps.RicercaInserisciPersonaFisicaGiuridicaComponent,
  comps.RicercaInserisciPersonaComponent,
  comps.RicercaPersonaFisicaComponent,
  comps.RicercaPersonaGiuridicaComponent,
  comps.InserisciContattoComponent,
  comps.HtmlContainerComponent
];


@NgModule({
  imports: [
    SharedModule,
    CommonModule,
    FormsModule, 
    ReactiveFormsModule,
    NgbModule,
    NgxDatatableModule
  ],
  declarations: [
    sharedComponents,
  ],
  providers: [
  ],
  exports: [
	  sharedComponents,  
  ],
  entryComponents: [ // Per aprire i componenti dinamicamente.
    comps.InserisciPersonaFisicaComponent,
    comps.InserisciPersonaGiuridicaComponent,
    comps.RicercaInserisciPersonaFisicaComponent,
    comps.RicercaInserisciPersonaFisicaGiuridicaComponent,
    comps.RicercaInserisciPersonaComponent,
  ]
})
export class AppSharedModule {}
