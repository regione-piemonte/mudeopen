import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'seleziona-soggetti-fascicolo',
  templateUrl: './seleziona-soggetti-fascicolo.component.html',
  styleUrls: ['./seleziona-soggetti-fascicolo.component.scss']
})
export class SelezionaSoggettiFascicoloComponent {

  @Output('confirmEvent') onItemsSelected: EventEmitter<any> = new EventEmitter<any>();
  @Input('roles') roles: any = null;


 /* 
	* nome metodo "roleCheck"; method description: 
	* @param (role: any)
	* @returns 
	*/ 

  roleCheck(role: any) {
    role.checked = !role.checked;
    role.subjects.forEach(item => item.checked = role.checked);
  }

  subjectCheck(role: any, subj: any) {
    subj.checked = !subj.checked;
    role.checked = !!role.subjects.find(item => !!item.checked);
  }

  getSubjectDetails(subject): string {
    let instances = subject.instances.join(', ');

    if(subject.end)
      return '<font color="lightgrey">' + subject.value + ' (' + subject.start.replace(/-/g,'/') + '&nbsp;-&nbsp;' + subject.end.replace(/-/g,'/') + ', <em>' + instances
       + '</em>)</font>';

    return subject.value + ' (' + subject.start.replace(/-/g,'/') + ', <em>' + instances + '</em>)';
  }

}
