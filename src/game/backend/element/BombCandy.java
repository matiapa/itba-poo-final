package game.backend.element;

public class BombCandy extends Candy {

    private int remainingMoves;

    public BombCandy(CandyColor color, int initialMoves) {
        super(color);
        this.remainingMoves = initialMoves;
    }

    public int getRemainingMoves(){ return remainingMoves; }

    public boolean decreaseRemainingMoves(){
        remainingMoves -= 1;
        return remainingMoves<=0;
    }

}
