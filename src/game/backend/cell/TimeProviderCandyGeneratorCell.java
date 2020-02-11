package game.backend.cell;

import game.backend.Grid;
import game.backend.element.TimeProviderCandy;

public class TimeProviderCandyGeneratorCell extends SpecialCandyGeneratorCell{

    public TimeProviderCandyGeneratorCell (Grid grid, int bombsToPlace, double bombFrequency) {
        super(grid, TimeProviderCandy.class, bombsToPlace, bombFrequency);

    }
}
