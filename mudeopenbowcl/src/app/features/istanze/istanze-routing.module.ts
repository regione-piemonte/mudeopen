/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

declare var $$: any;

import { CambioStatoComponent } from './components/cambio-stato/cambio-stato.component';
import { StepperComponent } from './components/stepper/stepper.component';


import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DettaglioIstanzaComponent } from './components/dettaglio-istanza/dettaglio-istanza.component';
import { ListaIstanzeComponent } from './components/lista-istanze/lista-istanze.component';
import { IstanzaResolverService } from './services/istanza-resolver.service';
import { Permissions } from '../../core/enum/permissions.enum';
import { AuthorizationGuard } from 'src/app/core/guards/authorization.guards';
import { InvioNotificaComponent } from './components/invio-notifica/invio-notifica.component';
import { IstanzaDettaglioComponent } from 'projects/frontoffice/src/app/features/fascicoli/fascicolo-dettaglio/fascicolo-dettaglio.component';
import { ModificaIstanzaComponent } from './components/modifica-istanza/modifica-istanza.component';

const routes: Routes = [
  { path: '', 
    component: ListaIstanzeComponent,
    data: { reuse: true, reloadIt: true }
  },
  {
    path: ':id',
    children: [
      { path: 'dettaglio-istanza', component: DettaglioIstanzaComponent },
      { path: 'dettaglio-istanza-stepper', component: IstanzaDettaglioComponent },
      {
        path: 'cambio-stato',  data: {
          permission: Permissions.CAMBIO_STATO,
        
        },
        canActivate: [AuthorizationGuard],
        component: CambioStatoComponent,
      
      },
      { path: 'invio-notifica',  
      data: {
        permission: Permissions.NOTIFICHE,
      
      },
      canActivate: [AuthorizationGuard],
       component: InvioNotificaComponent },


    ],
    runGuardsAndResolvers: 'paramsOrQueryParamsChange',
    resolve: { istanza: IstanzaResolverService },
  },
  { 
    path: 'modifica-dati-istanza/:idIstanza',
    component: ModificaIstanzaComponent,
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class IstanzeRoutingModule {}
