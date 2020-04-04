package it.polimi.ingsw.model.Cards;

public class GodDescription {

    public static String[] list = new String[9];

    private void setList(){

        list[0]= "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario, mandandolo nella tua attuale casella";
        list[1]= "Il tuo lavoratore può spostarsi una volta in più, ma non può tornare nella casella in sui è partito";
        list[2]= "Se nel tuo turno uno dei tuoi lavoratori è salito di livello, in questo turno i lavoratori degli avversari non possono salire di livello";
        list[3]= "Il tuo lavoratore può costruire una cupola su qualsiasi livello, compreso il terreno";
        list[4]= "Il tuo lavoratore può costruire una volta in più, ma non nella stessa casella";
        list[5]= "Il tuo lavoratore può costruire un blocco aggiuntuvo (non una cupola) al di sopra del primo blocco";
        list[6]= "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario se la casella successiva nella stessa direzione è libera, spostando il lavoratore avversario in questa (indipendentemente dal livello)";
        list[7]= "Vinci anche se il tuo lavoratore è sceso di due o più livelli";
        list[9]= "Se il tuo lavoratore non sale di livello, può costruire prima e dopo essere stato mosso";

    }

    /*

    singoli attributi con nome che le specifica

    public static String apolloDescription = "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario, mandandolo nella tua attuale casella";
    public static String artemisDescription = "Il tuo lavoratore può spostarsi una volta in più, ma non può tornare nella casella in sui è partito";
    public static String athenaDescription = "Se nel tuo turno uno dei tuoi lavoratori è salito di livello, in questo turno i lavoratori degli avversari non possono salire di livello";
    public static String atlasDescription = "Il tuo lavoratore può costruire una cupola su qualsiasi livello, compreso il terreno";
    public static String demeterDescription = "Il tuo lavoratore può costruire una volta in più, ma non nella stessa casella";
    public static String hephaestusDescription = "Il tuo lavoratore può costruire un blocco aggiuntuvo (non una cupola) al di sopra del primo blocco";
    public static String minotaurDescription = "Il tuo lavoratore può spostarsi nella casella di un lavoratore avversario se la casella successiva nella stessa direzione è libera, spostando il lavoratore avversario in questa (indipendentemente dal livello)";
    public static String panDescription = "Vinci anche se il tuo lavoratore è sceso di due o più livelli";
    public static String prometheusDescription = "Se il tuo lavoratore non sale di livello, può costruire prima e dopo essere stato mosso";
     */

}
