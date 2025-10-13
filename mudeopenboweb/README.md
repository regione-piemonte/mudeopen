# Project Description
This is a multi-module project manage the single model for construction for the Public
Administration.

The modules are as follow:
- backend services:
  - [mudeopen-bo-be]<< TODO-GITHUB >> REST service implementation

- frontend services:
  - [mudeopen-bo-fe]<< TODO-GITHUB >>: Angular application
- transversal:
  - [mudeopen-srv-common]<< TODO-GITHUB >>
  - [mudeopen-db]<< TODO-GITHUB >>: database implementation, with all the required scripts
 

# Configurations
For the configuration of each single module, please refer to the `README.md`
file which is present in each module.

# Getting Started
Please refer to the Prerequisites section to gather the requested configuration
prior to configure the project.

Please refer to the Installing section for specifications about the
installation process.

# Prerequisites

- The Java projects are written in UTF-8 and are compatible with Java 11.0.6
- Apache Ant 1.8.4 for the building process 
- All the libraries listed in the BOM.csv must be accessible to compile the
project.
- you need to have compiled the mudebowcl component and set the contents in the src/web/be/rest/ folder
- The correct version for the DBMS (tested on PostgreSQL 11.10)

# Installing
Please refer to the [INSTALLATION.md](./INSTALLATION.md) file for the steps
required for the installation

