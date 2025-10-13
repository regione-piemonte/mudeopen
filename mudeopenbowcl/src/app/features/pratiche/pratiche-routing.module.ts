/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

import { DocumentiPraticheComponent } from './components/documenti-pratiche/documenti-pratiche.component';

declare var $$: any;

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaPraticheComponent } from './components/lista-pratiche/lista-pratiche.component';
import { PraticaResolverService } from './services/pratica-resolver.service';
import { DocumentiPraticaResolverService } from './services/documenti-pratica-resolver.service';
import { UploadDocumentoComponent } from './components/upload-documento/upload-documento.component';
import { AuthorizationGuard } from 'src/app/core/guards/authorization.guards';
import { Permissions } from '../../core/enum/permissions.enum';

const routes: Routes = [
  { path: '', component: ListaPraticheComponent },
  {
    path: ':id/documenti',
    resolve: { pratica: PraticaResolverService },
    component: DocumentiPraticheComponent,
  },
  {
    path: ':id/documenti/upload',
    data: {
      permission: Permissions.UPLOAD_DOC,
    },
    canActivate: [AuthorizationGuard],
    resolve: { pratica: PraticaResolverService },
    component: UploadDocumentoComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PraticheRoutingModule {}
