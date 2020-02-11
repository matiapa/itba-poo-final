package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.BombCandyGeneratorCell;
import game.backend.element.BombCandy;

public class Level3 extends Grid {

	private static int BOMB_INITIAL_MOVES = 10;
	private static int NUMBER_OF_BOMBS = 15;
	private static double BOMB_FREQUENCY = 0.1;

	public static int getInitialMoves(){ return BOMB_INITIAL_MOVES; }


	public Level3(){
		super(new Level3State());
	}


	@Override
	protected void fillCells() {
		// An equal amount of bombs to place is assigned to each genCell
		genCell = new BombCandyGeneratorCell(this, NUMBER_OF_BOMBS, BOMB_FREQUENCY);
		super.fillCells();
	}


	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			state().addMove();
			//updateBombCandies();//TODO dejaste esto asa
			wasUpdated();
		}
		return ret;
	}


	public void informBombRemainingMoves(int newRemainingMovements){ ((Level3State) state()).informBombRemainingMoves(newRemainingMovements); }

	public void bombDeactivated(){ ((Level3State) state()).bombDeactivated(); }

	public void bombExploded(){ ((Level3State) state()).bombExploded(); }


	// -------------------------------------------------------- GAME STATE --------------------------------------------------------


	static private class Level3State extends GameState {

		private int remainingMovements = BOMB_INITIAL_MOVES;
		private long bombsDeactivated=0;
		private boolean bombExploded=false;


		public boolean gameOver() { return playerWon() || bombExploded; }
		
		public boolean playerWon() { return bombsDeactivated == NUMBER_OF_BOMBS; }

		@Override
		public String toString() {
			return String.format("%s \nRemaining movements: %d", super.toString(), remainingMovements);
		}

		private void informBombRemainingMoves(int newRemainingMovements){
			remainingMovements = newRemainingMovements<remainingMovements ? newRemainingMovements : remainingMovements;
		}

		private void bombDeactivated(){ bombsDeactivated += 1; }

		private void bombExploded(){ bombExploded=true; }

	}

}
