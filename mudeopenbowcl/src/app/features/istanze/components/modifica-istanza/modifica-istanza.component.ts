import {
  AfterViewInit,
  Component,
  OnInit,
  TemplateRef,
  ViewChild,
  LOCALE_ID, 
  EventEmitter,
  Inject,
} from '@angular/core';
import { Location } from '@angular/common';

import {
  AllegatoVO,
  FormUtils,
  IstanzaVO,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { ActivatedRoute, Router } from '@angular/router';
import * as model from 'mudeopen-common';
import { Permissions } from '../../../../core/enum/permissions.enum';

import { forkJoin } from 'rxjs';
import { FormioBaseComponent, FormioComponent, FormioForm } from '@formio/angular';

@Component({
  selector: 'app-modifica-istanza',
  templateUrl: './modifica-istanza.component.html',
  styleUrls: ['./modifica-istanza.component.scss'],
})
export class ModificaIstanzaComponent
  extends FormUtils
  implements OnInit, AfterViewInit
{
  @ViewChild('mainFormio') mainFormio: FormioComponent;
  Permissions = Permissions;
  idIstanza: any;
  submission: any = {};
  customForm: FormioForm;
  _isStepInReadOnlyMode: boolean = false;

  currentForm: any;
  public successEmitter: EventEmitter<any> = new EventEmitter<any>();


  formioOptions: any = {
    disableAlerts: true,
//    alertsPosition: AlertsPosition.none,
    saveDraft: true,
    saveDraftThrottle: 1000
  }
  renderOptions: any = {
    locale: 'it',
    language: 'it',
    i18n: {
      it: {
        error: "verificare i campi del form prima di salvare.",
        invalid_date:"Il campo non ha una data valida.",
        invalid_email: "{{field}} email non valida.",
        invalid_regex: "il campo non corrisponde al formato {{regex}}.",
        mask: "il campo non corrisponde al formato richiesto.",
        max: "il campo non puo' essere piu' grande di {{max}} caratteri.",
        maxLength: "il campo deve essere al massimo {{length}} caratteri.",
        min: "il campo non puo' essere piu' piccolo di {{min}}.",
        minLength: "il campo deve essere almeno di {{length}} caratteri.",
        next: "Avanti",
        pattern: "il campo non corrisponde al formato {{pattern}}",
        previous: "Indietro",
        required: "campo obbligatorio",
        unsavedRowsError: 'inserire i dati da salvare',
        invalidRowsError: 'correggere le righe non valide prima di procedere',
        invalidRowError: 'riga non valida. Correggerla o eliminarla',
        valueIsNotAvailable: 'campo obbligatorio',
        minWords: 'parole minime non sufficienti',
        maxWords: 'troppe parole inseite',
        custom: 'Verificare che il valore del campo sia corretto e non ci siano duplicazioni',
        'Type to search': 'inizia a scrivere per cercare',
        'No results found': 'nessuna voce trovata',
      }
    }
  };

  constructor(
    private router: Router,
    private location: Location,
    private route:ActivatedRoute,
    public messageService: MessageService,
    public mudeopenFoBeService: MudeopenFoBeService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super(mudeopenFoBeService, messageService);
    this.idIstanza = route.snapshot.paramMap.get('idIstanza');
  }

  ngOnInit(): void {
    forkJoin([
      this.mudeopenFoBeService.loadQuadroById('801', this.idIstanza, null),
      this.mudeopenFoBeService.loadJDataIstanza(this.idIstanza, null, null, false, 'BO_EXTRA_COLUMNS_DENUNCIA-SISMICA')
    ]).subscribe((results) => {
      this.customForm = JSON.parse(results[0].json_configura_quadro || '{}');
      this.submission = {data: JSON.parse(results[1].json_data || '{}')};
    });
  }

  ngAfterViewInit() {
  }

  saveMessage: boolean = false;
  onFormIOSubmit(submission) {
    const dataQuadro: model.IstanzaTemplateQuadroRequest = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: 801,
      jsonDataQuadro: JSON.stringify(submission.data),
      cod_sub_quadro: 'EXTRA_FIELDS'
    };

    this.mudeopenFoBeService.salvaJsonDataQuadro(dataQuadro).subscribe((res) => {
      this.successEmitter.emit('Dati salvati con successo');
      this.currentForm.emit('submitDone');

      this.saveMessage = true;
    });
  }  

  onFormIOReady(formioBaseComponent: FormioBaseComponent){
    this.currentForm = formioBaseComponent.formio;
  }

  goBack(): void {
    this.location.back();
  }
}
