Esempio di gestione degli errori
===

I profili da utilizzare sono:

1. example-quickstarts-errors

I codici sorgente di questo profilo si possono trovare nella cartella quickstarts di jboss-fuse.

Questo esempio esegue i seguenti passi:

1. prende in input un file (contiene un order come per il quickstarts cbr)
1. scrive sul log il file che si sta processando
1. esegue una validazione per data dell'ordine (l'ordine numero 4 fallisce perché la data è Domenica 4-3-2012)
1. esegue un metodo che lancia un eccezione con una probabilità di 2 su 3.
1. salva il file fra quelli elaborati
1. scrive sul log che il file ha terminato l'elaborazione

Processo principale:
``` xml
<route id="mainRoute">
    <from uri="file:work/errors/input"/>
    <log message="Processing ${file:name}"/>
    <to uri="bean:myOrderService?method=validateOrderDate"/>
    <to uri="bean:myOrderService?method=randomlyThrowRuntimeException"/>
    <to uri="file:work/errors/done"/>
    <log message="Done processing ${file:name}"/>
</route>
```

Nel caso in cui venga lanciata una eccezione dal metodo **_randomlyThrowRuntimeException_** l'ordine in elaborazione finisce nella DLQ:
``` xml
<bean id="myDeadLetterErrorHandler" class="org.apache.camel.builder.DeadLetterChannelBuilder">
    <property name="deadLetterUri" value="direct:deadletter"/>
    <property name="redeliveryPolicy">
        <bean class="org.apache.camel.processor.RedeliveryPolicy">
            <property name="maximumRedeliveries" value="2"/>
            <property name="useExponentialBackOff" value="true"/>
        </bean>
    </property>
</bean>
```
``` xml
<route id="dlcRoute">
    <from uri="direct:deadletter"/>
    <log message="File ${file:name} was moved to the dead letter channel"/>
    <to uri="file:work/errors/deadletter"/>
</route>
```

Le eccezioni lanciate dal metodo di validazione per data invece sono gestite tramite la definizione di un error handler dentro il contesto camel
``` xml
<onException>
    <exception>org.jboss.quickstarts.fuse.errors.OrderValidationException</exception>
    <handled>
        <constant>true</constant>
    </handled>
    <log message="Validation failed for ${file:name} - moving the file to work/errors/validation"/>
    <to uri="file:work/errors/validation"/>
</onException>
```

Nel quickstart (cartella src/main/resources/data) sono disponibili degli esempi di ordini da poter inviare alla rotta camel.
Per inviarli è sufficiente accedere agli endpoint del contesto camel o copiare il file nella cartella "instances/<container>/work/errors/input"

**Importante**

Per mostrare che fine fanno gli ordini è possibile visualizzare dal file system le cartelle di output:

* Ordini processati correttamente -> "instances/<container>/work/errors/done"
* Ordini in DLQ -> "instances/<container>/work/errors/deadletter"
* Ordini non validati correttamente -> "instances/<container>/work/errors/validation"
