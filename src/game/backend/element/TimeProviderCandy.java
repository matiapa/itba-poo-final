package game.backend.element;

import game.backend.GameListener;
import game.backend.Grid;
import game.backend.level.Level4;
import game.backend.move.Direction;

public class TimeProviderCandy extends Candy {

    public TimeProviderCandy(Grid grid, CandyColor color) {
        super(grid, color);

        grid.addListener(new GameListener(){

            @Override public void gridUpdated(){}

            @Override public void cellExplosion(Element e){}

            @Override
            public void onValidMove() {
                ((Level4) grid).timeProviderConsumed();
            }

            @Override
            public void scorePanelUpdated() {}


        });
    }

    public int getAdditionalTime() {
        return Level4.getAdditionalTime();
    }


    @Override
    public Direction[] explode() {
        ((Level4)grid).timeProviderConsumed();
        return super.explode();
    }
}
