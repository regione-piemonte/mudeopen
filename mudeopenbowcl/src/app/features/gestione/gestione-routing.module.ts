/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

declare var $$: any;

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CambioStatoComponent } from './istanze/cambio-stato/cambio-stato.component';
import { IstanzeComponent } from './istanze/istanze.component';

const routes: Routes = [
  { path: 'istanze', component: IstanzeComponent },
  { path: 'istanza/cambio-stato/:idIstanza', component: CambioStatoComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GestioneRoutingModule {
}
