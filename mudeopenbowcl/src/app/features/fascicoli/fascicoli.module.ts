import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FascicoliRoutingModule } from './fascicoli-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedBoModule } from 'src/app/shared/shared-bo.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ListaFascicoliComponent } from './components/lista-fascicoli/lista-fascicoli.component';
import { FormioModule } from '@formio/angular';
import { SharedModule } from 'mudeopen-common';



@NgModule({
  declarations: [ListaFascicoliComponent],
  imports: [
    CommonModule,
    FascicoliRoutingModule,
    FormsModule,
    SharedBoModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
    FormioModule,
    FormsModule,
 
    SharedModule, //MudeCommonModule,
  ]
})
export class FascicoliModule { }
