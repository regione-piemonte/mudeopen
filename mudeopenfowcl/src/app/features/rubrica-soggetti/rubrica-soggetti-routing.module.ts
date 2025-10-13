/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InserisciPersonaFisicaComponent, InserisciPersonaGiuridicaComponent } from '../../shared/components';
import { VisualizzaRubricaComponent } from './visualizza-rubrica/visualizza-rubrica.component';


const routes: Routes = [
  {
    path: '',
    component: VisualizzaRubricaComponent,
  },
  {
    path: 'inserisci-persona-fisica',
    component: InserisciPersonaFisicaComponent
  },
  {
    path: 'inserisci-persona-giuridica',
    component: InserisciPersonaGiuridicaComponent
  },
  {
    path: 'visualizza',
    component: VisualizzaRubricaComponent,
    //data: { reuse: true }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RubricaSoggettiRoutingModule {}