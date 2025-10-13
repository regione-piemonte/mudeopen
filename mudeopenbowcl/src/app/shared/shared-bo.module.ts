import { NgHasPermissionsDirective } from './directives/ng-has-permissions.directive';
import { TableComponent } from './table/table.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'mudeopen-common';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormioModule } from '@formio/angular';
import { NgHasPermissionsOnIstanceDirective } from './directives/ng-has-permissions-on-istance.directive';


@NgModule({
  declarations: [NgHasPermissionsDirective, NgHasPermissionsOnIstanceDirective,
     TableComponent],
  exports: [
    NgHasPermissionsDirective,
    NgHasPermissionsOnIstanceDirective,
    TableComponent
  ],
  imports: [
    CommonModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    NgbModule,
  ],

})
export class SharedBoModule { }
