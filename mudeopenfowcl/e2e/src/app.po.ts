import { browser, by, element } from 'protractor';

export class AppPage {

	/* 
	* nome metodo "navigateTo"; descrizione: 
	* @param ()
	* @returns 
	*/ 

  navigateTo() {
    return browser.get('/');
  }


	/* 
	* nome metodo "getParagraphText"; descrizione: 
	* @param ()
	* @returns 
	*/ 

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }
}
