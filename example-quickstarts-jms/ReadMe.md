Esempio di CBR con Camel e AMQ
===

Il profilo da utilizzare è:

1. example-quickstarts-jms.wiki

Per far funzionare questo esempio è necessario avere un broker AMQ attivo.

Questo esempio crea due rotte camel:
1. prende in input un file e lo invia sulla coda degli ordini in arrivo
1. dalla coda degli ordini in arrivo smista l'ordine in base al suo contenuto.

Rotta per il caricamento dei nuovi ordini sulla coda:
``` xml
<route id="file-to-jms-route">
    <from uri="file:work/jms/input" />
    <log message="Receiving order ${file:name}" />
    <to uri="amq:incomingOrders" />
</route>
```

Rotta per lo smistamento degli ordini:
``` xml
<route id="jms-cbr-route">
    <from uri="amq:incomingOrders" />
    <choice>
        <when>
            <xpath>/order:order/order:customer/order:country = 'UK'</xpath>
            <log message="Sending order ${file:name} to the UK" />
            <to uri="file:work/jms/output/uk" />
        </when>
        <when>
            <xpath>/order:order/order:customer/order:country = 'US'</xpath>
            <log message="Sending order ${file:name} to the US" />
            <to uri="file:work/jms/output/us" />
        </when>
        <otherwise>
            <log message="Sending order ${file:name} to another country" />
            <to uri="file:work/jms/output/others" />
        </otherwise>
    </choice>
    <log message="Done processing ${file:name}" />
</route>
```

Nel quickstart (cartella src/main/resources/data) sono disponibili degli esempi di ordini da poter inviare alla rotta camel.
Per inviarli è sufficiente accedere agli endpoint del contesto camel o copiare il file nella cartella "instances/<container>/work/jms/input"

**Importante**

Per mostrare che fine fanno gli ordini è possibile visualizzare dal file system la cartella di output "instances/<container>/work/jms/output"
