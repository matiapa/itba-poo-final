package game.backend.cell;

import game.backend.Grid;
import game.backend.element.TimeBonusCandy;

public class TimeBonusCandyGeneratorCell extends SpecialCandyGeneratorCell{

    public TimeBonusCandyGeneratorCell(Grid grid, int timesBonusToPlace, double timeBonusFrequency) {
        super(grid, TimeBonusCandy.class, timesBonusToPlace, timeBonusFrequency);
    }

}
