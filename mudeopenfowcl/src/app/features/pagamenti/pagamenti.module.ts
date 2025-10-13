/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MudeCommonModule, SharedModule } from 'mudeopen-common';
import { PagamentiRoutingModule } from './pagamenti-routing.module';
import { PagamentiComponent } from './pagamenti.component';

@NgModule({
  declarations: [PagamentiComponent],
  imports: [
    CommonModule, 
    SharedModule, //MudeCommonModule, 
    PagamentiRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PagamentiModule {}
