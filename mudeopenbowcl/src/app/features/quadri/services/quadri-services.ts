/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as model from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { CComplessoComponent } from '../create/categorie/c-complesso/c-complesso.component';
import { FFormioComponent } from '../create/categorie/f-formio/f-formio.component';
import { RReactiveComponent } from '../create/categorie/r-reactive/r-reactive.component';
import { SelezioneTipoComponent } from '../create/selezione-tipo/selezione-tipo.component';


@Injectable()
export class QuadriService {
  constructor(private http: HttpClient, 
          private modalService: NgbModal,
          private mudeopenFoBeService: MudeopenFoBeService) {
  }

  getTipoGestione(flag: string): string {
      return flag === 'C'? 'Complesso' : 
                    ( flag === 'F'? 'Formio' : 
                        ( flag === 'T'? 'Tabella' : 'Reactive Form'))
  }

  getQuadroLabel(quadro): string {
    return quadro.json_configura_quadro?.label && ('`' + quadro.json_configura_quadro?.label + '`');
  }

  requestDataDialog(msg: string, comp: any, data: any, callback: any, readOnly: boolean = false) {
    const modal = this.modalService.open(comp, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });
    if(readOnly) {
      modal.componentInstance.isReadonly = readOnly; }
    modal.componentInstance.data = data;
    modal.componentInstance.messageAction = msg;
    modal.componentInstance.confirmEvent.subscribe((res) => {
      if(res) { callback(res); }

      modal.dismiss()
    });
  }

  selectNewQuadroType(msg: string, callback: any, data: model.TipoQuadroVO = null) {
    this.requestDataDialog(msg, SelezioneTipoComponent, data, (res) => {
      this.openQuadroDialog(msg, res.categoria_quadro.id, { tipo_quadro: res.tipo_quadro }, callback);
    });
  }

  openQuadroDialog(msg: string, categoryId: string, quadro: any, callback: any = null, readOnly: boolean = false) {
    this.requestDataDialog(msg, categoryId === 'C'? CComplessoComponent : 
                                ( categoryId === 'F'? FFormioComponent : 
                                                    RReactiveComponent ), 
                            quadro, (quadro) => {
      if(!readOnly) {
        this.mudeopenFoBeService.salvaQuadro(quadro.quadroVO, quadro.file).subscribe(quadroRes => {
          if(!!callback) {
            callback(quadroRes); }
        }) }
    }, readOnly);
  }

}

