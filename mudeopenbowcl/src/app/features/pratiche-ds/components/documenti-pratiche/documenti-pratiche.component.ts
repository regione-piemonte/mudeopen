import {
  AfterViewInit,
  Component,
  OnInit,
  TemplateRef,
  ViewChild,
  LOCALE_ID,
  Inject,
} from '@angular/core';
import { Location } from '@angular/common';

import { PraticheService } from '../../services/pratiche.service';
import {
  AllegatoVO,
  FormUtils,
  IstanzaVO,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { RicercaService } from '../../../../shared/services/ricerca.service';
import { TableColumn } from '@swimlane/ngx-datatable';
import { formatDate } from '@angular/common';
import * as model from 'mudeopen-common';
import { CambioStatoComponent } from '../../../gestione/istanze/cambio-stato/cambio-stato.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Permissions } from '../../../../core/enum/permissions.enum';
import { IstanzaService } from 'src/app/features/istanze/services/istanza.service';

@Component({
  selector: 'app-documenti-pratiche',
  templateUrl: './documenti-pratiche.component.html',
  styleUrls: ['./documenti-pratiche.component.scss'],
})
export class DocumentiPraticheComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;
  Permissions = Permissions;
  pratica: any;
  idFascicolo: any;

  rows: AllegatoVO[] = [];
  columns: TableColumn[];

  constructor(
    private praticheService: PraticheService,
    private activatedRoute: ActivatedRoute,
    private istanzaService: IstanzaService,
    private router: Router,
    private location: Location,
    private route:ActivatedRoute,
    public messageService: MessageService,
    public mudeopenFoBeService: MudeopenFoBeService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super(mudeopenFoBeService, messageService);
    this.idFascicolo = route.snapshot.paramMap.get('idfascicolo');
  }

  ngOnInit(): void {
    this.pratica = this.activatedRoute.snapshot.data['pratica'].body[0];

    if(this.pratica != null) {
      this.onSearch();
      this._loadIstanzeFromFascicolo();
    }
    else {
      this.pratica = {};
    }
  }

  ngAfterViewInit() {
    this._initCol();
  }

  istanzeList: model.IstanzaVO[] = [];
  _loadIstanzeFromFascicolo() {
    this.mudeopenFoBeService.istanzeFascicolo(this.idFascicolo, { numeroPratica: this.pratica.numero_pratica}).subscribe(istanze => { 
      this.istanzeList = istanze;
    });
  }

  loadAllegatiIstanza(istanza: any) {
    this.mudeopenFoBeService.loadAllegatiIstanza(istanza.id_istanza).subscribe((allegati: Array<model.AllegatoVO>) => {
      istanza._allegati = allegati;      
      istanza._toogleView = true;
    });

  }

  getAttachCategories(istanza: any) {
    if(istanza._categories) {
      return istanza._categories; }

    istanza._categories = [];
    (istanza._allegati||[]).forEach(item => {
      const attach = {
        "name": item.nome_file_allegato + (!item.protocollo?'':' <span class="protocolAllegato">(protocollo: '+item.protocollo+' - '+item.data_protocollo+')</span>'),
        "nodeIndex": item.index_node,
        "size": item.dimensione_file
      };

      const category = istanza._categories.find(x => x.tipo_allegato.codice_tipo_allegato == item.tipo_allegato.codice_tipo_allegato);
      if(!category) {
        istanza._categories.push({
          tipo_allegato: item.tipo_allegato,
          attaches: [ attach ]
        });
      } else {
        category.attaches.push(attach); }
    });
  }

  toggleAttachments(index: number) {
    const istanza:any = this.istanzeList[index];
    if(!istanza._allegati) {
      return this.loadAllegatiIstanza(istanza); }

    istanza._toogleView = !istanza._toogleView;
  }
/*
  peekFilesData(istanza: any, attach: any): any {
    const category = attach.sub_cod_tipo_allegato || attach.codice_tipo_allegato;

    return (istanza._allegati || []).filter(item => {
      return (item.sub_cod_tipo_allegato || item.codice_tipo_allegato) == category;
    });
  }
*/
  download(file: any) {
    this.mudeopenFoBeService.downloadAllegato(file.nodeIndex).subscribe(x => {
      this.download2user(x);
    });
  }

  onSearch(page: any = 0) {
    if (page && page.pageSize) {
      page = page.offset; }

    this.praticheService.documentiPratica(this.pratica.id_pratica,
                                          page,
                                          this.datatable.limit)
                              .pipe(takeUntil(this.destroy$))
                              .subscribe((response: any) => {
        this.rows = response.body;
        this._handlePaging(response);
      });
  }

  viewDocument(row) { this.praticheService.downloadDocumento(row.index_node); }
  goBack() { this.location.back(); }

  deleteDocument(row: model.AllegatoVO) {
    this.praticheService
      .deleteDocumento(row.id)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response) => {
        console.log(response);
        this.onSearch();
      });
  }

  onDeleteDocumento(row: any) {
    this.confirmDialog(
      `Confermi di voler eliminare il documento "${row.nome_file_documento}"`,
      () => {
        this.deleteDocument(row);
      },
      'Elimina'
    );
  }

  private _initCol() {
    this.columns = [
      {
        name: 'Azioni',
        canAutoResize: false,
        cellTemplate: this.actionsTemplate,
        maxWidth: 98,
      },
      {
        name: 'Tipo documento',
        prop: 'tipo_documento.descrizione_tipo_docpa',
        resizeable: true,
        sortable: false,
      },
      {
        name: 'Nome file',
        prop: 'nome_file_documento',
      },
      {
        name: 'Data caricamento',
        prop: 'data_caricamento',
        pipe: {
          transform: (value: string) => formatDate(value, 'dd/MM/yyyy - HH:mm', this.locale),
        },
      },
      {
        name: 'Numero protocollo',
        prop: 'numero_protocollo'
      },
      {
        name: 'Data protocollo',
        prop: 'data_protocollo'
      },
    ];
  }

  downloadAllegato(firma: boolean, row) {
    this.istanzaService.downloadIstanza(firma, row.id_istanza);
  }

  uploadDocumento() {
    this.router.navigate([
      'backoffice',
      'pratiche-ds',
      this.pratica.id_pratica,
      'documenti-ds',
      this.idFascicolo,
      'upload',
    ]);
  }


}
