/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnInit } from '@angular/core';
import { MudeopenFoBeService } from 'mudeopen-common';
import { FormUtils } from 'mudeopen-common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FormsService } from '../services/forms.service';
import { AuthStoreService, MessageService } from 'mudeopen-common';
import { DettaglioNotificaComponent } from './dettaglio-notifica/dettaglio-notifica.component';
import { FormControl, FormGroup } from '@angular/forms';

import * as model from 'mudeopen-common';

@Component({
  selector: 'notifiche',
  templateUrl: './notifiche.component.html',
  styleUrls: ['./notifiche.component.scss']
})
export class NotificheComponent extends FormUtils implements OnInit {

	lockedOnIdIstanza: number = null;
	popupmode: boolean = false;

	columns: any = [
		{ name: 'Istanza', prop: '_codIstanza', resizeable: true, sortable: false, width: 148 },
		{ name: 'Comune', prop: '_comune', resizeable: true, sortable: false, width: 148 },
		{ name: 'Mittente', prop: '_mittente', resizeable: true, sortable: false, width: 148 },
		{ name: 'Tipo Notifica', prop: 'id_tipo_notifica.desTipoNotifica', resizeable: true, sortable: false, width: 148 },
		{ name: 'Oggetto', prop: 'oggetto_notifica', resizeable: true, sortable: false, width: 148 },
		{ name: 'Data Notifica', prop: 'dt_ins', resizeable: true, sortable: false, width: 148 },
		{ name: 'Allegati', prop: '_docs', resizeable: true, sortable: false, width: 148 }
	];

	ricercaForm: FormGroup;

	/* 
	* nome metodo "constructor"; method description: 
	* @param (private mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

	constructor(public formsService: FormsService, 
				private authService: AuthStoreService,
				public mudeopenFoBeService: MudeopenFoBeService, 
				private messages: MessageService,
				private modalService: NgbModal) {
		super(mudeopenFoBeService, messages);
		this.disableCountryLoading = true;

		this.ricercaForm = new FormGroup({
			intestatario: new FormControl(),
			comune: new FormControl(),
			codIstanza: new FormControl(!$$.isLocal? "" : "01-001011-0000007962-2023")
		  });
	}

	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
	this.onSearch();	
  }

  rows: any = [];
  rowExpanded: any;
  onSearch(page: any = 0) {
    if(page && page.pageSize) { page = page.offset; }

	this.mudeopenFoBeService.loadNotificheFO(page, this.datatable.limit, this.lockedOnIdIstanza? { idIstanza: this.lockedOnIdIstanza} : this.ricercaForm.value).subscribe(x => {
		this.rows = x.body;
		(this.rows || []).forEach(item => {
			(item as any)._mittente = (!item.ruolo_mittente? '' : item.ruolo_mittente);
			(item as any)._comune = item?.istanza.comune?.value + ' ('+item?.istanza.provincia?.value+')';
			(item as any)._intestatario = this.getSubjectInfo('name', item?.istanza?.intestatario?.anagrafica);
			(item as any)._codIstanza = item?.istanza.codice_istanza+ '<br>' +  (item as any)._intestatario;
			const sqm = (item as any).documentiRif.length == 1? 'o' : 'i';
			(item as any)._docs = !(item as any)?.documentiRif?.length? 'No' : ((item as any).documentiRif.length + ' allegat' + sqm);
		});

		this._handlePaging(x);
	});

  }

  openDetails(notifica: model.NotificaVO) {
    const modal = this.modalService.open(DettaglioNotificaComponent, { size: 'lg', backdrop: 'static', keyboard: false });
    modal.componentInstance.notifica = notifica;
  }

  onExit() {
    this.modalService.dismissAll();
  }

  showVisualizzaButton(istanza: model.IstanzaVO):boolean {
    return istanza.fonte == 'FO';
  }

  downloadPDF(istanza: model.IstanzaVO) {
    this.mudeopenFoBeService.downloadIstanza(istanza.id_istanza).subscribe(x => {
      this.download2user(x);
    });
  }

  downloadReceiptPDF(istanza: model.IstanzaVO) {
    this.mudeopenFoBeService.downloadRicevutaPdf(istanza.id_istanza).subscribe(x => {
      this.download2user(x);
    });
  }

}