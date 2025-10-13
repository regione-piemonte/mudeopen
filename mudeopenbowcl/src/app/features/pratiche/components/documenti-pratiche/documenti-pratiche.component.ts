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
  // TODO creare oggetto PraticaVO
  pratica: any;

  rows: AllegatoVO[] = [];
  datatable: any = {
    offset: 0,
    count: 0,
    limit: 10,
    selected: [],
    pages: 0,
    messages: {
      emptyMessage: 'Non è stato trovato nessun dato',
      totalMessage: 'Record totali',
    },
  };
  columns: TableColumn[];
  pageNum;

  pipeFormatDate = {
    transform: (value: string) =>
      formatDate(value, 'dd/MM/yyyy - HH:mm', this.locale),
  };
  booleanPipe = {
    transform: (value) => value ? 'Si' : 'No',
  };

  constructor(
    private praticheService: PraticheService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private location: Location,
    public messageService: MessageService,
    public mudeopenFoBeService: MudeopenFoBeService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super(mudeopenFoBeService, messageService);
  }

  ngOnInit(): void {
    this.pratica = this.activatedRoute.snapshot.data['pratica'].body[0];
    console.log(this.pratica);
    this.onSearch();
  }

  ngAfterViewInit() {
    this._initCol();
  }

  onSearch(page: any = 0) {
    if (page && page.pageSize) {
      page = page.offset;
    }
    this.pageNum = page;
    console.log(this.pratica.id_pratica
      )

    this.praticheService
      .documentiPratica(this.pratica.id_pratica
        ,
        page,
        this.datatable.limit
      )
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: any) => {
        this.rows = response.body;
        this._handlePaging(response);
      });
  }

  viewDocument(row) {
    this.praticheService.downloadDocumento(row.index_node);
  }

  deleteDocument(row: model.AllegatoVO) {
    this.praticheService
      .deleteDocumento(row.id)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response) => {
        console.log(response);
        this.onSearch();
      });
  }
goBack() {
  this.location.back();
}
  uploadDocumento() {
    this.router.navigate([
      'backoffice',
      'lista-pratiche',
      this.pratica.id_pratica,
      'documenti',
      'upload',
    ]);
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
        pipe: this.pipeFormatDate,
      },
      {
        name: 'Notificato',
        prop: 'notificato',
        pipe: this.booleanPipe,
      },
    ];
  }
}
