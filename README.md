# Prova Finale di Ingegneria del Software - a.a. 2019-2020
![alt text](https://2.bp.blogspot.com/-YHuiYPBEHKs/WVKpXTzu5KI/AAAAAAAAYCs/pTVyng97P3EDoLq9PMqVv18ECzBD4K2CwCLcBGAs/s1600/copertina_santorini_2016.jpg)

Scopo del progetto è quello di implementare il gioco da tavola [Santorini](https://roxley.com/products/santorini) seguendo il pattern architetturale Model View Controller per la realizzazione del modello secondo il paradigma di programmazione orientato agli oggetti. Il risultato finale copre completamente le regole definite dal gioco e permette di interagirci sia con una interfaccia da linea di comando (CLI) che grafica (GUI), la rete è stata gestita con il tradizionale approccio delle socket.

## Documentazione
La seguente documentazione comprende i documenti realizzati per la progettazione del problema, verranno prima elencati i diagrammi delle classi in UML poi la documentazione del codice (javadoc).

### UML
I seguenti diagrammi delle classi rappresentano: il modello secondo il quale il gioco dovrebbe essere stato implementato e i diagrammi del prodotto finale.
- [UML Iniziali](https://github.com/ToMmAzO/ing-sw-2020-Pozzi-Quagliana-Simeoni/blob/master/deliverables/uml/initial)
- [UML Finali](https://github.com/ToMmAzO/ing-sw-2020-Pozzi-Quagliana-Simeoni/blob/master/deliverables/uml/final)

### JavaDoc
La seguente documentazione include una descrizione per la maggiore parte delle classi e dei metodi utilizzati seguendo le tecniche di documentazione di Java.

### Test Coverage
![alt text](deliverables/testCoverage/TestCoverage.png)

### Librerie e Plugins
|Libreria/Plugin|Descrizione|
|---------------|-----------|
|__maven__|strumento di gestione per software basati su Java e build automation|
|__junit__|framework dedicato a Java per unit testing|
|__Swing__|libreria grafica di Java|

### Jars
I seguenti jar sono stati utilizzati per la consegna del progetto, permettono quindi il lancio del gioco secondo le funzionalità descritte nell'introduzione. Le funzionalità realizzate secondo la specifica del progetto sono elencate nella prossima sezione mentre i dettagli per come lanciare il sistema saranno definiti nella sezione chiamata __Esecuzione dei jar__. Per permettere la corretta esecuzione del gioco è necessario eseguire prima il __server.jar__ e poi i __client.jar__. La cartella in cui si trovano il software del client e del server si trova al seguente indirizzo: [Jars](https://github.com/ToMmAzO/ing-sw-2020-Pozzi-Quagliana-Simeoni/blob/master/deliverables/jar).

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- CLI
- GUI
- Socket

### Funzionalità Aggiuntive Sviluppate
- Divinità Avanzate

## Esecuzione dei JAR
### Client
Il client viene eseguito scegliendo l'interfaccia con cui giocare, le possibili scelte sono da linea di comando o interfaccia grafica. Le seguenti sezioni descrivono come eseguire il client in un modo o nell'altro.

#### CLI
Per lanciare il client in modalità CLI digitare il seguente comando:
```
java -jar client.jar -ip [server_ip] -cli
```

#### GUI
Per lanciare il client in modalità GUI digitare il seguente comando:
```
java -jar client.jar -ip [server_ip] -gui
```

#### Parameters
- `-ip 127.0.0.1`: consente di inserire l'indirizzo ip del server a cui collegarsi, nel caso mostrato 127.0.0.1
- `-cli/-gui`: permette di scegliere se giocare in cli/gui. Se non specificato il valore di default è __gui__;


### Server
L'esecuzione del server avviene attraverso il seguente comando:
```
java -jar server.jar
```

## Componenti del gruppo
- [__Tommaso Pozzi__](https://github.com/ToMmAzO)
- [__Luca Quagliana__](https://github.com/LucaQuagliana)
- [__Raffaele Simeoni__](https://github.com/RaffaeleSimeoni)
