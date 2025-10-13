/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'div-html',
  templateUrl: './html-container.component.html',
  styleUrls: ['./html-container.component.scss']
})
export class HtmlContainerComponent {
  @Input('innerHTML') innerHTML: any;
  
  constructor(public sanitizer: DomSanitizer) { }

}
