import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PraticheRoutingModule } from './pratiche-routing.module';
import { SharedBoModule } from 'src/app/shared/shared-bo.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormioModule } from '@formio/angular';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'mudeopen-common';
import { ListaPraticheComponent } from './components/lista-pratiche/lista-pratiche.component';
import { DocumentiPraticheComponent } from './components/documenti-pratiche/documenti-pratiche.component';
import { UploadDocumentoComponent } from './components/upload-documento/upload-documento.component';

@NgModule({
  declarations: [
    ListaPraticheComponent,
    DocumentiPraticheComponent,
    UploadDocumentoComponent,
  ],
  imports: [
    CommonModule,
    PraticheRoutingModule,
    FormsModule,
    SharedBoModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
    FormioModule,
    //FormioModule,
    FormsModule,
    SharedModule, //MudeCommonModule,
  ],
})
export class PraticheModule {}
