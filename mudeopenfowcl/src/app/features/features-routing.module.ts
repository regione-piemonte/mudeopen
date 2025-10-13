/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'mudeopen-common';




const routes: Routes = [
  {
    path: 'accreditamento',
    loadChildren: () => import('./accreditamento/accreditamento.module').then((m) => m.AccreditamentoModule),
  },
  {
    path: 'home',
    loadChildren: () => import('../home/home.module').then((m) => m.HomeModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'fascicolo',
    loadChildren: () => import('./fascicoli/fascicoli.module').then((m) => m.NuovoFascicoloModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'rubrica-soggetti',
    loadChildren: () => import('./rubrica-soggetti/rubrica-soggetti.module').then((m) => m.RubricaSoggettiModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'contatti',
    loadChildren: () => import('./contatti/contatti.module').then((m) => m.ContattiModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'impostazioni',
    loadChildren: () => import('./impostazioni/impostazioni.module').then((m) => m.ImpostazioniModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'pagamenti',
    loadChildren: () => import('./pagamenti/pagamenti.module').then((m) => m.PagamentiModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'scadenzari',
    loadChildren: () => import('./scadenzari/scadenzari.module').then((m) => m.ScadenzariModule),
    canActivate: [AuthGuard],
  },


  {
    path: 'geeco-callback/:idIstanza',
    loadChildren: () => import('../geeco/geeco.module').then((m) => m.GeecoModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'ppayCallback',
    loadChildren: () => import('../ppay/ppay.module').then((m) => m.PpayModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'loccsi',
    loadChildren: () => import('../loccsi/loccsi.module').then((m) => m.LoccsiModule),
    canActivate: [AuthGuard],
  },
  
  
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FeaturesRoutingModule {}
