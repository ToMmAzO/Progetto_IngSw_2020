package it.polimi.ingsw.model;

public enum Cell {

    BLOCK1("B1"), BLOCK2("B2"), BLOCK3("B3"), CUPOLA("C"), EMPTY("E"), WORKERLV0("W0"), WORKERLV1("W1"), WORKERLV2("W2"),WORKERLV3("W3");

    private String abbreviation;

    private Cell(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
