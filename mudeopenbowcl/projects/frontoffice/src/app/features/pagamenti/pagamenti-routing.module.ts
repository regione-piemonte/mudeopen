/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PagamentiComponent } from './pagamenti.component';

const routes: Routes = [
  {
    path: '',
    component: PagamentiComponent,
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagamentiRoutingModule {}
