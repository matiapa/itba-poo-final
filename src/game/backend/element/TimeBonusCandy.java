package game.backend.element;

import game.backend.GameListener;
import game.backend.Grid;
import game.backend.level.Level4;
import game.backend.move.Direction;

public class TimeBonusCandy extends Candy {

    public TimeBonusCandy(Grid grid, CandyColor color) {
        super(grid, color);

        grid.addListener(new GameListener(){

            @Override public void gridUpdated(){}

            @Override public void cellExplosion(Element e){}

            @Override public void onValidMove(){}

            @Override public void scorePanelUpdated(){}

        });
    }

    public int getAdditionalTime() {
        return Level4.getAdditionalTime();
    }


    @Override
    public Direction[] explode() {
        // This is to avoid the figures that get formed during grid loading
        if(grid.getGridLoadReady()){
            ((Level4) grid).timeBonusConsumed();
        }
        return super.explode();
    }

}
