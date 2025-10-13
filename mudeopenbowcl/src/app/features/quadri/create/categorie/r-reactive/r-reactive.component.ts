import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { ComponentCategories, getComponentNanmes } from '../../../services/quadri-utils';
import { CategoriaBaseComponent } from '../CategoriaBase.component';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;








@Component({
  selector: 'r-reactive',
  templateUrl: './r-reactive.component.html',
  styleUrls: ['./r-reactive.component.scss']
})
export class RReactiveComponent extends CategoriaBaseComponent  implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public data: model.QuadroVO;
  public isReadonly: boolean;
  public messageAction: string;

  componentNanmes: any;
  mainForm: FormGroup;
  
  constructor(public mudeopenFoBeService: MudeopenFoBeService,
    private modalService: NgbModal,
    public messageService: MessageService) {
    super(mudeopenFoBeService, messageService);

    this.disableCountryLoading = true;

    this.mainForm = new FormGroup({
      label: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
      componentName: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      })
    });
  }

  ngOnInit() {
    this.componentNanmes = getComponentNanmes();

    //replicate object in order to avoid passing data back to the caller
    this.data = JSON.parse(JSON.stringify(this.data));
    this.data.flg_tipo_gestione = ComponentCategories.REACTIVE_FORMS;

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
      this.mainForm.disable(); }

    this.getDataDifferences(this.mainForm.value, this.data);
  }

  onSubmit(isOk: any = false) {
    if(!isOk) {
      if(!this.getDataDifferences(this.mainForm.value, this.data)) {
        this.confirmEvent.emit(null); }
      else  {
        this.confirmDialog("Si vogliono eliminare le modifiche?", () => {
          this.confirmEvent.emit(null);
        }, 'ELIMINA'); }
    } 
    else if(this.mainForm.invalid) {
      this.commonUtils.validateForm(this.mainForm); }
    else {
      this.data.flg_attivo = 1; 
      (this.data as any).json_configura_quadro = {
                                    "componentName":this.mainForm.value.componentName.id, 
                                    "label":this.mainForm.value.label
                                  };
      this.confirmEvent.emit({
        quadroVO: this.data,
        file: (this.docxFileList.length && this.docxFileList[0] instanceof Blob && this.docxFileList[0]) || null
//,_NOEXIT: true
      });
    }
  }

}
