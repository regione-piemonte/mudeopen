/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { PpayRoutingModule } from './ppay-routing.module';
import { PpayComponent } from './ppay.component';

import { AppSharedModule } from '../shared/appshared.module';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'mudeopen-common';

@NgModule({
  declarations: [PpayComponent],
  imports: [
    AppSharedModule,
    CommonModule, 
    SharedModule,
    PpayRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PpayModule {}
