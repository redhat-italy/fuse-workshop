Esempio per lo sviluppo di un HelloWorld:
===

Questo esempio è stato pensato per essere installato tramite maven con il plugin fabric8.

A seguito della creazione di un container associato al profilo creato dal plugin fabric8 (nome: my-camel-simple) sarà
possibile mettersi in `fabric:watch *` e vedere l'aggiornamento del container a seguito di nuove compilazioni del sorgente.

Il contenuto del progetto è unicamente il file blueprint del camel context, che contiene la seguente rotta:

``` xml
<route id="simple-route">
  <from uri="timer://foo?fixedRate=true&amp;period=10000"/>
  <setBody>
    <simple>Hello world!</simple>
  </setBody>
  <log message="Message received: ${body}"/>
</route>
```