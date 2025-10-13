/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, Inject, Injector, OnInit } from '@angular/core';
import { CONFIG } from 'mudeopen-common';
import { StepConfig } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { CompilaIstanzaComponent } from '../compila-istanza/compila-istanza.component';



@Component({
  selector: 'app-formio',
  templateUrl: '../compila-istanza/compila-istanza.component.html',
  styleUrls: ['../compila-istanza/compila-istanza.component.css']
})
export class FormioComponent extends CompilaIstanzaComponent implements OnInit {

  constructor(mudeopenFoBeService: MudeopenFoBeService,
                injector: Injector,
                @Inject(CONFIG) injConfig: StepConfig) {
    super(mudeopenFoBeService, injector, injConfig);

    this.displayModeWithoutTabs = true;
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns 
	*/ 

  ngOnInit() {
    this.tabMainIndexSelected = 0;
    this.tabSubIndexSelected = -1;
    this.tabsMain = [ {
      "key": this.quadro.tipo_quadro.cod_tipo_quadro,
      "id_quadro": "~" + this.quadro.id_quadro + "~",
      "label": (this.quadro.json_configura_quadro as any).label,
      "title": (this.quadro.json_configura_quadro as any).label
    } ];
  }
}
