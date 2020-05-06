package it.polimi.ingsw.model.Cards;

import java.util.HashMap;

public enum God {

    APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;

    static HashMap<God, String> godDescription = new HashMap<>() {
        {
            put(APOLLO, "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario, mandandolo nella tua attuale casella.");
            put(ARTEMIS, "Il tuo lavoratore può spostarsi una volta in più, ma non può tornare nella casella in sui è partito.");
            put(ATHENA, "Se nel tuo turno uno dei tuoi lavoratori è salito di livello, in questo turno i lavoratori degli avversari non possono salire di livello.");
            put(ATLAS, "Il tuo lavoratore può costruire una cupola su qualsiasi livello, compreso il terreno.");
            put(DEMETER, "Il tuo lavoratore può costruire una volta in più, ma non nella stessa casella.");
            put(HEPHAESTUS, "Il tuo lavoratore può costruire un blocco aggiuntuvo (non una cupola) al di sopra del primo blocco.");
            put(MINOTAUR, "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario se la casella successiva nella stessa direzione è libera, spostando il lavoratore avversario in questa (indipendentemente dal livello).");
            put(PAN, "Vinci anche se il tuo lavoratore è sceso di due o più livelli.");
            put(PROMETHEUS, "Se il tuo lavoratore non sale di livello, può costruire prima e dopo essere stato mosso.");
        }
    };

    public static String getGodDescription(God god){
        return godDescription.get(god);
    }

    public static void printCardChosen(God god){
        System.out.println("Hai il potere di " + god.toString() + ": " + getGodDescription(god));
    }
}
