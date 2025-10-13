/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GestioneQuadriComponent } from "./gestione/gestione-quadri.component";
import { TemplatesComponent } from "./templates/templates.component";
import { TipiQuadroComponent } from './tipi/tipi-quadro.component';

const routes: Routes = [
  { path: 'tipi', component: TipiQuadroComponent },
  { path: 'gestione', component: GestioneQuadriComponent },
  { path: 'templates', component: TemplatesComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuadriRoutingModule { }
