package it.polimi.ingsw.observer;

public interface Observer<T> {

    void update(T message);

}

/*

Chi ha extends observable<oggettoX> ---> ha il metodo notify
Chi ha implements observer(oggettoX) ---> ha il metodo (@Override) update(oggettoX)

Bisogna aggiungere con addObserver:
-   chi ha la notyfy è un oggetto osservabile e notifica un determinato observable per l'oggetto
    (classe observable).addObserver(classe observer) --->   nella classe observer sarà ridefinito update in modo da poter
                                                            ricevere la notifica tramite la notify specifica per l'oggetto
                                                            contenuto come parametro nella notify (e quindi nell'update)
-   chi ha l'override di update è un observer di qualche classe per un determinato oggetto e deve reimplementare il metodo
    update aspettandosi di ricevere sempre quell'oggetto ogni volta che la classe observable notifica un cambiamento
---------------------------------------------------------------------------------------------------------------------------------
NOSTRO CASO:
Abbiamo già usato funzioni per il passaggio dal socket alla view e dalla view al controller (dovrebbe andare bene anche senza
observer ???).
Bisogna implementare la mappa e il mazzo come observable (extends observable) in cui ogni cambiamento viene notificato dal metodo
notify e bisogna implementare le varie remoteView per consentire il ricevimento di questi messaggi (implements observer) sia per
la mappa sia per il mazzo facendo due @Override del metodo update (quindi creare due classi dentro la classe remoteView e una
sarà ---> implements observer(Map) <--- e l'altra sarà ---> implements observer(Deck) <---)

Quindi:     Map.addObserver(RemoteView1)
            Map.addObserver(RemoteView2)    --->    tutte con la notify(Map)
            Map.addObserver(RemoteView3)

            Deck.addObserver(RemoteView1)
            Deck.addObserver(RemoteView2)    --->    tutte con la notify(Deck)
            Deck.addObserver(RemoteView3)

            per messaggi stringa stessa cosa??? e bisogna perforza passare dal model???
            il controller può essere observable per la view per le classi messaggi???

*/