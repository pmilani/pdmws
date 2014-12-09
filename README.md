PDM WebService
==================
Questo servizio serve da punto di integrazione nel data flow dai sistemi
aziendali ERP con il sistema PDM.


## Requisiti

Apache Maven 3
JDK 1.6 o successivo


## Build


```$ mvn clean install```

Il codice verra' generato, compilato e impacchettato per la distribuzione.

L'artefatto prodotto e' in `target/` ed e' un JAR eseguibile direttamente da
via `java -jar`.


## Esecuzione

```$ java -jar target/pdm-webservice-0.1.0.jar```

(o in caso di altre versioni basta cambiare il numero contenuto nel nome)

All'avvio sara' eseguito il bootstrap di un server web standalone.
Il server per default sara' disponibile su:
http://localhost:8090/ws

#### WSDL
http://localhost:8090/ws/pdm-ws.wsdl

#### Override settings
E' possibile passare da command-line i settings dell'applicazione come ad es.
--server.port=9000 
--server.address=192.168.0.1

Oppure creando un apposito file application.properties con tali properties

Vedere anche la doc. Spring Boot per altri parametri configurabili


## Test manuale

Per invocare il servizio manualmente si puo' utilizzare un tool come `curl`
o `wget`.

Es: inviare una richiesta addMetadata 
```$ curl --header "content-type: text/xml" -d @store-metadata-request.xml http://localhost:8090/ws```

La dir. `test/resources` contiene richieste d'esempio


## Design & Architettura

L'architettura prescelta e' quella dei microservices: questo servizio
e' autocontenuto e indipendente, e serve da punto di integrazione nel
data flow da sistemi aziendali ERP con il sistema PDM.
Il software e' un'applicazione java basata su Spring Boot.
Espone un Web Service endpoint WSDL 1.1
Il servizio risponde alle richieste HTTP al server web integrato.
Il server web integrato e' Tomcat e viene gestito automaticamente.


## IDE: sviluppare in Eclipse

Prima di tutto assicurarsi di aver eseguito una build (vedi sopra).
Quindi e' necessario generare i file di progetto:
```$ mvn eclipse:eclipse``` 

In Eclipse quindi si potra' importarlo come progetto Java esistente.
Poiche' con questo metodo non e' attiva l'integrazione maven con eclipse, ad ogni successiva modifica del POM 
bisognera' ri-eseguire il goal eclipse:eclipse e fare il refresh del workspace in Eclipse.


--

## Riferimenti

http://docs.spring.io/spring-boot/docs/1.1.9.RELEASE/reference/htmlsingle/
http://spring.io/guides/gs/producing-web-service/

