Esempio di CBR utilizzando una rotta camel dal wiki
===

Il profilo da utilizzare è:

1. example-quickstarts-cbr.wiki

Questo esempio crea una rotta camel complessa che esegue diverse operazioni in base al contenuto del messaggio.

Di seguito la descrizione della rotta riportata nel file camel:

> When this route is started, it will automatically create the work/cbr/input directory where you can drop the
> file that need to be processed.
>
> The 'log' elements are used to add human-friendly business logging statements. They make it easier to see what the
> route is doing.
>
> The 'choice' element contains the content based router. The two 'when' clauses use XPath to define the criteria
> for entering that part of the route. When the country in the XML message is set to UK or US, the file will be
> moved to a directory for that country. The 'otherwise' element ensures that any file that does not meet the
> requirements for either of the 'when' elements will be moved to the work/cbr/output/others directory.

Rotta camel:

``` xml
<route id="cbr-route">
    <from uri="file:work/cbr/input" />
    <log message="Receiving order ${file:name}" />
    <choice>
        <when>
            <xpath>/order:order/order:customer/order:country = 'UK'</xpath>
            <log message="Sending order ${file:name} to the UK" />
            <to uri="file:work/cbr/output/uk" />
        </when>
        <when>
            <xpath>/order:order/order:customer/order:country = 'US'</xpath>
            <log message="Sending order ${file:name} to the US" />
            <to uri="file:work/cbr/output/us" />
        </when>
        <otherwise>
            <log message="Sending order ${file:name} to another country" />
            <to uri="file:work/cbr/output/others" />
        </otherwise>
    </choice>
    <log message="Done processing ${file:name}" />
</route>
```

Nel quickstart (cartella src/main/resources/data) sono disponibili degli esempi di ordini da poter inviare alla rotta camel.
Per inviarli è sufficiente accedere agli endpoint del contesto camel o copiare il file nella cartella "instances/<container>/work/cbr/input"

**Importante**

Per mostrare che fine fanno gli ordini è possibile visualizzare dal file system la cartella di output "instances/<container>/work/cbr/output"
