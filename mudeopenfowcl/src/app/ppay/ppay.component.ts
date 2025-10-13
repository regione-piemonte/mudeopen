/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseComponent, MessageService, MudeopenFoBeService } from 'mudeopen-common';
import { AuthStoreService } from 'mudeopen-common';



@Component({
  selector: 'app-ppay',
  templateUrl: './ppay.component.html',
  styleUrls: ['./ppay.component.scss'],
})
export class PpayComponent extends BaseComponent {

  succeed: boolean = false;
  error: boolean = false;
  showButton: boolean = false;

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
        private mudeopenFoBeService: MudeopenFoBeService, 
        private ms: MessageService) {
    super();
    this.route.params.subscribe( params => this._init(params) );
  }
  
  _init(params: any) {
    if(!$$.getUrlParamValue('idPagamento')) return;

    this.succeed = $$.getUrlParamValue('codEsito') == '000';
    this.error = $$.getUrlParamValue('codEsito') == '100';
    
    this.mudeopenFoBeService.ppayCallback($$.getUrlParamValue('idPagamento'), 
                                             $$.getUrlParamValue('codEsito'), 
                                             $$.getUrlParamValue('descEsito'), 
                                             $$.getUrlParamValue('source')).subscribe(result => {
      this._closePopup();
    },
    (err) => {
      // nothing to do, even in case the call goes wrong      
      this._closePopup();
    });
  }

  _closePopup() {
    this.showButton = true;
    if(window.opener) {
      window.opener['myUpadteEvent'] = true;
    }
  }

  onEndPayment() {
    window.close();
  }    
}
