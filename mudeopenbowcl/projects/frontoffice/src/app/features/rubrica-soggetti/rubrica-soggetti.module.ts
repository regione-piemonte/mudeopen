/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgxPaginationModule } from 'ngx-pagination';
import { MudeCommonModule, SharedModule } from 'mudeopen-common';
import { CardRubricaComponent } from './card-rubrica/card-rubrica.component';
import { RubricaSoggettiRoutingModule } from './rubrica-soggetti-routing.module';
import { VisualizzaRubricaComponent } from './visualizza-rubrica/visualizza-rubrica.component';


@NgModule({
  declarations: [CardRubricaComponent, VisualizzaRubricaComponent],
  imports: [
    NgxDatatableModule, 
    NgxPaginationModule,
    CommonModule, 
    FormsModule, 
    ReactiveFormsModule, 
    RubricaSoggettiRoutingModule, 
    SharedModule, //MudeCommonModule
  ]
})
export class RubricaSoggettiModule {}