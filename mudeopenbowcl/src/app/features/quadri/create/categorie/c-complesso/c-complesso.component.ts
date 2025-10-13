/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, OnInit, EventEmitter, Output, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';

import { getComponentNanmes, getComponentCategory, ComponentCategories, AngularComponents } from '../../../services/quadri-utils';

import { CommonUtils } from 'mudeopen-common';
import { RegexUtil } from 'mudeopen-common';

import { MudeopenFoBeService } from 'mudeopen-common';

import * as model from 'mudeopen-common';

import { TabQuadroComponent } from  './tab-quadro/tab-quadro.component';
import { ValidazioneQuadroComponent } from  './validazione-quadro/validazione-quadro.component';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageService } from 'mudeopen-common';

import { CategoriaBaseComponent } from '../CategoriaBase.component';

@Component({
  selector: 'app-c-complesso',
  templateUrl: './c-complesso.component.html',
  styleUrls: ['./c-complesso.component.scss']
})
export class CComplessoComponent extends CategoriaBaseComponent  implements OnInit {

  @ViewChild('tabsTable') tabsTable: any;

  @Output('confirmEvent') confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public data: model.QuadroVO;
  public isReadonly: boolean;
  public messageAction: string;
  
  tabs: any = [];
  stepValidations: any = [];
  
  componentNanmes: any;
  quadriFomrioList: model.SelectVO[] = [];

  mainForm: FormGroup;
  
  constructor(public mudeopenFoBeService: MudeopenFoBeService,
          private modalService: NgbModal,
          public messageService: MessageService) {
    super(mudeopenFoBeService, messageService);
    
    this.disableCountryLoading = true;

    this.datatable.limit = 25;
    this.datatable.messages = {
      'emptyMessage': 'Non ci sono tab definiti per questo quadro',
      'totalMessage': 'Tab totali'
    };

    this.mainForm = new FormGroup({
      label: new FormControl(null, [Validators.required, Validators.maxLength(50)])
    });

  }

  ngOnInit() {
    this.componentNanmes = getComponentNanmes();
    //replicate object in order to avoid passing data back to the caller
    this.data = JSON.parse(JSON.stringify(this.data));
    
    this.data.flg_tipo_gestione = ComponentCategories.COMPLEX;

    this.tabs = (this.data?.json_configura_quadro as any)?.tabs || [];
    this.stepValidations = (this.data?.json_configura_quadro as any)?.stepValidations || [];

    this.patchFormValues(this.mainForm, { 
      label: (this.data.json_configura_quadro as any)?.label,
      componentName: { id: (this.data.json_configura_quadro as any)?.componentName, value: null }
    });

    if(this.data.modello_documentale) {
      this.docxFileList = [ {
        name: this.data.modello_documentale.path_modello, 
        size: this.data.modello_documentale.dimensione_file,
      } ]; }

    if(this.isReadonly) {
      this.mainForm.disable() }

    this.datatable.count = this.tabs.length;
    this.getDataDifferences(this.mainForm.value, this.data, this.stepValidations);

    this.mudeopenFoBeService.listaQuadri(JSON.stringify({
      "tipoGestione": { "eq": 'F' },
      "stato": { "eq": 1 },
      "result_type": { "eq": 'essential' }
    }), 0, 1000, '+mudeDTipoQuadro_codTipoQuadro, -numVersione').subscribe(x => {
      this.quadriFomrioList = x.body.map(x => { return { id: ('~' + x.id_quadro + '~ ' + (x.tipo_quadro.tipo_quadro_padre?.cod_tipo_quadro || x.tipo_quadro.cod_tipo_quadro)), value: (x.tipo_quadro.cod_tipo_quadro + ' (versione ' + x.num_versione + ')') } });
    });

  }

  invalidTabStructure: boolean = false;
  onSubmit(isOk: any = false) {
    if(!isOk) {
      if(!this.getDataDifferences(this.mainForm.value, this.data, this.stepValidations)) {
        this.confirmEvent.emit(null); }
      else {
        this.confirmDialog("Si vogliono eliminare le modifiche?", () => {
          this.confirmEvent.emit(null);
        }, 'ELIMINA'); }
    } 
    else if(this.mainForm.invalid) {
      this.commonUtils.validateForm(this.mainForm); }
    else {
      if(this.invalidTabStructure = !this.tabs.length || (this.tabs.filter(x => x.subtabs).length && this.tabs.filter(x => x.subtabs && !x.subtabs.length).length)) {
        return; }

      this.data.flg_attivo = 1; // this.data.flg_attivo;
      (this.data as any).json_configura_quadro = {
                                    "componentName": AngularComponents.CompilaIstanzaComponent,
                                    "label":this.mainForm.value.label,
                                    "tabs": this.tabs,
                                    "stepValidations": this.stepValidations
                                  };

      this.confirmEvent.emit({
//_NOEXIT: true,
        quadroVO: this.data,
        file: (this.docxFileList.length && this.docxFileList[0] instanceof Blob && this.docxFileList[0]) || null
      });
    }
  }

  openEditTab(tab, subtab, type, changeAllowed: boolean) {
    const modal = this.modalService.open(TabQuadroComponent, { size: 'lg', backdrop: 'static', keyboard: false });
    modal.componentInstance.tab = subtab || tab;
    modal.componentInstance.tabType = type;
    modal.componentInstance.isReadonly = this.isReadonly;
    modal.componentInstance.changeTypeAllowed = changeAllowed;
    modal.componentInstance.quadriFomrioList = this.quadriFomrioList;

    const index = ((subtab && tab.subtabs) || this.tabs).indexOf(subtab || tab);
    modal.componentInstance.confirmEvent.subscribe((tabChanged) => {
      if(!this.isReadonly) {
        if(tabChanged) {
          if(index == -1) {
            ((subtab && tab.subtabs) || this.tabs).push(tabChanged); }
          else {
            ((subtab && tab.subtabs) || this.tabs)[index] = tabChanged; }
        }

        this.tabs = JSON.parse(JSON.stringify(this.tabs) );
        setTimeout(x => { 
          this.tabsTable.rowDetail.toggleExpandRow(this.tabs.find(t => t.id_quadro == this.rowExpanded))
        });
      }

      modal.dismiss();
    });
  } 

  editTab(tab, rowIndex) {
    this.openEditTab(tab, null, tab.subtabs?1:0, true);
  }

  deleteTab(tab, rowIndex) {
    this.tabs.splice(this.tabs.indexOf(tab), 1);
    this.tabs = [...this.tabs];

  }

  tabMoveup(tab, rowIndex) {
    const pos = this.tabs.indexOf(tab);
    this.tabs.splice(pos, 1);
    this.tabs.splice(pos - 1, 0, tab);
    this.tabs = [...this.tabs];
  }

  subtabModify(tab, subtab) {
    this.openEditTab(tab, subtab, 0, false);
  }

  subtabDelete(tab: any, subtab: any) {
    tab.subtabs.splice(tab.subtabs.indexOf(subtab), 1);
  }

  subtabMoveup(tab: any, subtab: any) {
    const pos = tab.subtabs.indexOf(subtab);
    tab.subtabs.splice(pos, 1);
    tab.subtabs.splice(pos - 1, 0, subtab);
  }

  addNewTab() {
    this.openEditTab({}, null, 1, true);
  }

  addNewSubtab(tab) {
    this.openEditTab(tab, {}, 0, false);
  }

  rowExpanded: number;
  toggleExpandRow(row, expanded) {
    this.rowExpanded = expanded? null : row.id_quadro;
    this.toggleTableDetails(this.tabsTable, row, expanded);
  }

  getLimitedText(txt) {
    if(txt && txt.length > 255) {
      return txt.substring(0, 255) + ' ...'; }

      return txt;
  }

  editValidation(validation: any = null) {
    const modal = this.modalService.open(ValidazioneQuadroComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.data = validation;
    modal.componentInstance.isReadonly = this.isReadonly;
    modal.componentInstance.confirmEvent.subscribe((res) => {
      if(!this.isReadonly) {
        if(res) {
          if(validation) {
            this.stepValidations.splice(this.stepValidations.indexOf(validation), 1, res); }
          else {
            this.stepValidations.push(res); }

          this.stepValidations = [...this.stepValidations];
        }
      }

      modal.dismiss();
    });
  }

  deleteValidation(validation: any) {
    this.stepValidations.splice(this.stepValidations.indexOf(validation), 1);
  }

  validationMoveup(validation: any) {
    const pos = this.stepValidations.indexOf(validation);
    this.stepValidations.splice(pos, 1);
    this.stepValidations.splice(pos - 1, 0, validation);
  }

  getFomrio(idQuadroTilde: string): string {
    if(idQuadroTilde) {
      return ''+this.quadriFomrioList.find(x => x.id.startsWith(idQuadroTilde))?.value.replace(/[ ]/, '<span class="verText">'); }

    return '<span class="verText label">(quadro con sotto TAB)</span>';
  }

}
