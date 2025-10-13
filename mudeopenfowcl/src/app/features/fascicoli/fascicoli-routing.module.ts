/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IstanzaDettaglioComponent } from './fascicolo-dettaglio/fascicolo-dettaglio.component';
import { IntroFascicoloIstanzaComponent } from './intro-nuova-istanza/intro-nuova-istanza.component';
import { NotificheComponent } from './notifiche/notifiche.component';
import { VisualizzaRicercaComponent } from './visualizza-ricerca/visualizza-ricerca.component';

const routes: Routes = [
  { path: '', component: IntroFascicoloIstanzaComponent },
  { path: 'istanza', component: IstanzaDettaglioComponent },
  {
    path: 'notifiche',
    component: NotificheComponent//, data: { reuse: true } ,
  },

  { path: 'ricerca', 
    component: VisualizzaRicercaComponent, 
    data: { reuse: true }
  },
  { path: 'gestione', 
    children: [
      { path: 'dettagli-fascicolo', component: IntroFascicoloIstanzaComponent },
      { path: 'istanza', component: IstanzaDettaglioComponent } 
    ]
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NuovoFascicoloRoutingModule { }
