/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormUtils, IstanzaVO, MessageService, MudeopenFoBeService, SelectVO } from 'mudeopen-common';

import * as model from 'mudeopen-common';
import { FormsService } from '../../services/forms.service';

declare var $$: any;

@Component({
  selector: 'app-seleziona-nuova-instaza',
  templateUrl: './seleziona-nuova-instaza.component.html',
  styleUrls: ['./seleziona-nuova-instaza.component.css']
})
export class SelezionaNuovaInstazaComponent extends FormUtils implements OnInit {

  public idComune: number = null;
  public idIstanza: number = null;
  public idFascicolo: number = null;

  instanceSelection: number;
  noInstanceTypeAvailable: boolean;

  columnsIstanze: any = [
    {name: 'Tipo istanza', prop: 'tipologia_istanza.value', width: '148'},
    {name: 'Codice', prop: 'codice_istanza', width: '148'},
    {name: 'Intestatario', prop: 'fascicolo.intestatario.nome', width: '128'},
    {name: 'Data presentaz.', prop: 'data_registrazione_pratica', width: '128'}
  ];


  @Output('confirmEvent') confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  constructor(public mudeopenFoBeService: MudeopenFoBeService, 
              private messages: MessageService) {
    super(mudeopenFoBeService, messages);

    this.datatable.limit = 1000; // pagination not supported by the API
  }

  _boTemplateOverride: any;
  tipologieIstanza: model.TipoIstanzaVO[];
  tipologieIstanzaSelected: model.TipoIstanzaVO;

  disabledTipoIstanzaSelect: boolean;
  ngOnInit() {
    this.mudeopenFoBeService.recuperaTipologieIstanze(this.idComune, this.idIstanza, this.idFascicolo).subscribe(x => {
      this.tipologieIstanza = x;

      if($$.isBOUser && this._boTemplateOverride?.parameter) {
        this.disabledTipoIstanzaSelect = true;
        this.tipologieIstanzaSelected = this.tipologieIstanza.find(
                  (tipoIst: model.TipoIstanzaVO) => tipoIst.id == this._boTemplateOverride.tipologia_istanza);
        if(this.tipologieIstanzaSelected) { 
          this.onChangeSelection(); }
      }
  
      this.closeIfNoInstancesInFolder();
    });
  }

  get boTemplateInfo(): string {
    return this._boTemplateOverride.tipologia_istanza +' (ver: '+this._boTemplateOverride.template_ver+')';
  }

  closeIfNoInstancesInFolder() {
    this.mudeopenFoBeService.istanzeFascicolo(this.idFascicolo).subscribe(arr => { 
      if(!arr?.length) {  // no instances, so no selection is required
        this.confirmEvent.emit(true); }
    });
  }

  onChangeSelection(event = null) {
    this.instanceSelection = (this.tipologieIstanzaSelected.legata ? 1 : 0) + 
                              (this.tipologieIstanzaSelected.almeno_un_ruolo ? 2 : 0);

    this.noInstanceTypeAvailable = this.instanceSelection == 0;

    if(this.isReferenceInstanceRequired) {
      this.onSearch();
    }
  }

  displayedRowsIstanze: Array<IstanzaVO> = null;
  onSearch(page: any = 0) {
    this.mudeopenFoBeService.listaPossibiliIstanze(this.idFascicolo, this.idIstanza, this.tipologieIstanzaSelected.id, page, this.datatable.limit).subscribe(x => {
      this._handlePaging(x);
      this.displayedRowsIstanze = x.body;
      this.noInstanceTypeAvailable = !this.displayedRowsIstanze?.length;
    });
  }

  get isReferenceInstanceRequired(): boolean {
    return (this.instanceSelection & 1) === 1;
  }

  get isTopLevelInstanceAllowed(): boolean {
    return (this.instanceSelection & 2) === 2;
  }

  onExit() {
    this.confirmEvent.emit(null);
  }

  onSubmit(istanzaParent: model.IstanzaVO = null) {
    this.confirmEvent.emit({
      tipologiaIstanza: this.tipologieIstanzaSelected,
      istanzaParent: istanzaParent
    });
  }


}
