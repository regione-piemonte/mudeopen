import {
  AfterViewInit,
  Component,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import {
  FormUtils,
  IstanzaVO,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { Location } from '@angular/common';

import * as model from 'mudeopen-common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TableColumn } from '@swimlane/ngx-datatable';
import { Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { RicercaService } from 'src/app/shared/services/ricerca.service';
import { Permissions } from '../../../../core/enum/permissions.enum';
import { PraticheService } from '../../services/pratiche.service';

@Component({
  selector: 'app-lista-pratiche',
  templateUrl: './lista-pratiche.component.html',
  styleUrls: ['./lista-pratiche.component.scss'],
})
export class ListaPraticheComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  permissions = Permissions;
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;
  @ViewChild('expandRowTemplate') expandRowTemplate: TemplateRef<any>;
  @ViewChild('istancesDetailRow') istancesDetailRow: TemplateRef<any>;
  button;
  stato = '';
  columnsPratica: TableColumn[];
  praticheForm: any;
  istanzePratiche: any;
  numEl;
  pageNum;

  constructor(
    private router: Router,
    public messageService: MessageService,
    private ricercaService: RicercaService,
    private location: Location,
    private praticheService: PraticheService, 
    public mudeopenFoBeService: MudeopenFoBeService
  ) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = false;
    this.ricercaPraticaForm = this.initPraticaForm();
  }

  rowExpanded: any;
  rows: IstanzaVO[] = [];
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
  ricercaPraticaForm: FormGroup;

  tipologieIstanza: model.SelectVO[];
  tipologiaIntervento: model.SelectVO[];
  statoIstanzaList: model.SelectVO[];
  statoFascicoloList: model.SelectVO[];
  provincePiemonte: model.ProvinciaVO[];
  isSubmitted = false;

  searchSelected = 0;

  ngOnInit(): void {
    this.ricercaPraticaForm
      .get('id_provincia')
      .valueChanges.subscribe((value) => {
        console.log(value);
        if (value.id) {
          console.log(value.id);
          this.ricercaPraticaForm
            .get('id_comune')
            .get('id')
            .setValidators([Validators.required]);
      
                  } else {
          this.ricercaPraticaForm.get('id_comune').get('id').clearValidators();
        }
        this.ricercaPraticaForm
          .get('id_comune')
          .get('id')
          .updateValueAndValidity();
       
      });
    this.mudeopenFoBeService
      .getTipologieIntervento()
      .then((x) => (this.tipologiaIntervento = x));
    this.mudeopenFoBeService
      .getTipologieIstanza()
      .then((x) => (this.tipologieIstanza = x));
    this.mudeopenFoBeService
      .getSessionData('statoIstanzaList')
      .then(
        (x) =>
          (this.statoIstanzaList = x.filter(
            (y: model.SelectVO) =>
              y.id !== 'BZZ' && y.id !== 'DFR' && y.id !== 'FRM'
          ))
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
   
  }


  initPraticaForm(): FormGroup {
    return new FormGroup({
      // id_tipo_istanza: new FormGroup({
      //   id: new FormControl(),
      //   value: new FormControl(),
      // }),
      //id_pm: new FormControl(),
      id_provincia: new FormGroup({
        id: new FormControl(),
        value: new FormControl(),
      },),
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
      // id_intestatario_pf: new FormControl(),
      // id_intestatario_pg: new FormControl(),
      // codice_istanza: new FormControl(),
      // data_registrazione_pratica: new FormControl(),
      // data_protocollo: new FormControl(),
      anno: new FormControl(null, [
        Validators.maxLength(4),
        Validators.pattern('^[0-9]*$'),
      ]),
      numero_pratica: new FormControl(),
      //codice_pratica: new FormControl(),
    });
  }

  ngAfterViewInit() {
    this.initCol();
   
    if(this.praticheService.getFormItems()!= null) {

      this.ricercaPraticaForm.patchValue(this.praticheService.getFormItems())
      this.ricercaPraticaForm.get('id_provincia').valueChanges.subscribe((data)=>{
      this.onChangeProvincia(this.praticheService.getFormItems().id_provincia.id)

    })
      this.ricercaPraticaForm.get('id_provincia').get('id').setValue(this.praticheService.getFormItems().id_provincia.id)
      this.onSearch()
     }

  }
reset(){
  this.praticheService.setFormItems(null)
}
  selectForm(tabIndex: number) {
    this.searchSelected = tabIndex;
  }

  onSearch(page: any = 0) {
 
     ///
     this.praticheService.setFormItems(this.ricercaPraticaForm.value)
     ////
    this.commonUtils.validateForm(this.ricercaPraticaForm);
    if (page && page.pageSize) {
      page = page.offset;
    }
    this.isSubmitted = true;
    if (this.ricercaPraticaForm.valid) {
      if (this.ricercaPraticaForm?.value?.stato?.id === '') {
        this.ricercaPraticaForm
          .get('stato')
          .setValue({ id: null, value: null });
      }
    }
   
    this.praticheForm = this.ricercaPraticaForm.value;
    this.pageNum = page;
    this.ricercaService
      .searchPraticheDS(this.ricercaPraticaForm.value, page, this.datatable.limit)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: any) => {
        this.completeInfo(response.body);
        this.rows = response.body;
        this._handlePaging(response);

      });
      this.ricercaPraticaForm.get('id_comune').get('id').clearValidators();
      this.ricercaPraticaForm
      .get('id_comune')
      .get('id')
      .updateValueAndValidity();
  }

  downloadExcel() {
    this.ricercaService.excelPraticheDS(
      this.praticheForm,
      this.pageNum,
      this.datatable.limit
    );
  }

  goBack() {
    this.location.back();
  }

  downloadExcelIstanze(pratica) {
    this.ricercaService.excelIstanze(
      { id_pratica: pratica.id_pratica },
      0,
      10000
    );
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

  completeInfo(lst) {
    // lst.map(
    //   (item) =>
    //     (item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo))
    // );
    // lst.map(
    //   (item) =>
    //     (item._intestatario = this.getSubjectInfo(
    //       'name',
    //       item.fascicolo.intestatario.anagrafica
    //     ))
    // );
  }

  toggleExpandRow(pratica: any, expanded) {
    if (!pratica.id_pratica) {
      return;
    }

    this.ricercaService
      .searchIstanze({ id_pratica: pratica.id_pratica }, 0, 10000)
      .subscribe(
        (arr: any) => {
          (pratica as any)._istanzePraticheList = (arr.body || []).filter(
            (istanza: any) => {
              this.rowExpanded = Object.assign({}, pratica);

              console.log(istanza);
              return true;
            }
          );
        },
        (err) => console.log(err)
      );
  }

  rowIdentity(row) {
    return row?.id_pratica;
  }

  navigateToDocument(pratica: any) {
    this.router.navigate([
      'backoffice',
      'lista-pratiche',
      pratica.id_pratica,
      'documenti',
    ]);
  }

  private initCol() {
    this.columnsPratica = [
      {
        name: 'Azioni',
      //  maxWidth: 100,
      //  canAutoResize: false,
        cellTemplate: this.actionsTemplate,
      },
      {
        name: 'Elenco istanze',
      //  maxWidth: 100,
     //   canAutoResize: false,
        cellTemplate: this.expandRowTemplate,
      },
      {
        name: 'Numero pratica',
        prop: 'numero_pratica',
        resizeable: true,
        sortable: false,
      //  width: 150,
      },
      { name: 'Anno', prop: 'anno',
     //  width: 108
       },
      { name: 'Ente', prop: 'ente.descrizione', width: 108 },

      { name: 'Comune', prop: 'comune.value', width: 108 },
   

      {
        name: 'Data Creazione',
        prop: 'data_creazione',
       // width: 125,
      },
    ];
  }
}
