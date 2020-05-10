package it.polimi.ingsw.model.cards;

import java.util.HashMap;

public enum God {

    APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS,
    CHRONUS, HERA, HESTIA, TRITON, ZEUS;    //NEW

    static HashMap<God, String> godDescription = new HashMap<>() {
        {
            put(APOLLO, "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario, mandandolo nella tua attuale casella.");
            put(ARTEMIS, "Il tuo lavoratore può spostarsi una volta in più, ma non può tornare nella casella in cui è partito.");
            put(ATHENA, "Se nel tuo turno uno dei tuoi lavoratori è salito di livello, in questo turno i lavoratori degli avversari non possono salire di livello.");
            put(ATLAS, "Il tuo lavoratore può costruire una cupola su qualsiasi livello, compreso il terreno.");
            put(CHRONUS, "Vinci anche quando ci sono almeno 5 torri complete sulla mappa.");
            put(DEMETER, "Il tuo lavoratore può costruire una volta in più, ma non nella stessa casella.");
            put(HEPHAESTUS, "Il tuo lavoratore può costruire un blocco aggiuntuvo (non una cupola) al di sopra del primo blocco.");
            put(HERA, "Un avversario non può vincere muovendo in uno spazio perimetrale.");
            put(HESTIA, "Il tuo lavoratore può costruire una volta in più, ma non in uno spazio perimetrale.");
            put(MINOTAUR, "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario se la casella successiva nella stessa direzione è libera, spostando il lavoratore avversario in questa (indipendentemente dal livello).");
            put(PAN, "Vinci anche se il tuo lavoratore è sceso di due o più livelli.");
            put(PROMETHEUS, "Se il tuo lavoratore non sale di livello, può costruire prima e dopo essere stato mosso.");
            put(TRITON, "Ogni volta che il tuo lavoratore si sposta in uno spazio perimetrale, puoi muoverlo un'altra volta.");
            put(ZEUS, "Il tuo lavoratore può costruire un blocco sotto di sè.");
        }
    };

    public static String getGodDescription(God god){
        return godDescription.get(god);
    }

    public static void printCardChosen(God god){
        System.out.println("Hai il potere di " + god.toString() + ": " + getGodDescription(god));
    }
}
