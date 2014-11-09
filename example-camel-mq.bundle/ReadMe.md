Esempio di comunicazione tramite un broker AMQ
===

**Non mi ha funzionato per un problema di dipendenze**

Il profilo da utilizzare è:

1. example-camel-mq.bundle

Richiede un broker AMQ disponibile, altrimenti il container rimane in attesa.

Il profile crea due semplice rotte camel,
1. esegue un timer ogni 5 secondi, manipola il contenuto del messaggio e lo invia ad una componente AMQ sulla coda _camel-test_
1. riceve i messaggi dalla coda _camel-test_ e ne scrive il contenuto sul log.
