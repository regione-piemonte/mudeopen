/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { ElementRef, Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthStoreService, FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';

import * as model from 'mudeopen-common';


@Component({
  selector: 'app-loccsi',
  templateUrl: './loccsi.component.html',
  styleUrls: ['./loccsi.component.scss'],
})
export class LoccsiComponent extends FormUtils {
  @Output('onConfirm') onConfirm: EventEmitter<any> = new EventEmitter<any>();

  @ViewChild('searchBoxField') searchBoxField: ElementRef;

  nodata: boolean;
  searchBox: string;
  comune: model.ComuneVO;

  headers: any[] = [
    { name: 'Indirizzo', prop: 'loccsi_label' },
    { name: 'Località', prop: 'localita' },
    { name: 'CAP', prop: 'cap' },
    { name: 'Comune', prop: 'comune' },
    { name: 'ISTAT', prop: 'codice_istat' }
  ];

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

  if($$.isLocal)
    this.searchBox = 'via belfiore 19';
  }

  ngAfterViewInit() {
    this.searchBoxField.nativeElement.focus();
  }

  _lastWriteEvent = null;
  onSearchChange(event = null) {
    if(this._lastWriteEvent) { clearTimeout(this._lastWriteEvent); }

    if(!event || event.which == 13 || event.keyCode == 13) {
      this.onSearch();
    } else {
      this._lastWriteEvent = setTimeout(_ => {
        this.onSearch();
      }, 1500);
    }
  }

  addresses: any = [];
  onSearch() {
    if(!this.searchBox) { return; }

    this.addresses = [];
    this.mudeopenFoBeService.listaToponomastica(this.searchBox, this.comune.codBelfiore, this.comune.value, 0, 30).subscribe((result: any) => {
      result.forEach(element => {
        if(element.name != 'ind_loccsi_strade' && element.name != 'ind_loccsi_civici_full') { return; }

        const addr = (element.featureCollection?.features || []).map(feature => {
          return feature.properties;
        });

        if(addr?.length) { this.addresses = [...this.addresses, ...addr]; }

        $$.log3(JSON.stringify(this.addresses))

      });
    });
  }

  annulla() {
    this.onConfirm.emit(null);
  }  

  addAddress() {
    this.onConfirm.emit(this.datatable.selected[0]);
  }

}
