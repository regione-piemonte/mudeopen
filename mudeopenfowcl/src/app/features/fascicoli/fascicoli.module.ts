/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
declare var $$: any;



import { RiferimentoIstanzeComponent } from './steps/riferimento-istanze/riferimento-istanze.component';
import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormioAppConfig, FormioModule } from '@formio/angular';
import { MudeCommonModule, SharedModule } from 'mudeopen-common';
import { AppSharedModule } from '../../shared/appshared.module';
import { RubricaSoggettiModule } from '../rubrica-soggetti/rubrica-soggetti.module';
import { AggiungiAbilitazioneUtenteComponent } from './components/istanza-permissions-manager/aggiungi-abilitazione-utente/aggiungi-abilitazione-utente.component';
import { IstanzaPermissionsManagerComponent } from './components/istanza-permissions-manager/istanza-permissions-manager.component';
import { SelezionaNuovaInstazaComponent } from './components/seleziona-nuova-instaza/seleziona-nuova-instaza.component';
import { SelezionaSoggettiFascicoloComponent } from './components/seleziona-soggetti-fascicolo/seleziona-soggetti-fascicolo.component';
import { NuovoFascicoloRoutingModule } from './fascicoli-routing.module';
import { IstanzaDettaglioComponent } from './fascicolo-dettaglio/fascicolo-dettaglio.component';
import { IntroFascicoloIstanzaComponent } from './intro-nuova-istanza/intro-nuova-istanza.component';
import { StepManagerService } from './services';
import { FormsService } from './services/forms.service';
import * as steps from './steps';
import { VisualizzaRicercaComponent } from './visualizza-ricerca/visualizza-ricerca.component';
import { AggiungiRiferimentoIstanzaComponent } from './steps/riferimento-istanze/aggiungi-riferimento-istanza/aggiungi-riferimento-istanza.component';
import { DocxTesterComponent } from './components/docx-tester/docx-tester.component';
import { NotificheComponent } from './notifiche/notifiche.component';
import { DettaglioNotificaComponent } from './notifiche/dettaglio-notifica/dettaglio-notifica.component';
import { ListaAllegatiComponent } from './allegati/lista-allegati/lista-allegati.component';
import { ListaStatiComponent } from './lista-stati/lista-stati.component';

export const AppConfig = {
};

@NgModule({
  declarations: [
    IntroFascicoloIstanzaComponent,
    IstanzaDettaglioComponent,
    VisualizzaRicercaComponent,
    IstanzaPermissionsManagerComponent,
    AggiungiAbilitazioneUtenteComponent,
    AggiungiRiferimentoIstanzaComponent,
    steps.PresentaIstanzaComponent,
    steps.SoggettiCoinvoltiComponent,
    steps.SoggettoAbilitatoComponent,
    steps.RiferimentoIstanzeComponent,
    steps.AllegatiComponent,
    steps.CompilaIstanzaComponent,
    steps.PagamentiComponent,
    steps.FormioComponent,
    SelezionaNuovaInstazaComponent,
    SelezionaSoggettiFascicoloComponent,
    DocxTesterComponent,
    NotificheComponent,
    ListaAllegatiComponent,
    ListaStatiComponent,
    DettaglioNotificaComponent
  ],
  imports: [
    AppSharedModule,
    CommonModule,
    FormsModule,
  //  AuthStoreService,
    NuovoFascicoloRoutingModule,
    ReactiveFormsModule,
    RubricaSoggettiModule, // Per innestare InserisciPersonaFisicaComponent/InserisciPersonaGiuridicaComponent in SoggettiCoinvoltiComponent.
    SharedModule, //MudeCommonModule,
    NgxDatatableModule,
    FormioModule
  ],
  providers: [
    StepManagerService,
    FormsService,
//    {provide: FormioAppConfig, useValue: AppConfig}, // IF USED CREATE PROBLEMS WITH FORMBUILDER!
    //{provide: LOCALE_ID, useValue: 'it-IT' }
  ],
  entryComponents: [
    steps.RiferimentoIstanzeComponent,
    steps.SoggettoAbilitatoComponent,
    steps.SoggettiCoinvoltiComponent,
    steps.PagamentiComponent,
    steps.CompilaIstanzaComponent,
    steps.AllegatiComponent,
    steps.PresentaIstanzaComponent,
    steps.FormioComponent,
    IstanzaPermissionsManagerComponent,
    AggiungiAbilitazioneUtenteComponent
  ]
})
export class NuovoFascicoloModule {
  public static forRoot(): ModuleWithProviders<NuovoFascicoloModule> {
    return {
      ngModule: NuovoFascicoloModule,
      providers: [
        StepManagerService
      ]
    };
  }  
}