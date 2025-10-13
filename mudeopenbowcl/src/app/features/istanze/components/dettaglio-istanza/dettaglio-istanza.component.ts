import {
  AfterViewInit,
  Component,
  OnInit,
  PipeTransform,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import {
  AllegatoVO,
  FormUtils,
  IstanzaVO,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { Permissions } from '../../../../core/enum/permissions.enum';

declare var $: any;
declare var $$: any;
import { Location } from '@angular/common';
import { RicercaService } from 'src/app/shared/services/ricerca.service';
import { TableColumn } from '@swimlane/ngx-datatable';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, takeUntil } from 'rxjs/operators';
import { IstanzaBO } from 'src/app/shared/model/istanzaBO.model';
import { IstanzaService } from '../../services/istanza.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ListaStatiComponent } from 'projects/frontoffice/src/app/features/fascicoli/lista-stati/lista-stati.component';

@Component({
  selector: 'app-dettaglio-istanza',
  templateUrl: './dettaglio-istanza.component.html',
  styleUrls: ['./dettaglio-istanza.component.css'],
})
export class DettaglioIstanzaComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;
  Permissions = Permissions;
  istanza: IstanzaBO;
  allegati: any;

  constructor(
    private router: Router,
    private ricercaService: RicercaService,
    public mudeopenFoBeService: MudeopenFoBeService,
    public messageService: MessageService,
    private istanzaService: IstanzaService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    private location: Location
  ) {
    super(mudeopenFoBeService, messageService);
  }

  columns: TableColumn[];
  pageNum;
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

  ngOnInit(): void {
    this.istanza = this.activatedRoute.snapshot.data['istanza'];

    if(!(this.istanza as any).extra_columns)
      (this.istanza as any).extra_columns = {};
    else if(typeof (this.istanza as any).extra_columns == 'string')
      (this.istanza as any).extra_columns = JSON.parse((this.istanza as any).extra_columns);

    this.onSearch();
  }

  get isIstanzaPDFDownaloadable(): boolean {
    return this.istanza?.visualizza;
  }

  onOpenListaStati() {
    const modal = this.modalService.open(ListaStatiComponent, { size: 'lg', backdrop: 'static', keyboard: false });
    modal.componentInstance.idIstanza = this.istanza.id_istanza;
  }

  navigateToStepperInstance() {
    console.log(this.istanza.id_istanza);
    this.router.navigate(['backoffice', 'lista-istanze', this.istanza.id_istanza, 'dettaglio-istanza-stepper']);
  }

  ngAfterViewInit() {
    this._initCol();
  }

  onSearch(page: any = 0) {
    if (page && page.pageSize) {
      page = page.offset;
    }
    this.pageNum = page;

    this.istanzaService
      .getAllegati(
        this.activatedRoute.snapshot.paramMap.get('id'),
        page,
        this.datatable.limit
      )
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: any) => {
        this.getInfo(response.body);
        this.rows = response.body;

        this.localPagination(this.rows, page);
      });
  }
  getInfo(el: any) {
    el.map((item) => {
      console.log(el.nome_file_allegato);
      item._group = item.nome_file_allegato.substring(0, 3);
    });
  }

  rigeneraOK: boolean;
  generaTracciato() {
    this.istanzaService
      .getGeneraTracciato(this.istanza.id_istanza)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: any) => {
          this.rigeneraOK = true;
          this.istanza.genera_tracciato = false;
      	  // this.router.navigate(['backoffice', 'lista-istanze', this.activatedRoute.snapshot.paramMap.get('id'), 'dettaglio-istanza'], { queryParams: { lastUpdate: Date.now() } });
      },
      (err) => {
        this.istanza.genera_tracciato = true;
      });
  }
  localPagination(rows: any, page: number) {
    this.datatable.pages = this.datatable.count / this.datatable.limit;
    this.datatable.count = rows.length;
    if (page != 0) {
      let arr = [];
      let startPage = 0;
      let endPage = 0;
      for (let j = 0; j < page; j++) {
        startPage = page * this.datatable.limit;
        endPage = startPage + this.datatable.limit;
      }
      for (let i = startPage; i < endPage; i++) {
        arr.push(this.rows[i]);
      }

      this.rows = arr;
    }
  }

  goBack() {
    this.location.back();
    //this.router.navigate(['backoffice', 'lista-istanze']);
  }

  downloadAllegatiExcel() {
    this.istanzaService.downloadExcelAllegati(this.istanza.id_istanza);
  }

  downloadIstanzaFirma(firma: boolean) {
    this.istanzaService.downloadIstanza(firma, this.istanza.id_istanza);
  }

  onModificaDatiExtra() {
    console.log(this.istanza.id_istanza);
    this.router.navigate(['backoffice', 'lista-istanze', 'modifica-dati-istanza', this.istanza.id_istanza]);
  }

  downloadAllegato(firma: boolean, row) {
    this.istanzaService.downloadAllegato(firma, row.index_node);
  }

  ricevutaRegistrazione() {
    // const userId = JSON.parse(sessionStorage.getItem('infoProfilo'));
    // const userCF = userId.infoUtente.contatto.anagrafica.codiceFiscale;
    this.istanzaService.downloadRicevuta(this.istanza.id_istanza);
  }
  notifica() {
    this.router.navigate([
      'backoffice',
      'lista-istanze',
      this.istanza.id_istanza,
      'invio-notifica',
    ]);
  }

  cambiaStato() {
    this.router.navigate([
      'backoffice',
      'lista-istanze',
      this.istanza.id_istanza,
      'cambio-stato',
    ]);
  }
  private _initCol() {
    this.columns = [
      {
        name: 'Azioni',
        canAutoResize: false,
        cellTemplate: this.actionsTemplate,
        maxWidth: 100,
      },
      {
        name: 'Gruppo',
        prop:'tipo_allegato.categoria_allegato.descrizione_estesa_categoria_allegato',
        resizeable: true,
        sortable: false,

        width: 148,
      },
      {
        name: 'Tipo Allegato',
        prop: 'tipo_allegato.descrizione_breve_tipo_allegato',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      {
        name: 'Nome File',
        prop: 'nome_file_allegato',

        width: 148,
      },
    ];
  }


  searchSelected: number = 0;
  selectForm(tabIndex: number) {
    this.searchSelected = tabIndex;
  }

  extraTabs: any;
  get additionalTabs(): any {
    if(!this.extraTabs) {
      this.extraTabs = Object.keys((this.istanza as any).extra_columns || {})
            .filter(key => key.match(/^TB[0-9]$/) && !!Object.keys(this.getColumns(key) || {}).length)
            .map(key => { 
              return { type: key, label: (this.istanza as any).extra_columns[key]};
      });

    }

    return this.extraTabs;
  }

  getColumns(type) {
    const raw = (this.istanza as any).extra_columns || {};
    const filtered = Object.keys(raw)
    .reduce((obj, key) => {
      if(key.startsWith(type + '-')) {
        obj[key.substring(4)] = raw[key];
      }

      return obj;
    }, {});

    return filtered;
  }  

  get hasPratica() : boolean {
    return !!(this.istanza as any).idPratica;
  }

  isIdfPresent(istanza: any): boolean {
    return !!istanza.idf_index_node;
  }

  isIdfApproved(istanza: any): string {
    return istanza?.idf_autorizzato != 'S'? '' : (istanza?.idf_autorizzato?'concessa':'negata');
  }

}
