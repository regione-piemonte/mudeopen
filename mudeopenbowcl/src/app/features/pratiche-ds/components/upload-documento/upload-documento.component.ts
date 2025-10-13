import { Component, EventEmitter, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormioBaseComponent, FormioForm } from '@formio/angular';
import { PraticheService } from '../../services/pratiche.service';
import * as model from 'mudeopen-common';
import { Location } from '@angular/common';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  FormUtils,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { catchError, takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-upload-documento',
  templateUrl: './upload-documento.component.html',
  styleUrls: ['./upload-documento.component.css'],
})
export class UploadDocumentoComponent extends FormUtils implements OnInit {
  pratica: any;
  fileList: any = [];
  currentForm: any;
  file: File = null;
  form: FormioForm;
  tipoFileForm: FormGroup;
  refreshForm = new EventEmitter();
  successEmitter = new EventEmitter<any>();
  check: boolean = false;
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

  constructor(
    private activatedRoute: ActivatedRoute,
    private praticaService: PraticheService,
    private location: Location,
    public mudeopenFoBeService: MudeopenFoBeService,
    public messageService: MessageService,
    private router: Router
  ) {
    super(mudeopenFoBeService, messageService);
    this.tipoFileForm = this.initForm();
  }

  idFascicolo;
  ngOnInit(): void {
    this.pratica = this.activatedRoute.snapshot.data['pratica'].body[0];
    this.idFascicolo = this.activatedRoute.snapshot.paramMap.get('idFascicolo');

    this.praticaService.getPraticaUpload().subscribe((response: any) => {
      response.forEach((file: any) => {
        let array = {
          value: file.id,
          id: file.value,
        };
        this.fileList.push(array);
      });
    });
  }

  ready(formioBaseComponent: FormioBaseComponent) {
    this.currentForm = formioBaseComponent.formio;
  }

  initForm(): FormGroup {
    return new FormGroup({
      tipo: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl(),
      }),
      numero_protocollo: new FormControl(),
      data_protocollo: new FormControl(),
    });
  }
  onChange(event) {
    this.file = event.target.files[0];
  }
  goBack(): void {
    this.location.back();
  }
  onSubmit() {
    this.check = true;
    this.praticaService
      .uploadAllegato(
        this.pratica.id_pratica,
        this.tipoFileForm.value.tipo.id,
        this.file,
        this.tipoFileForm.value.numero_protocollo,
        this.tipoFileForm.value.data_protocollo
      )
      //.pipe(catchError((error) => error))
      .subscribe((response) => {

        this.router.navigate([
          'backoffice',
          'pratiche-ds',
          this.pratica.id_pratica,
          'documenti-ds',
          this.idFascicolo
        ]);
      });
  }
}
