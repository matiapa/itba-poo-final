package game.backend.cell;

import game.backend.Grid;
import game.backend.element.BombCandy;

public class BombCandyGeneratorCell extends SpecialCandyGeneratorCell {

    public BombCandyGeneratorCell(Grid grid, int bombsToPlace, double bombFrequency) {
        super(grid, BombCandy.class, bombsToPlace, bombFrequency);
    }

}
