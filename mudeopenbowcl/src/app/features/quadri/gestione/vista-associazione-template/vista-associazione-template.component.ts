import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormUtils } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';

@Component({
  selector: 'app-vista-associazione-template',
  templateUrl: './vista-associazione-template.component.html',
  styleUrls: ['./vista-associazione-template.component.scss']
})
export class VistaAssociazioneTemplateComponent extends FormUtils implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();
  public idTipoQuadro;
  public rows: any = null;

  constructor(public mudeopenFoBeService: MudeopenFoBeService) {
    super(mudeopenFoBeService, null);

    this.disableCountryLoading = true;
  }

  ngOnInit() {
    this.mudeopenFoBeService.recuperaTemplateAssociaQuadro(this.idTipoQuadro).subscribe(x => {
      this.rows = x;
    });
  }

  onSubmit(isOk: boolean = false) {
    this.confirmEvent.emit(isOk);
  }

}
