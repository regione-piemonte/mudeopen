/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormioModule } from '@formio/angular';
import { SharedModule } from 'mudeopen-common';
import { BuilderComponent } from './create/builder/builder.component';
import { CComplessoComponent } from './create/categorie/c-complesso/c-complesso.component';
import { TabQuadroComponent } from './create/categorie/c-complesso/tab-quadro/tab-quadro.component';
import { ValidazioneQuadroComponent } from './create/categorie/c-complesso/validazione-quadro/validazione-quadro.component';
import { CategoriaBaseComponent } from './create/categorie/CategoriaBase.component';
import { FFormioComponent } from './create/categorie/f-formio/f-formio.component';
import { RReactiveComponent } from './create/categorie/r-reactive/r-reactive.component';
import { TTabellaComponent } from './create/categorie/t-tabella/t-tabella.component';
import { SelezioneTipoComponent } from './create/selezione-tipo/selezione-tipo.component';
import { GestioneQuadriComponent } from "./gestione/gestione-quadri.component";
import { VistaAssociazioneTemplateComponent } from "./gestione/vista-associazione-template/vista-associazione-template.component";
import { QuadriRoutingModule } from './quadri-routing.module';
import { QuadriService } from './services/quadri-services';
import { PublicationConfirmComponent } from './templates/publication-confirm/publication-confirm.component';
import { SelezionaAllegatoComponent } from "./templates/seleziona-allegato/seleziona-allegato.component";
import { SelezionaTipoIstanzaComponent } from "./templates/seleziona-tipo-istanza/seleziona-tipo-istanza.component";
import { SelezioneIntestazioneComponent } from "./templates/selezione-intestazione/selezione-intestazione.component";
import { SelezioneQuadroComponent } from "./templates/selezione-quadro/selezione-quadro.component";
import { TemplatesComponent } from "./templates/templates.component";
import { TipiQuadroComponent } from './tipi/tipi-quadro.component';


export const AppConfig = {
};

@NgModule({
  declarations: [
    TipiQuadroComponent,
    GestioneQuadriComponent,
    VistaAssociazioneTemplateComponent,
    TemplatesComponent,
    SelezioneQuadroComponent,
    SelezionaAllegatoComponent,
    PublicationConfirmComponent,
    SelezioneTipoComponent,
    SelezionaTipoIstanzaComponent,
    SelezioneIntestazioneComponent,
    CComplessoComponent,
    FFormioComponent,
    RReactiveComponent,
    TTabellaComponent,
    TabQuadroComponent,
    ValidazioneQuadroComponent,
    CategoriaBaseComponent,
    BuilderComponent
  ],
  imports: [ 
    CommonModule,
    FormsModule,
    QuadriRoutingModule,
    SharedModule, //MudeCommonModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
    FormioModule
  ],
  providers: [
    QuadriService
  ],
  entryComponents: [
    SelezioneTipoComponent,
    SelezioneQuadroComponent,
    VistaAssociazioneTemplateComponent,
    SelezionaAllegatoComponent,
    PublicationConfirmComponent,
    SelezionaTipoIstanzaComponent,
    SelezioneIntestazioneComponent,
    CComplessoComponent,
    TabQuadroComponent,
    ValidazioneQuadroComponent,
    FFormioComponent,
    RReactiveComponent,
    TTabellaComponent,
    BuilderComponent
  ]
})
export class QuadriModule {}