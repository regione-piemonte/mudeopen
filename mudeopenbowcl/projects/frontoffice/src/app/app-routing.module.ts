/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ExtraOptions, RouterModule, Routes } from '@angular/router';

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./features/features.module').then((m) => m.FeaturesModule),
  }

  //{ path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot( routes, configRouter ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
