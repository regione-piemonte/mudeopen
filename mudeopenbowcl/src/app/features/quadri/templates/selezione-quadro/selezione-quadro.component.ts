/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $: any;



import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';



@Component({
  selector: 'app-selezione-quadro',
  templateUrl: './selezione-quadro.component.html',
  styleUrls: ['./selezione-quadro.component.scss']
})
export class SelezioneQuadroComponent implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public id_quadro: string;
  public ordinamento_template_quadro: string;
  public parameter_String: string;

  quadri: model.QuadroVO[];
  quadriFomrioList: model.SelectVO[] = [];

  constructor(public mudeopenFoBeService: MudeopenFoBeService) {
  }

  ngOnInit() {
    this.mudeopenFoBeService.listaQuadri(JSON.stringify({
      "result_type": { "eq": 'essential' }
    }), 0, 10000, '+mudeDTipoQuadro_codTipoQuadro, -numVersione').subscribe(x => {
      this.quadri = x.body;
      this.quadriFomrioList = x.body.map(x => { return { id: ''+x.id_quadro, value: (x.tipo_quadro.cod_tipo_quadro + ' (versione ' + x.num_versione + ')') } });
    });
  }

  onSubmit(isOk: boolean = false) {
    const data = !isOk?null : { 
            quadro: this.quadri.find(quadro => ''+quadro.id_quadro == this.id_quadro), 
            ordinamento_template_quadro: this.ordinamento_template_quadro ,
            parameter_string: this.parameter_String
          };

    this.confirmEvent.emit(data);
  }
}
