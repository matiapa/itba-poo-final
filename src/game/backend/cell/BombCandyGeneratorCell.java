package game.backend.cell;

import game.backend.Grid;
import game.backend.element.BombCandy;
import game.backend.element.HorizontalStripedCandy;

public class BombCandyGeneratorCell extends SpecialCandyGeneratorCell {

    public BombCandyGeneratorCell(Grid grid, int bombsToPlace, double bombFrequency) {
        super(grid, HorizontalStripedCandy.class, bombsToPlace, bombFrequency);
    }

}
