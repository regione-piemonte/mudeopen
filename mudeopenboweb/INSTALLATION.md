# INSTALLATION

## Local environment
- For the local development/testing environment, at least one Java EE8-compliant
AS server instance MUST be installer. It is not strictly required to have more
than one instance of the AS server.
- A proxy web server MAY be useful for JS development, by defining a proxy to
the source directories. Depending on the AS server, the proxy may be configured
on it (please refer to the documentation of the server for informations on how
to do so).
- A compliant version of the DBMS MUST be used. The development and runtime are
based on a PostgreSQL 9.6.10 instance, but any compliant solution should work.
Note that the following extensions MAY, SHOULD or MUST be installed
  - MUST be installed
    - plpgsql
    - pgcrypto
    - uuid-ossp
  - SHOULD be installed (note that the DBA views depend on these extensions.
  Therefore these are to be considered in the MUST section were the DBA views
  desired)
    - adminpack
    - pg_stat_statements
  - MAY be installed
    - pg_buffercache
    - tablefunc
- A Dockerized environment MAY help with the local development. We do not as of
now provide such environment nor configuration.
- So as to prevent multiple developers to each overwrite one's own properties
for local development, each project that MAY require local configuration has no
such profile file, and the file with the corresponding name is ignored by the
versioning system; its content MUST be reconstructed by example of the singlings
properties files.
- The project components MUST be compiled in the following order (so as to
correctly configure the dependencies):
  - The following MAY be compiled in any order:
    - << our components >>
  - The following projects SHOULD be compiled in the given order,
  notwithstanding the possibility of a local, separed development environment:
    - `mudeopen-fo-fe` (note that the `/dist` ?? folder MAY be used as a dependency for 
    the `mudeopen-bo-be/angular` ??? project)
    - `mudeopen-bo-be`
- Configure the DBMS via the scripts in the `mudeopen-db` project.
- To test the backend resources, the preferred way is to deploy the backend
project and use a REST client to access the resources.

## Production environment
For the production environment, the same configuration for the local environment
may be used. We strongly suggest against using an exact replica of such
configuration due to the load on the server.

- A load balancer MAY be configured at least for the REST resources.
- The DMBS and AS environments SHOULD be kept separated. The added overhead due
to the TCP connection between two distinct machines is far overshadowed by the
load distribution.
- It is recommended to protect the URLs by preponing a WAF to the DMZ.
- It is recommended to implement a OAuth server to mediate the authentication
experience.
- The `mudeopen-fo-fe` project MUST be compiled before the `mudeopen-bo-be` project (the
definitions given for MAY/SHOULD in the "Local environment" section are to be
changed to MUST).
