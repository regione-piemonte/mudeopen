/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/


declare var $$: any;

import { Component, OnInit, Input } from '@angular/core';
import { FormUtils } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { FormsService } from '../../../features/fascicoli/services/forms.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-lista-stati',
  templateUrl: './lista-stati.component.html',
  styleUrls: ['./lista-stati.component.css']
})
export class ListaStatiComponent extends FormUtils  implements OnInit {

  @Input() public idIstanza: number;

  constructor(private formsService: FormsService, 
      mudeopenFoBeService: MudeopenFoBeService,
      private modalService: NgbModal) {
    super(mudeopenFoBeService, null);
  }

  isLoading: boolean;
  listaStati: model.IstanzaStatoVO[];
  ngOnInit() {
    this.isLoading = true;
    this.mudeopenFoBeService.getStatiIstanza(this.idIstanza).subscribe((stati: Array<model.IstanzaStatoVO>) => {
      this.isLoading = false;
      this.listaStati = stati;
    });
  }

  onExit() {
    this.modalService.dismissAll();
  }

}
