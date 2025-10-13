/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from 'mudeopen-common';
import { AppSharedModule } from '../../shared/appshared.module';
import { AccreditamentoRoutingModule } from './accreditamento-routing.module';
import { MainComponent } from './pages/main.component';

@NgModule({
  declarations: [MainComponent],
  imports: [
    AppSharedModule,
    CommonModule, 
    SharedModule,
    AccreditamentoRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AccreditamentoModule {}
