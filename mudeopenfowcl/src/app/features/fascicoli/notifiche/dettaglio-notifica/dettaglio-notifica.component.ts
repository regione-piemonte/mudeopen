/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MudeopenFoBeService } from 'mudeopen-common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';

import * as model from 'mudeopen-common';
import { StepManagerService } from '../../services';



@Component({
  selector: 'app-dettaglio-notifica',
  templateUrl: './dettaglio-notifica.component.html',
  styleUrls: ['./dettaglio-notifica.component.css'],
})
export class DettaglioNotificaComponent extends FormUtils implements OnInit {
  
  documents: model.DocumentoVO[] = [];

  public notifica: any; // set right properties in the swager in order to use  from model.NotificaVO;

	constructor(public mudeopenFoBeService: MudeopenFoBeService, 
              private messages: MessageService,
              private modalService: NgbModal,
              private stepManagerService: StepManagerService) {
    super(mudeopenFoBeService, messages);
  }


  ngOnInit(): void {
    this.mudeopenFoBeService.notificaLettaFO(this.notifica.id_notifica).subscribe(x => {
      this.documents = x;
      this.notifica.letto = true;
      this.stepManagerService.loadJDataIstanza(this.notifica.istanza.id_istanza, 0).subscribe((info: model.JDataIstanzaVO) => {
        const istanza = this.notifica.istanza;
        const jdata = this.notifica.istanza.json_data = JSON.parse(info.json_data);

        const praticaInfo = ((jdata?.CAMBIO_STATO_APA?.numPratica || '') || ((jdata?.CAMBIO_STATO_APA?.numPraticaNew || '' ) + ' - ' + jdata?.CAMBIO_STATO_APA?.anno)) + ' ';

        istanza.numero_pratica = istanza.numero_protocollo || jdata?.CAMBIO_STATO_APA?.numero || '';
        istanza.data_pratica = istanza.data_protocollo || jdata?.CAMBIO_STATO_APA?.data_delibera || '';
    
        istanza.numero_protocollo_comune = praticaInfo.substring(0, praticaInfo.indexOf(' '));
        istanza.data_protocollo_comune = jdata?.CAMBIO_STATO_APA?._data_creazione_protocollo || praticaInfo.substring(praticaInfo.lastIndexOf(' ')+1);
    
      });

    });
      
  }

  getFilename(doc: model.DocumentoVO) {
    return doc.nome_file_documento.indexOf('_') > -1 && doc.nome_file_documento.substring(doc.nome_file_documento.indexOf('_') + 1) || doc.nome_file_documento ;
  }

  downloadDocument(doc: model.DocumentoVO) {
    this.mudeopenFoBeService.downloadDocumento(doc.index_node).subscribe(x => {
      this.download2user(x);
    });
  }

  onExit() {
    this.modalService.dismissAll();
  }

}
