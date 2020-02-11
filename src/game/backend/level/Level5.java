package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;

public class Level5 extends Grid {

    private static int MAX_MOVES = 20;

    private static int NUMBER_OF_WALLS = 9;
    private static int WALL_SIDE_LENGTH = 3;


    public Level5() {
        super();
        initialize(new Level5State(NUMBER_OF_WALLS, MAX_MOVES));
        paintInitialWalls();
    }

    @Override
    protected void fillCells() {
        setGenCell(new CandyGeneratorCell(this));
        super.fillCells();
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

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            getState().addMove();
        }
        return ret;
    }

    // -------------------------------------------------------- GAME STATE --------------------------------------------------------

    private class Level5State extends GameState {

        private int maxMoves;
        private int wallsLeft;

        public Level5State(int wallsLeft, int maxMoves) {
            this.wallsLeft = wallsLeft;
            this.maxMoves = maxMoves;
        }

        @Override
        public boolean playerWon() {
            return wallsLeft == 0;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() == maxMoves;
        }

        private void addBrokenWalls(int wallsBroken) {
            this.wallsLeft -= wallsBroken;
        }


        @Override
        public String toString() {
            return String.format("%s \nRemaining walls: %d", super.toString(), wallsLeft);
        }
    }
}

