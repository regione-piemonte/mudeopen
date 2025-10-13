/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MudeCommonModule, SharedModule } from 'mudeopen-common';
import { ImpostazioniRoutingModule } from './impostazioni-routing.module';
import { ImpostazioniComponent } from './impostazioni.component';

@NgModule({
  declarations: [ImpostazioniComponent],
  imports: [
    CommonModule, 
    SharedModule, //MudeCommonModule, 
    ImpostazioniRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ImpostazioniModule {}
