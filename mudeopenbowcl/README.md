# Project Description

Modulo contenente il codice sviluppato con Angular che gestisce la parte fronte-end del BackOffice

# Configurations

  All'interno della cartella buildfiles del modulo sono contenuti i file per gli ambienti target utilizzati.
  I parametri sono utilizzati, per individuare:

- isProduction: flag che indica se si tratta di un ambiente di produzione o meno.
- basePath: del BackOffice per le chiamate ai servizi rest esposti per le chiamate alla logica applicativa.
- logoutUrl: la url da configurare per effettuare la logout dal sistema
- frontoffice: la url che permette di passare dall'attuale modulo a quello di FrontOffice

  ad es: \
  isProduction : false, \
  basePath: '<https://mudeopen-bo-rp.it/mudeopen/bo/rest',\>
  logoutUrl: '<https://mudeopen-bo-rp.it/logout',\>
  frontoffice: '<https://fo-mudeopen.it/mudeopen/#/>'

# Getting Started

Please refer to the Prerequisites section to gather the requested configuration
prior to configure the project.

Please refer to the Installing section for specifications about the
installation process.

# Prerequisites

- Node 12.
- All the libraries listed in the BOM.csv must be accessible to compile the
project.
- You need to have compiled the mudeopenfecommon component and set the contents in the "node_modules" folder.

# Installing

Questa componente non viene installata direttamente su web o application server ma viene gestita
come dipendenza per la componente mudeopenboweb; il compilato della presente componente dovr&agrave;
essere impostato nell'opportuna cartella di mudeopenboweb.
