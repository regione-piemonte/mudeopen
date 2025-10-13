/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

declare var $$: any;

import { Component, OnInit, SecurityContext } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from 'buildfiles/environment.local-rp-01';


@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.css']
})
export class StepperComponent {

  constructor(public sanitizer: DomSanitizer) {
    
  }

  get url() {
    return this.sanitizer.sanitize(SecurityContext.HTML, environment?.frontoffice + "/fascicolo/istanza?viewMode=backoffice&idIstanza=3253");
  }

}
