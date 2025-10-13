/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

import { NotFoundComponent } from './not-found/not-found.component';

declare var $$: any;

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../core/guards/auth.guards';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { Permissions } from '../core/enum/permissions.enum';
import { AuthorizationGuard } from '../core/guards';
import { UserNotFoundComponent } from './user-not-found/user-not-found.component';
import { NoRoleAssignedComponent } from './no-role-assigned/no-role-assigned.component';

const routes: Routes = [
  {
    path: 'quadri',
    data: {
      permission: Permissions.MANAGE_QUADRI,
    },
    loadChildren: () => import('./quadri/quadri.module').then((m) => m.QuadriModule),
    canActivate: [AuthorizationGuard],
  },
  {
    path: 'gestione',
    data: {
      permission: Permissions.SEARCH_PRATICA,
    },
    loadChildren: () => import('./gestione/gestione.module').then((m) => m.GestioneModule),
    canActivate: [AuthorizationGuard],
  },
  {
    path: 'scrivania',
    data: {
      permission: Permissions.VIEW_SCRIVANIA,
    },
    loadChildren: () => import('./scrivania/scrivania.module').then((m) => m.ScrivaniaModule),
    canActivate: [AuthorizationGuard],
  },

  {
    path: 'lista-istanze',
    data: {
      permission: Permissions.SEARCH_ISTANZA,
    },
    canActivate: [AuthorizationGuard],
    loadChildren: () =>
      import('./istanze/istanze.module').then((m) => m.IstanzeModule),
  },
  {
    path: 'lista-fascicoli',
    data: {
      permission: Permissions.SEARCH_FASCICOLO,
    },
    canActivate: [AuthorizationGuard],
    loadChildren: () =>
      import('./fascicoli/fascicoli.module').then((m) => m.FascicoliModule),
  },
  {
    path: 'lista-pratiche',
    data: {
      permission: Permissions.SEARCH_PRATICA,
    },
    canActivate: [AuthorizationGuard],
    loadChildren: () =>
      import('./pratiche/pratiche.module').then((m) => m.PraticheModule),
  },
  {
    path: 'scrivania-ds',
    data: {
      permission: Permissions.BO_DS_ISTANZE,
    },
    loadChildren: () => import('./scrivania-ds/scrivania-ds.module').then((m) => m.ScrivaniaDSModule),
    canActivate: [AuthorizationGuard],
  },
  {
    path: 'pratiche-ds',
    data: {
      permission: Permissions.BO_DS_PRATICHE,
    },
    loadChildren: () => import('./pratiche-ds/pratiche-ds.module').then((m) => m.PraticheDSModule),
    canActivate: [AuthorizationGuard],
  },


  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule),
  },
  {
    path: 'not-authorized',
    component: NotAuthorizedComponent,
  },
  {
    path: 'no-role-assigned',
    component: NoRoleAssignedComponent,
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BackofficeRoutingModule {
}
