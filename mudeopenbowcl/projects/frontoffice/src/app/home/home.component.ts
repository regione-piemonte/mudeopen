/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BaseComponent } from 'mudeopen-common';
import { AuthStoreService } from 'mudeopen-common';



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent extends BaseComponent {


	/* 
	* nome metodo "constructor"; method description: 
	* @param (
    private router: Router
  )
	* @returns 
	*/ 

  constructor(private router: Router, public authService: AuthStoreService) {
    super();
  }


}
