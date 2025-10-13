/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthStoreService, FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';
import { ColumnMode, SelectionType } from '@swimlane/ngx-datatable';
import { ftruncate } from 'fs';



@Component({
  selector: 'app-geeco',
  templateUrl: './geeco.component.html',
  styleUrls: ['./geeco.component.scss'],
})
export class GeecoComponent extends FormUtils {
  columnMode = ColumnMode;
  selectionType = SelectionType;

  isSuccess: boolean  = false;

  listCatasto: any = [];
  listIndirizzi: any = [];

  datatableIndirizzi: any = {
    offset: 0,
    count: 0,
    limit: 3,
    selected: [],
    pages: 0,
    messages: this.datatable.messages
  }

  datatableCatasto: any = {
    offset: 0,
    count: 0,
    limit: 3,
    selected: [],
    pages: 0,
    messages: this.datatable.messages
  }

  headersIndirizzi: any[] = [
    { name: 'Indirizzo', prop: 'descr_indirizzo' }
  ];

  headersCatasto: any[] = [
    { name: 'Sezione', prop: 'text_fieldsezione' },
    { name: 'Foglio', prop: 'foglioN' },
    { name: 'Mappale', prop: 'map' },
    { name: 'Subalterno', prop: 'sub' },
    { name: 'Catasto', prop: 'censito_al_catasto' }
  ];

  /*
  headersIndirizzi: any[] = [
    { name: 'Sedime', prop: 'sedime' },
    { name: 'Denominazione', prop: 'denominazione' },
    { name: 'Numero', prop: 'n' }
  ];

  headersCatasto: any[] = [
    { name: 'Mappale', prop: 'map' },
    { name: 'Subalterno', prop: 'sub' },
    { name: 'Foglio', prop: 'foglioN' }
  ];
*/


	/* 
	* nome metodo "constructor"; method description: 
	* @param (
    private router: Router
  )
	* @returns 
	*/ 

  constructor(private router: Router, 
              private route: ActivatedRoute,
              public authService: AuthStoreService,
              public mudeopenFoBeService: MudeopenFoBeService, 
              public messageService: MessageService) {
      super(mudeopenFoBeService, messageService);

      if(window.location.href.indexOf('/mudeopen/bo') > -1) {
        this.quit(); }
      else {
        this.route.params.subscribe( params => this._init(params.idIstanza) ); }
  }
  
  currIdIstanza: number;
  _init(idIstanza: number) {
    if(!idIstanza) return;

    this.currIdIstanza = idIstanza;
    window.resizeTo(Math.min(screen.availWidth, 850), Math.min(screen.availHeight, 900));
    this.mudeopenFoBeService.recuperaDatiGeeco(idIstanza).subscribe((outUtenteVO) => {
      outUtenteVO = JSON.parse(outUtenteVO || '{}');

      this.listIndirizzi = outUtenteVO.datiUbicazione || [];
      this.listCatasto = outUtenteVO.datiCatastali || [];

      if(!this.listIndirizzi?.length && !this.listCatasto?.length) { 
        console.log('QUITTING.....');
        setTimeout(_ => { this.quit();  }, 1000);
      }

      console.log('FOUND: ' + this.listIndirizzi?.length + ' - ' + this.listCatasto?.length);
    },
    (err) => { 
      console.log('ERROR.....');
      setTimeout(_ => { this.quit();  }, 1000);
    });

  }

  quit() {
    window.close();
  }  

  addAddress() {
    if(!(!this.listIndirizzi?.length || this.datatableIndirizzi?.selected?.length) || !(!this.listCatasto?.length || this.datatableCatasto?.selected?.length)) {
      return }

    if(this.listIndirizzi?.length) {
      this.datatableIndirizzi.selected[0].selezionare_se_si_tratta_di_indirizzo_principale = true;
      this.datatableIndirizzi.selected.forEach(x => {
        x.geolocalizzazione = 'geolocalizzazione';
      });
    }

    if(this.listCatasto?.length) {
      this.datatableCatasto.selected.forEach(x => {
        x.text_fieldsezione = x.text_fieldsezione == '_'? '' : x.text_fieldsezione;
        x.geolocalizzazione = 'geolocalizzazione';
      });
    }

    this.mudeopenFoBeService.saveSelectedJson(this.currIdIstanza, JSON.stringify({
      datiUbicazione: this.datatableIndirizzi?.selected || [],
      datiCatastali: this.datatableCatasto?.selected || []
    })).subscribe((outUtenteVO) => {
      // set refresh data
      if(window.opener) {
        window.opener['myUpadteEvent'] = true;
      }

      this._closePopup();
    },
    (err) => { 
      this.quit();
    });
  }

  _closePopup() {
    if(window.opener) {
      window.opener['myUpadteEvent'] = true;
    }

    this.isSuccess = true;
  }

}
