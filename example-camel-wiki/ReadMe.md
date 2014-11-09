Esempio HelloWorld:
===

Il profilo da utilizzare è:

1. example-camel-wiki

Carica una rotta di camel dal profilo disponibile dal wiki (da questo il nome wiki) che esegue un timer ogni 5 secondi e
visualizza nel log il messaggio.

Si consiglia di utilizzare questo esempio per il rolling upgrade.

Rotta:
``` html
<route id="route1">
    <from uri="timer://foo?fixedRate=true&amp;period=5000"/>
    <setBody>
        <simple>Hello from Fabric based Camel route!</simple>
    </setBody>
    <log message=">>> ${body} : ${header.karaf.name}"/>
</route>
```

![Log della versione 1.0](log_version_1_0.jpg)

Tramite l'interfaccia grafica creiamo una nuova versione 1.1 e modifichiamo il file camel.xml presente nel wiki come segue
``` html
<route id="route1">
    <from uri="timer://foo?fixedRate=true&amp;period=5000"/>
    <setBody>
        <simple>Hello from Fabric based Camel route (version 1.1)!</simple>
    </setBody>
    <log message=">>> ${body} : ${header.karaf.name}"/>
</route>
```

![Log della versione 1.1](log_version_1_1.jpg)
