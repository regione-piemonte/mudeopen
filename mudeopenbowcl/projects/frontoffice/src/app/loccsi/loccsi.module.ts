/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { LoccsiRoutingModule } from './loccsi-routing.module';
import { LoccsiComponent } from './loccsi.component';

import { AppSharedModule } from '../shared/appshared.module';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'mudeopen-common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

@NgModule({
  declarations: [LoccsiComponent],
  imports: [
    AppSharedModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule, 
    SharedModule,
    NgxDatatableModule,
    LoccsiRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class LoccsiModule {}
