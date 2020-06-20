# Prova Finale di Ingegneria del Software - a.a. 2019-2020
![alt text](https://2.bp.blogspot.com/-YHuiYPBEHKs/WVKpXTzu5KI/AAAAAAAAYCs/pTVyng97P3EDoLq9PMqVv18ECzBD4K2CwCLcBGAs/s1600/copertina_santorini_2016.jpg)

Scopo del progetto è quello di implementare il gioco da tavola [Santorini](https://roxley.com/products/santorini) seguendo il pattern architetturale Model View Controller per la realizzazione del modello secondo il paradigma di programmazione orientato agli oggetti. Il risultato finale copre completamente le regole definite dal gioco e permette di interagirci sia con una interfaccia da linea di comando (CLI) che grafica (GUI), la rete è stata gestita con il tradizionale approccio delle socket.

## Documentazione
La seguente documentazione comprende i documenti realizzati per la progettazione del problema, verranno prima elencati i diagrammi delle classi in UML poi la documentazione del codice (javadoc).

### UML
I seguenti diagrammi delle classi rappresentano: il modello secondo il quale il gioco dovrebbe essere stato implementato e i diagrammi del prodotto finale.
- [UML Iniziali](https://github.com/ToMmAzO/ing-sw-2020-Pozzi-Quagliana-Simeoni/blob/master/deliverables/UML/Initial.mdj)
- [UML Finali](https://github.com/ToMmAzO/ing-sw-2020-Pozzi-Quagliana-Simeoni/blob/master/deliverables/UML/Santorini.mdj)

### JavaDoc
La seguente documentazione include una descrizione per la maggiore parte delle classi e dei metodi utilizzati seguendo le tecniche di documentazione di Java.

### Librerie e Plugins
|Libreria/Plugin|Descrizione|
|---------------|-----------|
|__maven__|strumento di gestione per software basati su Java e build automation|
|__junit__|framework dedicato a Java per unit testing|
|__Swing__|libreria grafica di Java|

### Jars
I seguenti jar sono stati utilizzati per la consegna del progetto, permettono quindi il lancio del gioco secondo le funzionalità descritte nell'introduzione. Le funzionalità realizzate secondo la specifica del progetto sono elencate nella prossima sezione mentre i dettagli per come lanciare il sistema saranno definiti nella sezione chiamata __Esecuzione dei jar__. La cartella in cui si trovano il software del client e del server si trova al seguente indirizzo: [Jars](https://github.com/ToMmAzO/ing-sw-2020-Pozzi-Quagliana-Simeoni/blob/master/deliverables/jar).

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- CLI
- GUI
- Socket

### Funzionalità Aggiuntive Sviluppate
- Divinità Avanzate
- Persistenza ?

## Esecuzione dei JAR
### Client
Il client viene eseguito scegliendo l'interfaccia con cui giocare, le possibili scelte sono da linea di comando o interfaccia grafica. Le seguenti sezioni descrivono come eseguire il client in un modo o nell'altro.

#### CLI
Per lanciare il client in modalità CLI digitare il seguente comando:

#### GUI
Per lanciare il client in modalità GUI digitare il seguente comando:

### Server
Per eseguire il server è solamente necessario configurare alcune delle sue caratteristiche attraverso un file di configurazione in formato json.

## Componenti del gruppo
- [__Tommaso Pozzi__](https://github.com/ToMmAzO)
- [__Luca Quagliana__](https://github.com/LucaQuagliana)
- [__Raffaele Simeoni__](https://github.com/RaffaeleSimeoni)
