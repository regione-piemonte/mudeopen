import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormioBaseComponent, FormioForm } from '@formio/angular';
import {
  FormUtils,
  MessageService,
  MudeopenFoBeService,
  SelectVO,
} from 'mudeopen-common';
import { takeUntil } from 'rxjs/operators';
import { IstanzaBO } from 'src/app/shared/model/istanzaBO.model';
import { IstanzaService } from '../../../services/istanza.service';

@Component({
  selector: 'app-dettaglio-nuova-notifica',
  templateUrl: './dettaglio-nuova-notifica.component.html',
  styleUrls: ['./dettaglio-nuova-notifica.component.css'],
})
export class DettaglioNuovaNotificaComponent
  extends FormUtils
  implements OnInit
{
  @Output('confirmEvent') confirmEvent: EventEmitter<any> =
    new EventEmitter<any>();
  indirizzoIstanza: any;
  nuovaNotificaForm: FormGroup;
  notifications: SelectVO[] = [];
nuovaNotifica:string
  constructor(
    private modalService: NgbModal,
    public mudeopenFoBeService: MudeopenFoBeService,
    public messageService: MessageService,
    public istanzaService: IstanzaService,

    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    super(mudeopenFoBeService, messageService);
  }
  refreshForm = new EventEmitter();
  successEmitter = new EventEmitter<any>();
  check: boolean = false;
  isOk: boolean = false;
  currentForm: any;
  form: FormioForm;
  formioOptions: any = {
    disableAlerts: true,
    //    alertsPosition: AlertsPosition.none,
    saveDraft: false,
    saveDraftThrottle: 10000,
  };
  renderOptions: any = {
    locale: 'it',
    language: 'it',
    i18n: {
      it: {
        error: 'verificare i campi del form prima di salvare.',
        invalid_date: 'Il campo non ha una data valida.',
        invalid_email: '{{field}} email non valida.',
        invalid_regex: 'il campo non corrisponde al formato {{regex}}.',
        mask: 'il campo non corrisponde al formato richiesto.',
        max: "il campo non puo' essere piu' grande di {{max}} caratteri.",
        maxLength: 'il campo deve essere al massimo {{length}} caratteri.',
        min: "il campo non puo' essere piu' piccolo di {{min}}.",
        minLength: 'il campo deve essere almeno di {{length}} caratteri.',
        next: 'Avanti',
        pattern: 'il campo non corrisponde al formato {{pattern}}',
        previous: 'Indietro',
        required: 'campo obbligatorio',
        unsavedRowsError: 'inserire i dati da salvare',
        invalidRowsError: 'correggere le righe non valide prima di procedere',
        invalidRowError: 'riga non valida. Correggerla o eliminarla',
        valueIsNotAvailable: 'campo obbligatorio',
        minWords: 'parole minime non sufficienti',
        maxWords: 'troppe parole inseite',
        custom:
          'Verificare che il valore del campo sia corretto e non ci siano duplicazioni',
        'Type to search': 'inizia a scrivere per cercare',
        'No results found': 'nessuna voce trovata',
      },
    },
  };
  istanza: IstanzaBO;
  idIstanza: any;
  ngOnInit(): void {
    this.istanzaService.getNotificationsType().pipe(takeUntil(this.destroy$))
    .subscribe((response: SelectVO[]) => {
    console.log(response);
      this.notifications = response;
    });
    this.nuovaNotificaForm = new FormGroup({
      notifica: new FormControl(null, [Validators.required]),
    });
    this.istanzaService
      .getIstanza(this.idIstanza)
      .subscribe((response: any) => {
        this.istanza = response;
        this.indirizzoIstanza = this.getIndirizzo(
          (this.istanza as any)?.ubicazione || this.istanza.indirizzo
        );

        //this.getFormIo();
      });
  }
  onPossibileNotificationChange(selected: any): void {
    if (selected) {
      this.nuovaNotifica = selected;
      this.istanzaService.getFormIONotifica(this.istanza.id_istanza, selected)
        .pipe(takeUntil(this.destroy$))
        .subscribe((response) => {
          this.form = JSON.parse(response[0].value).formio;
        });
    }

  }
  ready(formioBaseComponent: FormioBaseComponent) {
    this.currentForm = formioBaseComponent.formio;
  }
  goBack() {
    this.confirmEvent.emit(this.isOk);
    this.modalService.dismissAll();
    // this.onConfirm.emit();
  }

  getFormIo() {
   /* this.istanzaService
      .getFormIONotifica(this.istanza.id_istanza)
      .subscribe((response: any) => {
        this.form = JSON.parse(response[0].value).formio;
      });*/
  }

  submitFormIO(submission) {
    this.istanzaService
      .submitFormIO(this.istanza.id_istanza, submission.data, this.nuovaNotifica)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response) => {
        this.isOk = true;
        this.successEmitter.emit('Dati salvati con successo');
        this.currentForm.emit('submitDone');
        setTimeout(() => {
          this.confirmEvent.emit(this.isOk);
          this.modalService.dismissAll();
        }, 500);
      });
  }
}
