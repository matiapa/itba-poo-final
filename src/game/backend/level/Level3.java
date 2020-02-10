package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.BombCandyGeneratorCell;
import game.backend.element.BombCandy;

public class Level3 extends Grid {

	private static int BOMB_INITIAL_MOVES = 10;
	private static int NUMBER_OF_BOMBS = 2*SIZE;
	private static double BOMB_FREQUENCY = 0.1;

	public static int getInitialMoves(){ return BOMB_INITIAL_MOVES; }


	public Level3(){
		super(new Level3State(NUMBER_OF_BOMBS));
	}


	@Override
	protected void fillCells() {
		// An equal amount of bombs to place is assigned to each genCell
		genCell = new BombCandyGeneratorCell(this, NUMBER_OF_BOMBS/SIZE, BOMB_FREQUENCY);
		super.fillCells();
	}


	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			state().addMove();
			//updateBombCandies();
			wasUpdated();
		}
		return ret;
	}

	public void bombExploded(){ ((Level3State) state()).bombExploded(); }

	// Runs all the grid, updates the remaining movements of each bomb, and counts the bombs on the grid
	private void updateBombCandies(){

		int remainingMoves = BOMB_INITIAL_MOVES;
		int bombsOnGrid = 0;

		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if(g()[i][j].getContent() instanceof BombCandy){

					BombCandy bomb = (BombCandy) g()[i][j].getContent();

					// Decrease the remaining moves
					if(bomb.decreaseRemainingMoves()){
						((Level3State) state()).bombExploded();
					}

					// Update the remaining moves
					remainingMoves = bomb.getRemainingMoves() < remainingMoves ? bomb.getRemainingMoves() : remainingMoves;

					// Update bombs on grid
					bombsOnGrid += 1;

				}
			}
		}

		((Level3State) state()).setRemainingMovements(remainingMoves);
		((Level3State) state()).setBombsOnGrid(bombsOnGrid);

	}


	// -------------------------------------------------------- GAME STATE --------------------------------------------------------


	static private class Level3State extends GameState {

		private long bombsToPlace;
		private boolean bombExploded=false;

		private int remainingMovements;
		private int bombsOnGrid;


		public Level3State(int numberOfBombs) {
			this.bombsToPlace = numberOfBombs;
		}


		public boolean gameOver() { return playerWon() || bombExploded; }
		
		public boolean playerWon() {
			return bombsToPlace==0 && bombsOnGrid==0;
		}

		@Override
		public String toString() {
			return String.format("%s \nRemaining movements: %d", super.toString(), remainingMovements);
		}

		private void bombExploded(){ bombExploded=true; }

		private void setRemainingMovements(int remainingMovements){ this.remainingMovements = remainingMovements; }

		public void setBombsOnGrid(int bombsOnGrid) { this.bombsOnGrid = bombsOnGrid; }
	}

}
