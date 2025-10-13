import {
  AfterViewInit,
  Component,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import {
  IstanzaVO,
  FormUtils,
  MessageService,
  MudeopenFoBeService,
  FascicoloVO,
} from 'mudeopen-common';
import { RicercaService } from 'src/app/shared/services/ricerca.service';
import * as model from 'mudeopen-common';
import { FormControl, FormGroup } from '@angular/forms';
import { TableColumn } from '@swimlane/ngx-datatable';
import { takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Permissions } from '../../../../core/enum/permissions.enum';
import { FascicoliService } from '../../services/fascicoli.service';

@Component({
  selector: 'app-lista-fascicoli',
  templateUrl: './lista-fascicoli.component.html',
  styleUrls: ['./lista-fascicoli.component.scss'],
})
export class ListaFascicoliComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;
  @ViewChild('expandRowTemplate') expandRowTemplate: TemplateRef<any>;
  @ViewChild('istancesDetailRow') istancesDetailRow: TemplateRef<any>;
  statiFiltro: any;
  stato = '';
  columnsFascicoli: TableColumn[];
  Permission = Permissions;
  constructor(
    public messageService: MessageService,
    private ricercaService: RicercaService,
    public mudeopenFoBeService: MudeopenFoBeService,
    private router: Router,
    private fascicoliService: FascicoliService
  ) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = false;
    this.ricercaFascicoliForm = this.initFascicoliForm();
  }

  pageNum;
  numberEl;
  rows: FascicoloVO[] = [];
  rowExpanded: any;
  isSubmitted = false;
  fascicolo: any;
  istanzeFascicolo: any;
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
  ricercaFascicoliForm: FormGroup;
  tipologieIstanza: model.SelectVO[];
  tipologiaIntervento: model.SelectVO[];
  statoIstanzaList: model.SelectVO[];
  statoFascicoloList: model.SelectVO[];
  provincePiemonte: model.ProvinciaVO[];
  searchSelected = 0;

  ngOnInit(): void {
    this.mudeopenFoBeService
      .getTipologieIntervento()
      .then((x) => (this.tipologiaIntervento = x));

    this.mudeopenFoBeService
      .getSessionData('statoFascicoloList')
      .then((x) => (this.statoFascicoloList = x));

    this.mudeopenFoBeService
      .getProvince('{"idRegione":{"eq":"1"}}')
      .pipe(takeUntil(this.destroy$))
      .subscribe((x) => {
        this.provincePiemonte = x;
      });

    this.statiFiltro = new Map([
      ['CLS', 'CHIUSO'],
      ['OPN', 'APERTO'],
      ['', 'TUTTI'],
    ]);
  }

  ngAfterViewInit() {
    this._initCol();
  ////
  if(this.fascicoliService.getFormItems()!= null) {

    this.ricercaFascicoliForm.patchValue(this.fascicoliService.getFormItems())
    this.ricercaFascicoliForm.get('id_provincia').valueChanges.subscribe((data)=>{
      this.onChangeProvincia(this.fascicoliService.getFormItems().id_provincia.id)

    })
      this.ricercaFascicoliForm.get('id_provincia').get('id').setValue(this.fascicoliService.getFormItems().id_provincia.id)
   
   
    this.onSearch()
   }

   ////

  }

  initFascicoliForm(): FormGroup {
    return new FormGroup({
      id_fascicolo: new FormGroup({
        id: new FormControl(),
        value: new FormControl(),
      }),
      id_tipo_intervento: new FormGroup({
        id: new FormControl(),
        value: new FormControl(),
      }),
      id_pm: new FormControl(),
      id_provincia: new FormGroup({
        id: new FormControl(),
        value: new FormControl(),
      }),
      id_comune: new FormGroup({
        codBelfiore: new FormControl(),
        id: new FormControl(),
        value: new FormControl(),
      }),
      id_dug: new FormGroup({
        id: new FormControl(),
        value: new FormControl(),
      }),
      duf: new FormControl(),
      stato: new FormGroup({
        id: new FormControl(),
        value: new FormControl(),
      }),
      id_intestatario_pf: new FormControl(),
      id_intestatario_pg: new FormControl(),
      codice_fascicolo: new FormControl(),
      data_creazione_from: new FormControl(),
      data_creazione_to: new FormControl(),
    });
  }

  wrongDateInterval: string;

  downloadExcel() {
    this.ricercaService.excelFascicoli(this.fascicolo, 0, 100000);
  }

  onSearch(page: any = 0) {
     ///
     this.fascicoliService.setFormItems(this.ricercaFascicoliForm.value)
     ////
    if (
      this.ricercaFascicoliForm.value.data_creazione_from >
      this.ricercaFascicoliForm.value.data_creazione_to
    ) {
      this.wrongDateInterval = 'Intervallo date';

      setTimeout(() => {
        this.wrongDateInterval = null;
      }, 5000);
      return;
    }

    if (page && page.pageSize) {
      page = page.offset;
    }
    this.isSubmitted = true;
    if (
      this.ricercaFascicoliForm?.value?.id_tipo_intervento.id === 'undefined'
    ) {
      this.ricercaFascicoliForm
        .get('id_tipo_intervento')
        .setValue({ id: null, value: null });
    }
    if (this.stato !== '') {
      this.ricercaFascicoliForm
        .get('stato')
        .setValue({ id: this.stato, value: null });
    }


    this.fascicolo = this.ricercaFascicoliForm?.value;
    this.pageNum = page;

    if (this.ricercaFascicoliForm.valid) {
      this.ricercaService
        .searchFascicoli(
          this.ricercaFascicoliForm.value,
          page,
          this.datatable.limit
        )
        .pipe(takeUntil(this.destroy$))
        .subscribe((response: any) => {
          //  console.log(response.body[0].intestatario.anagrafica);
          this.completeInfo(response.body);
          this.rows = response.body;
          this._handlePaging(response);
        });
    }
    this.stato = '';
 
  }

  filtriVeloci(el) {
    this.ricercaFascicoliForm.reset();
    this.stato = el;
    this.onSearch();
  }

  downloadExcelIstanze(fascicolo) {
    this.ricercaService.excelIstanze(
      { id_fascicolo: fascicolo.id_fascicolo },
      0,
      10000
    );
  }

  toggleExpandRow(fascicolo: any, expanded) {
    if (!fascicolo?.id_fascicolo) {
      return;
    }

    this.ricercaService
      .searchIstanze({ id_fascicolo: fascicolo.id_fascicolo }, 0, 10000)
    
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        
        (arr) => {
      
          console.log('fasc', arr.body),
            ((fascicolo as any)._istanzeFascicoloList = (arr.body || []).filter(
              (istanza: any) => {
                console.log(arr);
               /* if (istanza.id_istanza_riferimento) {
                return false;
               }*/

                this.rowExpanded = Object.assign({}, fascicolo);
                console.log(fascicolo);
                /*(istanza as any)._istanzeFiglie = (arr.body || [])
                .filter(
                  (istFiglia: model.IstanzaVO) =>
                    //istFiglia.id_istanza_riferimento === istanza.id_istanza,
                  console.log(arr)
                );
                console.log('ddd', istanza._istanzeFiglie)*/
              //    console.log(fascicolo._istanzeFascicoloList.length);
              // this.istanzeFascicolo = fascicolo?._istanzeFascicoloList;
              // this.numberEl = fascicolo?._istanzeFascicoloList.length;

                return true;
              }
            ));
            console.log(fascicolo?._istanzeFascicoloList)
        },
        (err) => console.log(err)
      );
    this.stato = '';
  }

  completeInfo(lst) {
    lst.map(
      (item) =>
        (item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo))
    );
    lst.map(
      (item) =>
        (item._intestatario = this.getSubjectInfo(
          'name',
          item?.intestatario?.anagrafica
        ))
    );
  }

  rowIdentity(row) {
    return row?.codice_fascicolo;
  }
  navigateToDetailInstance(istanza: any) {
    console.log(istanza);
    this.router.navigate([
      'backoffice',
      'lista-istanze',
      istanza,
      'dettaglio-istanza',
    ]);
  }
  private _initCol() {
    this.columnsFascicoli = [
      {
        name: 'Elenco istanze',
     //   maxWidth: 98,
        canAutoResize: false,
        cellTemplate: this.expandRowTemplate,
      },
      {
        name: 'Numero Fascicolo',
        prop: 'codice_fascicolo',
        resizeable: true,
        sortable: false,
     //   width: 148,
      },
      {
        name: 'Comune',
        prop: 'comune.value',
        resizeable: true,
        sortable: false,
      //  width: 108,
      },
      {
        name: 'Indirizzo',
        prop: '_indirizzo',
        resizeable: true,
        sortable: false,
     //   width: 125,
      },
      {
        name: 'Intestatario',
        prop: '_intestatario',
        resizeable: true,
        sortable: false,
     //   width: 128,
      },
      {
        name: 'Stato',
        prop: 'stato_fascicolo.value',
        resizeable: true,
        sortable: false,
     //   width: 88,
      },
      {
        name: 'Data stato',
        prop: 'data_stato',
        resizeable: true,
        sortable: false,
     //   width: 135,
      },
    ];
  }

  clean(){
    this.fascicoliService.setFormItems(null)
  }
}
