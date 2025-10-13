/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { GeecoRoutingModule } from './geeco-routing.module';
import { GeecoComponent } from './geeco.component';

import { AppSharedModule } from '../shared/appshared.module';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'mudeopen-common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

@NgModule({
  declarations: [GeecoComponent],
  imports: [
    AppSharedModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule, 
    SharedModule,
    GeecoRoutingModule,
    NgxDatatableModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class GeecoModule {}
