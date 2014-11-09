Esempio di combinazione di diversi EIP
===

Il profilo da utilizzare è:

1. example-quickstarts-eip

I codici sorgente di questo profilo si possono trovare nella cartella quickstarts di jboss-fuse.

Questo esempio esegue i seguenti passi:

1. prende in input un file contenente ordini
1. archivia in asincrono il file ricevuto (wiretap)
1. esegue lo split del file per ordine con una espressione xpath
1. invia ognuno ad una recipient list di due elementi:
    1. salva l'ordine in un nuovo file in una cartella di output (work/eip/output/)
    1. esegue un filter che scrive nel log ordini con quantità superiore a 100 animali


Ricezione ordini:
``` xml
<route id="mainRoute">
    <from uri="file:work/eip/input" />
    <log message="[main]    Processing ${file:name}" />
    <wireTap uri="direct:wiretap" />
    <to uri="direct:splitter" />
    <log message="[main]    Done processing ${file:name}" />
</route>
```

Archiviazione ordini:
``` xml
<route id="wiretapRoute">
    <from uri="direct:wiretap" />
    <log message="[wiretap]  Archiving ${file:name}" />
    <to uri="file:work/eip/archive" />
</route>
```

Split ordine
``` xml
<route id="splitterRoute">
    <from uri="direct:splitter" />
    <split>
        <xpath>//order:order</xpath>

        <setHeader headerName="orderId">
            <xpath>/order:order/@id</xpath>
        </setHeader>
        <setHeader headerName="region">
            <method bean="MyRegionSupport" method="getRegion" />
        </setHeader>

        <log message="[splitter] Shipping order ${header.orderId} to region ${header.region}" />

        <recipientList>
            <simple>file:work/eip/output/${header.region}?fileName=${header.orderId}.xml,direct:filter</simple>
        </recipientList>
    </split>
</route>
```

Filtro ordine:
``` xml
<route id="filterRoute">
    <from uri="direct:filter" />
    <filter>
        <xpath>sum(//order:quantity/text()) > 100</xpath>
        <log message="[filter]   Order ${header.orderId} is an order for more than 100 animals" />
    </filter>
</route>
```

Nel quickstart (cartella src/main/resources/data) sono disponibili degli esempi di ordini da poter inviare alla rotta camel.
Per inviarli è sufficiente accedere agli endpoint del contesto camel o copiare il file nella cartella "instances/<container>/work/eip/input"

**Importante**

Per mostrare che fine fanno gli ordini è possibile visualizzare dal file system le cartelle di output:

* Ordini completati per regione -> "instances/<container>/work/eip/output/<region>"

