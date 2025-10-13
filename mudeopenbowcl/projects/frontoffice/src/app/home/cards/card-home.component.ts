/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'card-home',
  templateUrl: './card-home.component.html',
  styleUrls: ['./card-home.component.css']
})
export class CardHomeComponent {

  @Input() cardText: string;
  @Input() cardTextSmall: string;
  @Input() cardTitle: string;
  @Input() cardTitleFooter: string;
  @Input() imagePath: string;
  @Input() routerLink: string;
  @Input() state: any;
  

  constructor(private router: Router) { }


	/* 
	* nome metodo "gotolink"; method description: 
	* @param ()
	* @returns 
	*/ 

  gotolink() {
    if(this.state) {
      this.router.navigate([this.routerLink], { state: this.state }); }
    else {
      this.router.navigate([this.routerLink]); }
  }

}