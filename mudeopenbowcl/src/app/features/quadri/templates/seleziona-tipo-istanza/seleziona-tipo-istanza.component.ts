/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $: any;


import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';


@Component({
  selector: 'app-seleziona-tipo-istanza',
  templateUrl: './seleziona-tipo-istanza.component.html',
  styleUrls: ['./seleziona-tipo-istanza.component.scss']
})
export class SelezionaTipoIstanzaComponent implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  id_tipo_istanza: string;
  tipoIstanzaList: model.SelectVO[] = [];

  constructor(public mudeopenFoBeService: MudeopenFoBeService) {
  }

  ngOnInit() {
    this.mudeopenFoBeService.getTipologieIstanza().then(x => {
      this.tipoIstanzaList = x;
    });
  }

  onSubmit(isOk: boolean = false) {
    this.confirmEvent.emit(this.id_tipo_istanza);
  }
}
