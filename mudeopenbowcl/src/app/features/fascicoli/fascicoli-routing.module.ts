/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaFascicoliComponent } from './components/lista-fascicoli/lista-fascicoli.component';

const routes: Routes = [
     { path: '', component: ListaFascicoliComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FascicoliRoutingModule { }
