package it.polimi.ingsw.model.Board;

public enum BlockType {

    GROUND(0), BLOCK1(1), BLOCK2(2), BLOCK3(3), CUPOLA(4);

    private final int abbreviation;

    BlockType(int abbreviation){
        this.abbreviation = abbreviation;
    }

    public int getAbbreviation() {
        return abbreviation;
    }

}
