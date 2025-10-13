/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MudeCommonModule, SharedModule } from 'mudeopen-common';
import { FeaturesRoutingModule } from './features-routing.module';

@NgModule({
  imports: [
    FeaturesRoutingModule, 
    SharedModule, //MudeCommonModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class FeaturesModule {}
