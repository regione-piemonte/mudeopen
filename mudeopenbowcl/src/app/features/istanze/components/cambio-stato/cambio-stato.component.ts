import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';
import { SelectVO } from 'mudeopen-common/lib/swagger-api/model/selectVO';
import { IstanzaBO } from '../../../../shared/model/istanzaBO.model';
import { FormioBaseComponent, FormioForm } from '@formio/angular';
import { Location } from '@angular/common';
import { IstanzaService } from '../../services/istanza.service';

@Component({
  selector: 'app-cambio-stato',
  templateUrl: './cambio-stato.component.html',
  styleUrls: ['./cambio-stato.component.css'],
})
export class CambioStatoComponent extends FormUtils implements OnInit {
  nuovoStatoForm: FormGroup;
  currentForm: any;
  nuovoStato: string;
  istanza: IstanzaBO;
  possibiliStati: SelectVO[] = [];

  isFlatPikrSet = false;
  refreshForm = new EventEmitter();
  successEmitter = new EventEmitter<any>();

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
        max: 'il campo non puo\' essere piu\' grande di {{max}} caratteri.',
        maxLength: 'il campo deve essere al massimo {{length}} caratteri.',
        min: 'il campo non puo\' essere piu\' piccolo di {{min}}.',
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
        custom: 'Verificare che il valore del campo sia corretto e non ci siano duplicazioni',
        'Type to search': 'inizia a scrivere per cercare',
        'No results found': 'nessuna voce trovata',
      },
    },
  };

  constructor(
    private istanzaService: IstanzaService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public mudeopenFoBeService: MudeopenFoBeService,
    public messageService: MessageService,
  ) {
    super(mudeopenFoBeService, messageService);
  }

  ngOnInit(): void {
    this.istanza = this.activatedRoute.snapshot.data['istanza'];
    this.istanzaService.getPossibiliStatiIstanza(this.activatedRoute.snapshot.paramMap.get('id'))
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: SelectVO[]) => {
        this.possibiliStati = response;
      });

    this.nuovoStatoForm = new FormGroup({
      stato: new FormControl(null, [Validators.required]),
    });
  }

  onPossibileStatoChange(selezionato: string) {
    if (selezionato) {
      this.nuovoStato = selezionato;
      this.istanzaService.getFormIOStato(this.istanza, selezionato)
        .pipe(takeUntil(this.destroy$))
        .subscribe((response) => {
          const formio = JSON.parse(response[0].value);
          this.form = formio.formio || formio;
        });
    }
  }

  setFlatPikr() {
    setTimeout(_ => {
      if (!this.isFlatPikrSet && window['flatpickr']) {
        this.isFlatPikrSet = true;
        window['flatpickr'].l10ns.it = {
          weekdays: {
            shorthand: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'],
            longhand: ['Domenica', 'Lunedi', 'Martedi', 'Mercoledi', 'Giovedi', 'Venerdi', 'Sabato'],
          },
          months: {
            shorthand: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
            longhand: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
          },
          firstDayOfWeek: 1,
          //      ordinal: () => "�",
          rangeSeparator: ' al ',
          weekAbbreviation: 'Se',
          scrollTitle: 'Scrolla per aumentare',
          toggleTitle: 'Clicca per cambiare',
          time_24hr: true,
        };

        this.refreshForm.emit({
          form: this.form,
        });
      }
    }, 500);
  }

  onFormIOSubmit(submission) {
    this.istanzaService.cambiaStato(this.istanza, submission.data, this.nuovoStato)
      .pipe(takeUntil(this.destroy$))
      .subscribe((response) => {
        this.successEmitter.emit('Dati salvati con successo');
        this.currentForm.emit('submitDone');
        this.router.navigate(['backoffice', 'lista-istanze', this.activatedRoute.snapshot.paramMap.get('id'), 'dettaglio-istanza'], { queryParams: { lastUpdate: Date.now() } });
        console.log(response);
      });
  }

  ready(formioBaseComponent: FormioBaseComponent) {
    this.currentForm = formioBaseComponent.formio;
  }

  goBack() {

    this.router.navigate(['backoffice', 'lista-istanze', this.activatedRoute.snapshot.paramMap.get('id'), 'dettaglio-istanza'], { queryParams: { lastUpdate: Date.now() } });
  }
}
