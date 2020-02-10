package game.backend.element;

import game.backend.GameListener;
import game.backend.Grid;
import game.backend.level.Level3;

public class BombCandy extends Candy {

    BombCandy(){
        grid.addListener(new GameListener(){

            @Override public void gridUpdated(){}

            @Override public void cellExplosion(Element e){}

            @Override
            public void onValidMove() {

            }

        });
    }

    private int remainingMoves = Level3.getInitialMoves();

    public BombCandy(Grid grid, CandyColor color) {
        super(grid, color);
    }

    public int getRemainingMoves(){ return remainingMoves; }

    public boolean decreaseRemainingMoves(){
        remainingMoves -= 1;
        return remainingMoves<=0;
    }

    /*@Override
    public Direction[] explode() {
        ((Level3) grid).bombExploded();
        return super.explode();
    }*/

}
