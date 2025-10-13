/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/


declare var $$: any;

import { Component, OnInit, Input } from '@angular/core';
import { FormUtils } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { FormsService } from '../../../../features/fascicoli/services/forms.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-lista-allegati',
  templateUrl: './lista-allegati.component.html',
  styleUrls: ['./lista-allegati.component.scss']
})
export class ListaAllegatiComponent extends FormUtils  implements OnInit {

  @Input() public idIstanza: number;

  constructor(private formsService: FormsService, 
      mudeopenFoBeService: MudeopenFoBeService,
      private modalService: NgbModal) {
    super(mudeopenFoBeService, null);
  }

  isLoading: boolean;
  noAttches: boolean;
  attachemnts: any = [];
  ngOnInit() {
    this.isLoading = true;
    this.mudeopenFoBeService.loadAllegatiIstanza(this.idIstanza).subscribe((allegati: Array<model.AllegatoVO>) => {
      this.isLoading = false;
      this.noAttches = (!allegati?.length);
      if(this.noAttches) { return; }

      (allegati || []).forEach((a) => {
        const key = a.tipo_allegato.categoria_allegato.descrizione_estesa_categoria_allegato+': '+a.tipo_allegato.descrizione_breve_tipo_allegato;
        const fileList = this.attachemnts[key] || [];
        fileList.push(a);
        this.attachemnts[key] = fileList;
      })
    });

  }

  attachName(attach) {
    return attach.nome_file_allegato.replace(/^([A-Z]+[0-9]+_)(.*)$/, '$2');
  }

  downloadAttach(attach) {
    this.mudeopenFoBeService.downloadAllegato(attach.index_node).subscribe(x => {
      this.download2user(x);
    });
  }

  onExit() {
    this.modalService.dismissAll();
  }

}
