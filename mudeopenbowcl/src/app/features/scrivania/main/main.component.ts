/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import * as model from 'mudeopen-common';
import {
  FormUtils,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { ScrivaniaService } from '../service/scrivania.service';
import { TableColumn } from '@swimlane/ngx-datatable';
import { IstanzaBO } from 'src/app/shared/model/istanzaBO.model';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Permissions } from '../../../core/enum/permissions.enum';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent extends FormUtils implements AfterViewInit {
  @ViewChild('table') table: any;
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;

  rowExpanded: any;
  rows: model.IstanzaVO[] = null;

  permissions = Permissions;

  columnsIstanze: TableColumn[] = [];

  lastActivatedUrl: string;
  constructor(
    public mudeopenFoBeService: MudeopenFoBeService,
    private scrivaniaService: ScrivaniaService,
    public messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    super(mudeopenFoBeService, messageService);
    this.datatable.limit = 10;

    router.events.subscribe(params => {
      if(params instanceof NavigationEnd) {
        if(route.routeConfig?.data?.reloadIt)
          route.routeConfig.data.reloadIt = this.lastActivatedUrl = null;

        if(params.url?.endsWith("/scrivania") && !this.lastActivatedUrl?.endsWith("/scrivania")) {
            this.loadPage(); 
            this.lastActivatedUrl = params.url;
          }
        else if(params.url?.indexOf("/dettaglio-istanza") == -1) {
          this.lastActivatedUrl = params.url; }
      }
    });
  }

  booleanPipe = {
    transform: (value) => value ? 'Si' : 'No',
  };

  ngAfterViewInit() {
    this._initTable();
  }

  downloadExcel() {
    this.scrivaniaService.downloadExcel();
  }

  loadPage(page: any = 0) {
    if (page && page.pageSize) {
      page = page.offset;
    }
    this.scrivaniaService
      .getIstanzeDepositate(page, this.datatable.limit)
      .subscribe((x:any) => {
        this.completeInfo(x.body);
        this.rows = x.body;

        setTimeout(() => {
          this.toggleExpandRow(
            this.rows.find((t) => t.id_istanza === this.rowExpanded),
            false,
          );
        });
        this._handlePaging(x);
      });
  }
  rowIdentity(row) {
    return row?.id_istanza;
  }
  navigateToDetailInstance(istanza: IstanzaBO) {
    console.log(istanza.id_istanza);
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza']);
  }

  navigateToStepperInstance(istanza: IstanzaBO) {
    console.log(istanza.id_istanza);
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza-stepper']);
  }

  completeInfo(lst) {
    lst.map(
      (item) =>
        (item._intestatario = this.getSubjectInfo(
          'name',
          item.intestatario?.anagrafica
        )),
        lst.map(item => item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo)),

    );
    lst.map(
      (item) =>
        (item._tipo = item.tipologia_istanza?.value + ' - ' + (item.specie_pratica?.value ? item.specie_pratica?.value : ''))
    );
  }

  
  private _initTable() {
    this.columnsIstanze = [
      {
        name: 'Azioni',
        maxWidth: 98,
        canAutoResize: false,
        cellTemplate: this.actionsTemplate,
      },
      { name: 'Numero istanza', prop: 'codice_istanza', width: 148 },
      { name: 'Tipo istanza', prop: '_tipo', width: 200 },
      { name: 'Intestatario', prop: '_intestatario', width: 128 },
      { name: 'Comune', prop: 'comune.value', width: 108 },
      { name: 'Indirizzo', prop: '_indirizzo', width: 125 },
      {
        name: 'Data Ricezione',
        prop: 'data_stato_dps',
        width: 125,
      },
      { name: 'Stato', prop: 'stato_istanza.value', width: 88 },
      { name: 'Data stato', prop: 'data_creazione', width: 135 },
      {
        name: 'Occupazione suolo pubblico',
        prop: 'occupazione_suolo_pubblico',
        pipe: this.booleanPipe,
        width: 128,
      },
    ];
  }

  // Open/close panel
  public toggleExpandRow(row: any, expanded) {
    if (!row?.id_istanza) {
      return;
    }

    this.rowExpanded = expanded ? null : row.id_istanza;

    this.toggleTemplateDetails(row, expanded);
  }

  public toggleTemplateDetails(row: any, expanded: boolean) {
    if (!expanded) {
      console.log(row);
      this.table.rowDetail.collapseAllRows();
      this.table.rowDetail.toggleExpandRow(row);
    } else if (expanded) {
      this.table.rowDetail.collapseAllRows();
    }
  }

  public onTempStateChange(instance: model.IstanzaVO) {
    // const modal = this.modalService.open(DettaglioComponent, {
    //   size: 'xl',
    //   backdrop: 'static',
    //   keyboard: false,
    //   windowClass: 'alwaysScrollModal',
    // });
    // modal.componentInstance.istanza = instance;
    // modal.componentInstance.onConfirm.subscribe((data) => {
    //   const inst = {
    //     'anno': parseInt((''+instance.data_registrazione_pratica).replace(/[0-9]{2,2}\/[0-9]{2,2}\/([0-9]{4,4}).*/, '$1'), 10),
    //     'numero_pratica': data.numeroProtocollo,
    //     'numero_protocollo': data.numeroProtocollo,
    //     'data_protocollo': data.dataProtocollo
    //   }
    // });
  }
}
