
# Project Description

Modulo contenente le API che vengono invocate dalla componente mudeopensrvsoap.
Nella cartella srv/yaml sono presenti i descrittori yaml con le definizioni delle interfacce
usate dal software.

# Configurations

Nella cartella buildfiles sono contenuti i file di riferimento ai diversi ambienti target.
In questi file vengono definiti:

- nome datasource
- endpoint di puntamento alla componente mudeopensrvcommon
- configurazioni Hibernate
- configurazioni server mail

# Getting Started

Please refer to the Prerequisites section to gather the requested configuration
prior to configure the project.

Please refer to the Installing section for specifications about the
installation process.

# Prerequisites

- The Java projects are written in UTF-8 and are compatible with Java 11.0.6.
- Apache Ant 1.8.4 for the building process.
- A "Spring version 4.3.24.RELEASE".
- A "resteasy-jaxrs ver. 3.7.0.Final".
- All the libraries listed in the BOM.csv must be accessible to compile the
project.
- The correct version for the DBMS (tested on PostgreSQL 11.10)

# Installing

Please refer to the [INSTALLATION.md](./INSTALLATION.md) file for the steps
required for the installation
