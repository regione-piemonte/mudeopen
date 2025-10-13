import {
  AfterViewInit,
  Component,
  EventEmitter,
  OnInit,
  Output,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TableColumn } from '@swimlane/ngx-datatable';
import {
  FormUtils,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { Location } from '@angular/common';

import { IstanzaBO } from 'src/app/shared/model/istanzaBO.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DettaglioNuovaNotificaComponent } from './dettaglio-nuova-notifica/dettaglio-nuova-notifica.component';
import { takeUntil } from 'rxjs/operators';
import { IstanzaService } from '../../services/istanza.service';
import { NotificaDetailsComponent } from './notifica-details/notifica-details.component';

@Component({
  selector: 'app-invio-notifica',
  templateUrl: './invio-notifica.component.html',
  styleUrls: ['./invio-notifica.component.css'],
})
export class InvioNotificaComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;
  @Output('confirmEvent') confirmEvent: EventEmitter<any> =
    new EventEmitter<any>();

  istanza: IstanzaBO;
  constructor(
    public mudeopenFoBeService: MudeopenFoBeService,
    public messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    private location: Location,

    private istanzaService: IstanzaService
  ) {
    super(mudeopenFoBeService, messageService);
  }
  booleanPipe = {
    transform: (value) => value ? 'Si' : 'No',
  };

  columns: TableColumn[];
  pageNum;

  rows: any[] = [];
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
    this.istanzaService
      .getListaNotifiche(this.istanza.id_istanza, page, this.datatable.limit)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: any) => {
        console.log(response);
        this.completeInfo(response.body);
        this.rows = response.body;
        this._handlePaging(response);
        this.pageNum = page;
      });
  }
  goBack() {
    this.location.back();
  }

  completeInfo(lst) {
    lst.map((item) => {
      if (item.id_user_mittente === null) {
        console.log(item.id_user_mittente, item.ruolo_mittente, 'ffffff');
        item._mittente = item.ruolo_mittente;
      } else {
        item._mittente = this.getSubjectInfo(
          'name',
          item?.id_user_mittente?.contatto?.anagrafica
        );
      }
    });
    /*lst.map((item) => {
      let destinatari = [];
      for (let element in item.id_user_destinatari) {
        destinatari[element] = this.getSubjectInfo(
          'name',
          item.id_user_destinatari[element].contatto?.anagrafica
        );
      }
      item._destinatario = destinatari;
    });*/
    lst.map((item) => {
      let destinatari = [];
      for (let element in item.destinatari) {
        destinatari[element] = item.destinatari[element].id + " data lettura:[" + item.destinatari[element].value + "]"
      }
      item._destinatario = destinatari;
    });
  }
  nuovaNotifica() {
    const modal = this.modalService.open(DettaglioNuovaNotificaComponent, {
      size: 'xl',
      backdrop: 'static',
      keyboard: false,
      windowClass: 'alwaysScrollModal',
    });
    modal.componentInstance.idIstanza = this.istanza.id_istanza;
    modal.componentInstance.confirmEvent.subscribe((res) => {
      if (res) {
        console.log(res);
        this.onSearch();
      }
    });
  }
  getNotificationDetail(notification: any) {
    
    const modal = this.modalService.open(NotificaDetailsComponent, {
      size: 'xl',
      backdrop: 'static',
      keyboard: false,
      windowClass: 'alwaysScrollModal',
    });
    modal.componentInstance.notification = notification;
  
  }
  private _initCol() {
    this.columns = [
      {
        name: 'Azioni',
        canAutoResize: false,
        cellTemplate: this.actionsTemplate,
        maxWidth: 100,
      },
     /* {
        name: 'Mittente',
        prop: '_mittente',
        resizeable: true,
        sortable: false,

        width: 148,
      },*/
      {
        name: 'Documenti associati',
        prop: 'rif_documenti',
        pipe: this.booleanPipe,
        resizeable: true,
        sortable: false,

        width: 148,
      },
      {
        name: 'Tipo Notifica',
        prop: 'id_tipo_notifica.desTipoNotifica',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      {
        name: 'Destinatari',
        prop: '_destinatario', 

        width: 148,
      },
      {
        name: 'Oggetto',
        prop: 'oggetto_notifica',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      {
        name: 'Data Notifica',
        prop: 'dt_ins',
        resizeable: true,
        sortable: false,
        width: 148,
      },
      // {
      //   name: 'Data lettura',
      //   prop: '?',
      //   resizeable: true,
      //   sortable: false,

      //   width: 148,
      // },
    ];
  }
}
