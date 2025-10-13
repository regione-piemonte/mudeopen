/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { MudeCommonModule, SharedModule } from 'mudeopen-common';
import { ContattiRoutingModule } from './contatti-routing.module';
import { ContattiComponent } from './contatti.component';

@NgModule({
  declarations: [ContattiComponent],
  imports: [CommonModule, MudeCommonModule, ContattiRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ContattiModule {}
