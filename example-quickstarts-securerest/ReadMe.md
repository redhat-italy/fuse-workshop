Esempio per esporre un servizio ReST tramite CXF
===

Il profilo da utilizzare è:

1. example-quickstarts-securerest

I codici sorgente di questo profilo si possono trovare nella cartella quickstarts di jboss-fuse.

Questo esempio espone un API rest attraverso il servizio di CXF che prevede la necessità di una autenticazione.

Nella pagina delle API si può giocare un pochino col servizio ReST invocando direttamente i metodi esposti, ma l'interfaccia grafica
ogni tanto fa comparire dei balloon di errore anche se la chiamata è avvenuta con successo.

I metodi DELETE e PUT da interfaccia grafica non funzionano, restituiscono 405.

Per aggiungere un utente applicativo dobbiamo da CLI eseguire i seguenti comandi:

  * Lista dei realms presenti

          jaas:realms

  Output:

  | Index | Realm | Module Class |
  | --- | --- | ---------- |
  | 1 | karaf | org.apache.karaf.jaas.modules.properties.PropertiesLoginModule |
  | 2 | karaf | org.apache.karaf.jaas.modules.publickey.PublickeyLoginModule |
  | 3 | karaf | io.fabric8.jaas.ZookeeperLoginModule |

  * Gestire il realms corretto (per far funzionare l'autenticazione io ho dovuto aggiungere l'utente al modulo io.fabric8.jaas.ZookeeperLoginModule)

          jaas:manage --realm karaf --index 3

  * Aggiungere un utente

          jaas:useradd altro password

  * Salvare le modifiche

          jaas:update

Adesso che abbiamo l'utente possiamo lanciare i seguenti comandi CURL per verificare l'applicativo:

> **A differenza del quickstart rest senza sicurezza jaas questo non supporta il formato json, quindi gli esempi devono essere
> mostrati utilizzando i file xml disponibili.**

  * Verificare l'url di base con API o registry

          Dovrebbe essere simile a http://localhost:8182/cxf/crm

  * Create a customer

          curl -v --basic -u altro:password -X POST -T src/test/resources/add_customer.xml -H "Content-Type: application/xml" http://localhost:8182/cxf/securecrm/customerservice/customers

  * Retrieve the customer instance with id 123

          curl -v --basic -u altro:password http://localhost:8182/cxf/securecrm/customerservice/customers/123

  * Update the customer instance with id 123

          curl -v --basic -u altro:password -X PUT -T src/test/resources/update_customer.xml -H "Content-Type: application/xml" http://localhost:8182/cxf/securecrm/customerservice/customers

  * Delete the customer instance with id 123

           curl -v --basic -u altro:password -X DELETE http://localhost:8182/cxf/securecrm/customerservice/customers/123
