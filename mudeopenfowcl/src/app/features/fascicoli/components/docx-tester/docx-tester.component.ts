/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormUtils, MudeopenFoBeService, MessageService } from 'mudeopen-common';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Model } from '@swimlane/ngx-datatable';
import { FormsService } from '../../services/forms.service';

@Component({
  selector: 'app-docx-tester',
  templateUrl: './docx-tester.component.html',
  styleUrls: ['./docx-tester.component.css']
})
export class DocxTesterComponent extends FormUtils {

  public quadro: any;
  public idIstanza: number;
  
  public docxFileList: any = [];

  constructor(private modalService: NgbModal,
              public mudeopenFoBeService: MudeopenFoBeService,
              public formsService: FormsService,
              public messageService: MessageService) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = true;
  }

  public downloadDocxPdf(file: any) {
    this.docxFileList = [];
    this.mudeopenFoBeService.downloadDocxPDF(this.idIstanza, file).subscribe(x => {
      this.download2user(x);
    });
  }
  
  public downloadQuadroPDFIstanza() {
    this.docxFileList = [];
    this.mudeopenFoBeService.downloadQuadroPDFIstanza(this.idIstanza, this.quadro.id_quadro).subscribe(x => {
      this.download2user(x);
    });
  }

  public downloadQuadroDocx() {
    this.mudeopenFoBeService.downloadModello(this.quadro.modello_documentale.id, null).subscribe(x => {
      this.download2user(x);
    });
  }
  
  onSubmit(isOk: any = false) {
    this.modalService.dismissAll();
  }


}
