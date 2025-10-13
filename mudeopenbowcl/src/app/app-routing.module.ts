/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

import { UserNotFoundComponent } from './features/user-not-found/user-not-found.component';

declare var $$: any;



import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards';

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

const routes: Routes = [
  {
    path: 'backoffice',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/backoffice.module').then((m) => m.BackofficeModule),
  },
  {
    path: 'accreditamento',
    loadChildren: () => import('./features/accreditamento/accreditamento.module').then((m) => m.AccreditamentoModule),
  },

  {
    path: 'user-not-found',
    component: UserNotFoundComponent
  },
  { path: '**', redirectTo: 'backoffice' },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot( routes, configRouter ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
