package game.backend.cell;

import game.backend.Grid;
import game.backend.element.BombCandy;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;

public class BombCandyGeneratorCell extends GeneratorCell{

    private int bombsToPlace;
    private double bombFrequency;
    private int bombInitialMoves;

    public BombCandyGeneratorCell(Grid grid, int bombsToPlace, double bombFrequency, int bombInitialMoves) {
        super(grid);
        this.bombsToPlace = bombsToPlace;
        this.bombFrequency = bombFrequency;
        this.bombInitialMoves = bombInitialMoves;
    }

    @Override
    public Element getContent() {
        int i = (int)(Math.random() * CandyColor.values().length);
        return bombsToPlace>0 && Math.random()<bombFrequency ? new BombCandy(CandyColor.values()[i], bombInitialMoves) : new Candy(CandyColor.values()[i]);
    }
}
