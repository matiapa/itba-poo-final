package game.backend.element;

import game.backend.Grid;
import game.backend.level.Level4;
import game.backend.move.Direction;

public class TimeProviderCandy extends Candy {

    public TimeProviderCandy(Grid grid, CandyColor color) {
        super(grid, color);
    }

    @Override
    public Direction[] explode() {
        ((Level4)grid).timeProviderConsumed();
        return super.explode();
    }
}
