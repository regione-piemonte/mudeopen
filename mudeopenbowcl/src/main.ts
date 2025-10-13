import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { environment } from 'buildfiles/environment.local-rp-01';

import { AppModuleBO } from './app/app.module';

if(environment.isProduction) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModuleBO)
  .catch(err => console.log(err));
