import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FascicoliService {
form:any;

  setFormItems(form: any) {
  
    this.form = JSON.stringify(form);
    console.log(this.form)
    
      }
      getFormItems() {
        return JSON.parse(this.form ? this.form : null );
      }
}
