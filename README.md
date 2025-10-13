# Product

MudeOpen - Modello Unico dell'Edilizia Open

# Description

Il Modello Unico Digitale per l'Edilizia (MUDE Open) è un sistema informativo unificato a livello regionale che consente ai professionisti di presentare via web ai Comuni qualsiasi pratica edilizia, di conservarla e consultarla in formato digitale. 

Il sistema MUDE Piemonte &egrave; promosso dalla Regione Piemonte nell'ambito della sussidiariet&agrave; verso i Comuni affinch&eacute;  possano dotarsi di un sistema telematico e digitale per l'inoltro e la gestione delle pratiche edilizie in forma dematerializzata, in ottemperanza alle varie disposizioni di legge nazionali in materia di dematerializzazione dei procedimenti amministrativi. In particolare Regione Piemonte con DPGR 5 ottobre 2018, n. 8/R  (Regione Piemonte BU41S2 11/10/2018), ha deliberato il Regolamento regionale recante: "Disposizioni per l'erogazione graduale del servizio telematico per la richiesta o la presentazione dei titoli abilitativi edilizi e definizione dei requisiti tecnici per l'interoperabilit&agrave; dei sistemi e per l'integrazione dei processi fra le diverse amministrazioni", in ottemperanza all'art. 49 comma 2 della legge regionale 5 dicembre 1977, n. 56, con il quale stabilisce che i comuni si dovranno dotare di sistemi telematici per l'inoltro delle istanze relative ai procedimenti di edilizia privata descrivendone  i requisiti tecnici essenziali per garantire uniformit&agrave; informativa e l'interoperabilit&agrave; fra gli altri sistemi a livello regionale, e nell'ambito della sussidiariet&agrave; mette a disposizione dei comuni o loro forme associative il sistema regionale MUDE Piemonte. 

Al sistema si accede dal portale [www.mude.piemonte.it](www.mude.piemonte.it) che, rappresenta un punto informativo di riferimento per la materia edilizia in Piemonte e in generale sulle attivit&agrave; del progetto MUDE Piemonte, che si esplicano anche attraverso le attivit&agrave; del  Gruppo di lavoro Inter-istituzionale costituito a partire dalla sottoscrizione dell'Accordo di Collaborazione ai sensi della Legge 241/90 art. 15 (Accordi fra Pubbliche Amministrazioni) del 30 settembre 2010, che trova espressione operativa nel Tavolo Tecnico Mude. 
La dematerializzazione del procedimento edilizio, nelle sue varie fasi procedurali e articolazioni nonch&eacute;  in termini di soggetti terzi coinvolti, &egrave; un processo ancora in itinere, al quale contribuiscono in forme e competenze diverse i vari soggetti coinvolti nel procedimento.
A questo proposito, sono stakeholder anche alcuni ordini e collegi professionali, che risultano fra i partecipanti al Tavolo Tecnico Mude. 

Il portale MUDE permette l'accesso alla "Scrivania del professionista" (con certificato digitale o SPID) e alla "Scrivania della PA" (credenziali Rupar Piemonte). Le "scrivanie" rappresentano gli strumenti di lavoro dei vari soggetti coinvolti dove &egrave; possibile preparare, organizzare, inoltrare e ricevere le istanze. Il "colloquio" fra le scrivanie e quindi fra privato e PA &egrave; agevolato da un sistema di notifiche che permette alla PA di segnalare al privato gli adempimenti necessari e lo stato di avanzamento delle istanze.
La compilazione della modulistica e la modalit&agrave; di inoltro sono controllate ed assistite affinch&eacute;  il professionista possa presentare il titolo abilitativo conforme all'intervento previsto, assicurando che l'istanza sia corretta e completa nelle sue varie sezioni. La procedura di compilazione della modulistica e di inoltro in via telematica &egrave; interamente digitale, accompagnata dalla firma digitale di ogni elaborato. La modulistica attualmente disponibile riguarda CILA, CILA Superbonus, SCIA, SCIA alternativa a PdC, PdC, Autorizzazione Paesaggistica, SCA, Integrazione documentale, Fine lavori SCIA, Inizio-Fine lavori PdC, Fine Lavori CILA, Proroghe lavori PdC, Notifica preliminare cantiere, Denuncia lavori di costruzione in zona sismica, Inizio-Fine lavori strutturali, Richiesta Pareri.


MudeOpen &egrave; costituito dalle componenti web e dai webservice utilizzati dai fruitori.

Di seguito le suddivisioni principali della soluzione MUDEOpen:

- FrontOffice (rivolto a cittadini e professionisti):
- BackOffice (rivolto agli operatori delle PA):
- Webservice soap (rivolto alle soluzioni di fruitori esterni)




<b>Componenti MudeOpen<b>

| Componente | Descrizione | Tecnologia |
| ---------- | ----------- | ---------- |
| [mudeopensrvcommon](./mudeopensrvcommon/) | Componente funzionalit&agrave; comuni a tutti i moduli| adopt-openjdk 11.0.4|
| [mudeopenbowcl](./mudeopenbowcl) | Componente Angular per il backOffice | Angular 10 |
| [mudeopenboweb](./mudeopenboweb) | Componente Web per il backOffice |adopt-openjdk 11.0.4 |
| [mudeopenfecommon](./mudeopenfecommon) | Componente funzionalit&agrave; comuni Angular | Angular 10  |
| [mudeopenfowcl](./mudeopenfowcl) | Componente Angular per il frontOffice |Angular 10  |
| [mudeopenfoweb](./mudeopenfoweb) | Componente Web per il frontOffice | adopt-openjdk 11.0.4 |
| [mudeopensrvapi](./mudeopensrvapi) | Componente servizi Rest, utilizzate da backOffice, frontOffice e soap | adopt-openjdk 11.0.4 |
| [mudeopensrvsoap](./mudeopensrvsoap) | Componente di backend che espone servizi SOAP in ascolto  | adopt-openjdk 11.0.4|



# Initial configuration

Per allestire un development environment occorre installare i system software elencati nei prerequisiti di sistema e
configurare opportunamente i file di properties denominati ad es. dev-rp-01.properties presenti sotto la cartella buildfiles delle componenti.\

Le librerie necessarie alla compilazione sono elencate nei file SBOM delle singole componenti e sono pubblicate su http://repart.csi.it
da dove il processo di compilazione le scarica automaticamente oppure sono incluse nella cartella lib/  per semplicit&agrave;.\
Per avere una migliore affidabilit&agrave; e scalabilit&agrave; complessiva del sistema configurare istanze/partizioni dedicate per le componenti di front end e dei servizi. 

# Getting Started

Sono presenti componenti che per essere compilate usano:
- Node
- Ant

Per le componenti che usano Node usare nvm con i seguenti comandi:\
`nvm use 12` \
`nvm install` \
`npm run ng -- build --configuration dev-rp-01`

L'esecuzione crea nella cartella 'dist' del workspace con il contenuto da inserire successivamente nella componente web.

Per le componenti che usano la adopt-openjdk 11.0.4 usare il tool `ant` con il seguente comando:

`ant -Dtarget=dev-rp-01 -lib apache-ivy-2.0.0`

L'esecuzione crea nella cartella 'dist/dev-rp-01' del workspace un file .tar contenente il pacchetto da installare.

# Prerequisite

- Ant ver. 1.8.4
- Apache-ivy-2.0.0
- Adopt-openjdk 11.0.4
- Node 12 o nvm
- Apache Web Server ver. 2.4
- Application Server Wildfly17
- PostgreSQL ver. 11.10
- Tutte le librerie elencate nei file bom devono essere accessibili per essere compilate dai progetti. Le librerie sono pubblicate http://repart.csi.it, ma per semplicit&agrave; sono incluse, per ogni componente java, nella cartella lib_ivy_remote/, all'interno di uno zip, da espandere all'interno della stessa componente dentro <root>/lib. 

# Installation

Nei file README.md dei singoli componenti sono presenti le istruzioni per la compilazione e per l'installazione.

I pacchetti generati dalla compilazione delle componenti devono essere deployati su Wildfly17.

Per quanto riguarda il DB, gli script per la creazione delle strutture e il popolamento delle tavole di decodifica e la configurazione della tavola delle property, sono contenute dentro la cartella [db](./db/).

Nelle singole componenti viene utilizzato ivy come repository, ricordare di cambiarne il puntamento ad un indirizzo raggiungibile.

# Versioning

Per il versionamento del software non si usa la tecnica Semantic Versioning

# Authors

The authors are listed [here](./AUTHORS.txt).

# Copyrights

© Copyright 2025 Regione Piemonte

# License

SPDX-License-Identifier: EUPL-1.2\
Vedere il file [EUPL_v1_2_IT-LICENSE.txt](./EUPL_v1_2_IT-LICENSE.txt) e [EUPL_v1_2_EN-LICENSE.txt](./EUPL_v1_2_EN-LICENSE.txt)  per i dettagli.
