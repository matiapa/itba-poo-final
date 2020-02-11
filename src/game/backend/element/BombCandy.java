package game.backend.element;

import game.backend.GameListener;
import game.backend.Grid;
import game.backend.level.Level3;
import game.backend.move.Direction;

public class BombCandy extends Candy {

    private int remainingMoves = Level3.getInitialMoves();

    public BombCandy(Grid grid, CandyColor color) {
        super(grid, color);
        grid.addListener(new GameListener(){

            @Override public void gridUpdated(){}

            @Override public void cellExplosion(Element e){}

            @Override
            public void scorePanelUpdated() {

            }

            @Override
            public void onValidMove() {

                remainingMoves -= 1;
                if(remainingMoves <= 0){
                    ((Level3) grid).bombExploded();
                }

                ((Level3) grid).informBombRemainingMoves(remainingMoves);

            }

        });
    }

    @Override
    public Direction[] explode() {
        ((Level3) grid).bombDeactivated();
        return super.explode();
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

}
