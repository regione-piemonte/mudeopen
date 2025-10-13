import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListaIstanzeComponent } from './components/lista-istanze/lista-istanze.component';
import { IstanzeRoutingModule } from './istanze-routing.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormioModule } from '@formio/angular';
import { SharedBoModule } from 'src/app/shared/shared-bo.module';
import { SharedModule } from 'mudeopen-common';
import { DettaglioIstanzaComponent } from './components/dettaglio-istanza/dettaglio-istanza.component';
import { CambioStatoComponent } from './components/cambio-stato/cambio-stato.component';
import { RecapIstanzaComponent } from './components/recap-istanza/recap-istanza.component';
import { RecapBreveIstanzaComponent } from './components/recap-breve-istanza/recap-breve-istanza.component';
import { InvioNotificaComponent } from './components/invio-notifica/invio-notifica.component';
import { DettaglioNuovaNotificaComponent } from './components/invio-notifica/dettaglio-nuova-notifica/dettaglio-nuova-notifica.component';
import { NotificaDetailsComponent } from './components/invio-notifica/notifica-details/notifica-details.component';
import { StepperComponent } from './components/stepper/stepper.component';
import { ModificaIstanzaComponent } from './components/modifica-istanza/modifica-istanza.component';

@NgModule({
  declarations: [
    ListaIstanzeComponent,
    DettaglioNuovaNotificaComponent,
    InvioNotificaComponent,
    DettaglioIstanzaComponent,
    CambioStatoComponent,
    StepperComponent,
    RecapIstanzaComponent,
    RecapBreveIstanzaComponent,
    NotificaDetailsComponent,
    ModificaIstanzaComponent
  ],
  imports: [
    CommonModule,
    IstanzeRoutingModule,
    FormsModule,
    SharedBoModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
    SharedModule,
    FormioModule,
  ],
})
export class IstanzeModule {}
