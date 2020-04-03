package it.polimi.ingsw.model;

public enum BlockType {

    EMPTY("0"), BLOCK1("1"), BLOCK2("2"), BLOCK3("3"), CUPOLA("4");

    private String abbreviation;

    BlockType(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}