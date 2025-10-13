/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MudeopenFoBeService } from 'mudeopen-common';
import { CategoriaBaseComponent } from '../../create/categorie/CategoriaBase.component';
import * as model from 'mudeopen-common';


@Component({
  selector: 'app-selezione-intestazione',
  templateUrl: './selezione-intestazione.component.html',
  styleUrls: ['./selezione-intestazione.component.css']
})
export class SelezioneIntestazioneComponent extends CategoriaBaseComponent implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public data: model.TemplateVO;
  public readOnly: boolean;
  docxInstanceFileList: any = [];
  loadDocx: boolean;
  
  constructor(public mudeopenFoBeService: MudeopenFoBeService) {
    super(mudeopenFoBeService, null);
    this.disableCountryLoading = true;
  }

  ngOnInit() {
    if(this.data) {
      if(this.data.modello_intestazione) {
        this.docxFileList = [ {
          name: this.data.modello_intestazione.path_modello, 
          size: this.data.modello_intestazione.dimensione_file,
        } ]; }
    }
  }

  intestazioneSelect(data: any, file: any, type: string = null) {
    if(this.selectDocx(data, file) !== false) {
      this.mudeopenFoBeService.salvaIntestazioneTemplate(data, this.docxFileList.length && this.docxFileList[0], type).subscribe(x => {
        this.data = x;

        this.loadDocx = type == 'full-document';
      }); }
  }
  
  intestazioneRemove(data: any, file: any) {
    this.docxFileList = [];

    const modello = data.modello_intestazione;
    if(modello?.id) {
      modello.dimensione_file = -1; // mark to be deleted
      this.mudeopenFoBeService.salvaIntestazioneTemplate(data, null).subscribe(x => {
        return modello; // SQ crap!
       });
    }
  }

  downloadDocxPdf() {
    this.mudeopenFoBeService.downloadModelloDocxTemplate(this.data.id_template).subscribe(x => {
      this.download2user(x);
    });
  }

  onSubmit(isOk: any = false) {
    this.confirmEvent.emit(isOk);
  }

}
