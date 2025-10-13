/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainComponent } from './main/main.component';
import { ScrivaniaRoutingModule } from './scrivania-routing.module';
import { SharedModule } from 'mudeopen-common';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormioModule } from '@formio/angular';
import { SharedBoModule } from '../../shared/shared-bo.module';

@NgModule({
  declarations: [MainComponent],
  imports: [
    CommonModule,
    ScrivaniaRoutingModule,

    FormsModule,

    SharedModule, //MudeCommonModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
    FormioModule,
    SharedBoModule,
  ],
})
export class ScrivaniaModule {
}
