package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.BombCandyGeneratorCell;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Element;
import game.backend.element.HorizontalStripedCandy;
import game.backend.element.VerticalStripedCandy;

public class Level5 extends Grid {

    private static int MAX_MOVES = 20;
    private static int NUMBER_OF_WALLS = 9;
    private static int WALL_SIDE_LENGTH = 3;


    public Level5() {
        super();
        initialize(new Level5State());
        paintInitialWalls();
    }

    @Override
    protected void fillCells() {
        setGenCell(new BombCandyGeneratorCell(this, 70, 0.8));
        super.fillCells();
    }


    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        Element c1 = grid[i1][j1].getContent();
        Element c2 = grid[i2][j2].getContent();

        if (ret = super.tryMove(i1, j1, i2, j2)) {
            getState().addMove();
            if(c1 instanceof HorizontalStripedCandy || c2 instanceof HorizontalStripedCandy){
                removeCellWallRow(i2);
            }
            if(c1 instanceof VerticalStripedCandy || c2 instanceof VerticalStripedCandy){
                removeCellWallColumn(j2);
            }
            gridUpdated();
        }
        return ret;
    }

    public void removeCellWallRow(int i){
        for(int j=0; j<getSize(); j++) {
            if(grid[i][j].getEffect()!= Cell.CellEffect.NONE){
                ((Level5State) getState()).removeWall();
                grid[i][j].setEffect(Cell.CellEffect.NONE);
            }
        }
    }

    public void removeCellWallColumn(int j){
        for(int i=0; i<getSize(); i++) {
            if(grid[i][j].getEffect()!= Cell.CellEffect.NONE) {
                ((Level5State) getState()).removeWall();
                grid[i][j].setEffect(Cell.CellEffect.NONE);
            }
        }
    }


    private void paintInitialWalls() {
        int last = getSize() - WALL_SIDE_LENGTH;
        int first = last - WALL_SIDE_LENGTH;

        for (int i = first; i < last; i++) {
            for (int j = first; j < last; j++) {
                grid[i][j].setEffect(Cell.CellEffect.SANDYBROWN);
            }
        }
    }

    public void removeCellWall(int i, int j){
        if(grid[i][j].getEffect()!=Cell.CellEffect.NONE){
            ((Level5State) getState()).removeWall();
            grid[i][j].setEffect(Cell.CellEffect.NONE);
        }
        // TODO: Complete this
    }

    static public String levelInfo() {
        return "Walls are removed by special candies explosions. You should remove all the walls in no more than "+MAX_MOVES+" moves";
    }

    // -------------------------------------------------------- GAME STATE --------------------------------------------------------

    private class Level5State extends GameState {

        private int maxMoves = Level5.MAX_MOVES;
        private int wallsLeft = Level5.NUMBER_OF_WALLS;

        @Override
        public boolean playerWon() {
            return wallsLeft == 0;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() == maxMoves;
        }

        @Override
        public String toString() {
            return String.format("%s \nRemaining walls: %d", super.toString(), wallsLeft);
        }

        private void removeWall() { this.wallsLeft -= 1; }

    }

}

