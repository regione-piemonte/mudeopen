/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { AngularComponents, ComponentCategories, getComponentNanmes } from '../../../services/quadri-utils';
import { BuilderComponent } from '../../builder/builder.component';
import { CategoriaBaseComponent } from '../CategoriaBase.component';







@Component({
  selector: 'f-formio',
  templateUrl: './f-formio.component.html',
  styleUrls: ['./f-formio.component.scss']
})
export class FFormioComponent extends CategoriaBaseComponent implements OnInit {

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
      formio: new FormControl(null, [Validators.required]),
      submission: new FormControl(),
    });
  }

  ngOnInit() {
    this.componentNanmes = getComponentNanmes();
    this.data.flg_tipo_gestione = ComponentCategories.FORMIO;

    //replicate object in order to avoid passing data back to the caller
    this.data = JSON.parse(JSON.stringify(this.data));
    this.patchFormValues(this.mainForm, { 
      label: (this.data.json_configura_quadro as any)?.label,
      formio: JSON.stringify((this.data.json_configura_quadro as any)?.formio || (this.data.json_configura_quadro as any), null, '\t'),
      submission: (this.data.json_default && JSON.stringify(this.data.json_default)) || null,
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
      const formio: any = this.getFormio();
      let submission: any = this.getFormioSubmission();
      if(!formio || submission === null) { return; }
      if(!submission) { submission = null; }
  
      this.data.flg_attivo = 1; // this.data.flg_attivo;
      (this.data as any).json_configura_quadro = {
                                    "componentName": AngularComponents.FormioComponent,
                                    "label": this.mainForm.value.label,
                                    "formio": formio
                                  };
      this.data.json_default = submission;

      this.confirmEvent.emit({
        quadroVO: this.data,
        file: (this.docxFileList.length && this.docxFileList[0] instanceof Blob && this.docxFileList[0]) || null
//,_NOEXIT: true
      });
    }
  }

  noValidFormIO: boolean;
  getFormio() {
    this.noValidFormIO = false;
    try {
      return this.mainForm.value.formio && JSON.parse(this.mainForm.value.formio) 
                    || { components: [] };
    } catch (error) {
      this.noValidFormIO = true;
    }

    return null;
  }

  noValidFormIOsubmission: boolean;
  getFormioSubmission() {
    this.noValidFormIOsubmission = false;
    try {
      return this.mainForm.value.submission && JSON.parse(this.mainForm.value.submission) 
        || "";
    } catch (error) {
      this.noValidFormIOsubmission = true;
    }

    return null;
  }

  openBuilder() {
    const formio: any = this.getFormio();
    let submission: any = this.getFormioSubmission();
    if(!formio || submission === null) {
        return;}
    if(!submission) {
      submission = null;}

    const modal = this.modalService.open(BuilderComponent, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });
    modal.componentInstance.formioTemplate = formio;
    modal.componentInstance.submission = submission;
    modal.componentInstance.confirmEvent.subscribe((data) => {
      if(data) {
        this.mainForm.get('formio').setValue(JSON.stringify(data.formio, null, '\t'));
        this.mainForm.get('submission').setValue(data.submission && JSON.stringify(data.submission, null, '\t'));
      }

      modal.dismiss()
    });
  }

}
