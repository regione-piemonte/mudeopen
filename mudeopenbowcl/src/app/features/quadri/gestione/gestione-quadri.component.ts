import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { QuadriService } from '../services/quadri-services';
import { getComponentCategory } from '../services/quadri-utils';
import { VistaAssociazioneTemplateComponent } from './vista-associazione-template/vista-associazione-template.component';




@Component({
  selector: 'gestione-quadri',
  templateUrl: './gestione-quadri.component.html',
  styleUrls: ['./gestione-quadri.component.scss']
})
export class GestioneQuadriComponent extends FormUtils implements OnInit, AfterViewInit {

  @ViewChild('table') table: any;

  isFilterOn: boolean = false;

  searchTipoQuadro: string;
  searchTipoGestione: string;
  searchVersione: any;
  searchStato: string;

  saveMessage: boolean;

  rows: any = null;
  componentNanmes: any;

  
  /*
  * nome metodo "constructor"; descrizione:
  * @param (private mudeopenFoBeService: MudeopenFoBeService,
          private router: Router,
          private route: ActivatedRoute,
          private messages: MessageService,
          private modalService: NgbModal)
  * @returns
  */
  constructor(public mudeopenFoBeService: MudeopenFoBeService,
    private router: Router,
    public quadriService: QuadriService,
    public messageService: MessageService,
    private route: ActivatedRoute,
    private modalService: NgbModal) {
    super(mudeopenFoBeService, messageService);

    this.componentNanmes = getComponentCategory();
    this.disableCountryLoading = true;
  }


  /*
  * nome metodo "ngOnInit"; descrizione:
  * @param ()
  * @returns void
  */
  ngOnInit(): void {
    this.onSearch();
  }

  ngAfterViewInit() {
    setTimeout(_ => 
      window.dispatchEvent(new Event('resize')), 300);
  }

  /*
  * nome metodo "onSearch"; descrizione:
  * @param (page: any = 0)
  * @returns
  */
  onSearch(page: any = 0) {
    if(page && page.pageSize) { page = page.offset; }
    
    this.mudeopenFoBeService.listaQuadri(JSON.stringify({
      "tipoQuadro": { "eq": this.searchTipoQuadro || null },
      "tipoGestione": { "eq": this.searchTipoGestione || null },
      "versione": { "eq": (this.searchVersione == true && -1) || null },
      "stato": { "eq": this.searchStato || null }
    }), page, this.datatable.limit, '+mudeDTipoQuadro_codTipoQuadro, +numVersione').subscribe(x => {
      this.rows = x.body;
      this._handlePaging(x);
    });
  }

  showFilter() {
    this.isFilterOn = true;
  }

  hideFilter() {
    this.searchTipoQuadro = this.searchTipoGestione = this.searchVersione = this.searchStato = null;
    this.isFilterOn = false;
    this.onSearch(this.datatable.offset);
  }

  quadroCreate() {
    this.quadriService.selectNewQuadroType('crea nuovo quadro', res => {
      this.saveMessage = true;
      this.onSearch(this.datatable.offset);
    });
  }

  quadroCreateNewVersion(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('crea nuova versione ' + (quadro.num_versione + 1), quadro.flg_tipo_gestione, quadro, quadroRes => {
      this.saveMessage = true;
      this.onSearch(this.datatable.offset);
    }); // open just the same quadro type
  }

  quadroModify(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('modifica versione ' + quadro.num_versione, quadro.flg_tipo_gestione, quadro, quadroRes => {
      this.saveMessage = true;
      this.onSearch(this.datatable.offset);
    });
  }

  quadroView(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('visualizza versione ' + quadro.num_versione, quadro.flg_tipo_gestione, quadro, null, true);
  }

  quadroDelete(lista: any, quadro: model.QuadroVO, index: number) {
    this.confirmDialog("Vuoi eliminare il quadro?", () => {
      this.mudeopenFoBeService.eliminaQuadro(quadro.id_quadro).subscribe(x => {
        lista.splice(index, 1);
        this.saveMessage = true;
        this.onSearch(this.datatable.offset);
      });
    });
  }

  quadroUpdateAllTemplates(lista: any, quadro: model.QuadroVO, index: number) {
    const modal = this.modalService.open(VistaAssociazioneTemplateComponent, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });
    modal.componentInstance.idTipoQuadro = quadro.tipo_quadro.id_tipo_quadro;
    modal.componentInstance.confirmEvent.subscribe((isOk) => {
      if(isOk) {
        this.mudeopenFoBeService.pubblicaQuadro(quadro.tipo_quadro.id_tipo_quadro).subscribe(x => {
          this.saveMessage = true;
          this.onSearch(this.datatable.offset);
        }); }

      modal.dismiss()
    });
    
  }
}
