package it.polimi.ingsw.network.message;

public class Message_Welcome extends Message{

    private final Target target = Target.WELCOME;

    public Target getTarget(){
        return target;
    }

    @Override
    public void printMessage(){
        System.out.println("Attendi il tuo turno per scegliere la carta divinità.");
    }

}
