import {
  AfterViewInit,
  Component,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';

declare var $: any;
declare var $$: any;

import { FormControl, FormGroup } from '@angular/forms';
import {
  FormUtils,
  IstanzaVO,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { RicercaService } from 'src/app/shared/services/ricerca.service';
import * as model from 'mudeopen-common';
import { TableColumn } from '@swimlane/ngx-datatable';
import { takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';
import { IstanzaBO } from 'src/app/shared/model/istanzaBO.model';
import { Permissions } from '../../../../core/enum/permissions.enum';
import { IstanzaService } from '../../services/istanza.service';

@Component({
  selector: 'app-lista-istanze',
  templateUrl: './lista-istanze.component.html',
  styleUrls: ['./lista-istanze.component.css'],
})
export class ListaIstanzeComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;
  permissions = Permissions;
  constructor(
    public messageService: MessageService,
    private ricercaService: RicercaService,
    public mudeopenFoBeService: MudeopenFoBeService,
    private router: Router,
    private istanzeService: IstanzaService
  ) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = false;
    this.ricercaIstanzeForm = this.initIstanzaForm();
  }

  rowExpanded: any;
  check = 'aaaa';
  tipologieIstanza: model.SelectVO[];
  tipologiaIntervento: model.SelectVO[];
  statoIstanzaList: model.SelectVO[];
  statoFascicoloList: model.SelectVO[];
  provincePiemonte: model.ProvinciaVO[];
  isSubmitted = false;
  ricercaIstanzeForm: FormGroup;
  istanzeFormValue: any;
  pageNum;
  stato = '';
  statiFiltro: any;
  rows: IstanzaBO[] = [];
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

  booleanPipe = {
    transform: (value) => (value ? 'Si' : 'No'),
  };

  indirizzoPipe = {
    transform: (value) =>
      this.getIndirizzo(value.ubicazione || value.indirizzo),
  };

  columnsIstanze: TableColumn[];

  searchSelected = 0;

  ngOnInit(): void {
    this.mudeopenFoBeService
      .getTipologieIntervento()
      .then((x) => (this.tipologiaIntervento = x));
    this.mudeopenFoBeService
      .getTipologieIstanza()
      .then((x) => (this.tipologieIstanza = x));

    this.mudeopenFoBeService
      .getSessionData('statoIstanzaList')
      .then(
        (x) => {
          this.statoIstanzaList = [];
          
          x.filter((y: model.SelectVO) => {
            if(y.id !== 'BZZ' && y.id !== 'DFR' && y.id !== 'FRM')
              this.statoIstanzaList.push(y);

            if(y.id == 'APA')
              this.statoIstanzaList.push({ id: '-1', value: '-!- denuncia sismica regionale -'});
          });
        }
      );

    this.mudeopenFoBeService
      .getSessionData('statoFascicoloList')
      .then((x) => (this.statoFascicoloList = x));

    this.mudeopenFoBeService
      .getProvince('{"idRegione":{"eq":"1"}}')
      .pipe(takeUntil(this.destroy$))
      .subscribe((x) => {
        this.provincePiemonte = x;
      });

    this.ricercaService
      .filtriVeloci()
      .pipe(takeUntil(this.destroy$))
      .subscribe((response) => {
        this.statiFiltro = response;
      });
  }

  ngAfterViewInit() {
    this._initCol();

 ////
 if(this.istanzeService.getFormItems()!= null) {
 
      this.ricercaIstanzeForm.patchValue(this.istanzeService.getFormItems())
      this.ricercaIstanzeForm.get('id_provincia').valueChanges.subscribe((data)=>{
        this.onChangeProvincia(this.istanzeService.getFormItems().id_provincia.id)
  
      })
        this.ricercaIstanzeForm.get('id_provincia').get('id').setValue(this.istanzeService.getFormItems().id_provincia.id)
     
      this.onSearch()
     }
  
     ////
  }

  filtriVeloci(el) {
    this.ricercaIstanzeForm.reset();
    this.stato = el;
    this.onSearch();
  }

  initIstanzaForm(): FormGroup {
    return new FormGroup({
      id_tipo_istanza: new FormGroup({
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
      codice_istanza: new FormControl(),
      data_dps: new FormControl(),
    });
  }

  selectForm(tabIndex: number) {
    this.searchSelected = tabIndex;
  }

  rowIdentity(row) {
    return row?.id_istanza;
  }

  navigateToDetailInstance(istanza: any) {
    console.log(istanza.id_istanza);
    this.router.navigate([
      'backoffice',
      'lista-istanze',
      istanza.id_istanza,
      'dettaglio-istanza',
    ]);
  }

  navigateToStepperInstance(istanza: IstanzaBO) {
    console.log(istanza.id_istanza);
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza-stepper']);
  }

  onSearch(page: any = 0) {
    ///
    this.istanzeService.setFormItems(this.ricercaIstanzeForm.value)
    ////
    this.datatable.count = 0;
    if (page && page.pageSize) {
      page = page.offset;
    }

    if (this.stato !== '') {
      this.ricercaIstanzeForm
        .get('stato')
        .setValue({ id: this.stato, value: null });
    }

    this.isSubmitted = true;
    if (this.ricercaIstanzeForm.valid) {
      if (this.ricercaIstanzeForm?.value?.stato?.id === '') {
        this.ricercaIstanzeForm
          .get('stato')
          .setValue({ id: null, value: null });
      }
    }
    this.istanzeFormValue = this.ricercaIstanzeForm?.value;
  

    this.ricercaService
      .searchIstanze(this.ricercaIstanzeForm?.value, page, this.datatable.limit)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: any) => {
        console.log('grgr', response);
        this.completeInfo(response.body);
        this.rows = response.body;
        //console.log(this.rows[0].funzioniUtente)
        this._handlePaging(response);
        this.pageNum = page;
        console.log(this.datatable);
      });

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
          item.intestatario?.anagrafica
        ))
    );
    lst.map(
      (item) =>
        (item._tipo = item.tipologia_istanza?.value + ' - ' + item.specie_pratica?.value)
    );

  }

  downloadExcel() {
    this.ricercaService.excelIstanze(this.istanzeFormValue, 0, 100000);
  }

  private _initCol() {
    this.columnsIstanze = [
      {
        name: 'Azioni',
        maxWidth: 98,
        canAutoResize: false,
        cellTemplate: this.actionsTemplate,
      },
      {
        name: 'Numero istanza',
        prop: 'codice_istanza',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      {
        name: 'Numero fascicolo',
        prop: 'fascicolo.codice_fascicolo',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      {
        name: 'Numero pratica',
        prop: 'numero_pratica',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      {
        name: 'Tipo istanza',
        prop: '_tipo',
        resizeable: true,
        sortable: false,
        width: 200,
      },
      {
        name: 'Intestatario',
        prop: '_intestatario',
        resizeable: true,
        sortable: false,
        width: 128,
      },
  

      {
        name: 'Indirizzo',
        prop: '_indirizzo',
        resizeable: true,
        sortable: false,
        width: 125,
      },
      {
        name: 'Data Ricezione',
        resizeable: true,
        sortable: false,
        prop: 'data_stato_dps',
        width: 125,
      },
      {
        name: 'Stato',
        prop: 'stato_istanza.value',
        resizeable: true,
        sortable: false,
        width: 88,
      },
      {
        name: 'Data stato',
        prop: 'data_creazione',
        resizeable: true,
        sortable: false,
        width: 135,
      },

      {
        name: 'Occupazione suolo pubblico',
        prop: 'occupazione_suolo_pubblico',
        resizeable: true,
        sortable: false,
        pipe: this.booleanPipe,
        width: 128,
      },
      {
        name: 'Comune',
        prop: 'comune.value',
        resizeable: true,
        sortable: false,
        width: 108,
      },
    ];
  }
  clean(){
    this.istanzeService.setFormItems(null)
  }
}
