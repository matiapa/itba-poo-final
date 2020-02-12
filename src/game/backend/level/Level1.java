package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Wall;

public class Level1 extends Grid {
	
	private static int REQUIRED_SCORE = 5000; 
	private static int MAX_MOVES = 20;

	public Level1(){
		super();
		initialize(new Level1State());
	}


	@Override
	protected void fillCells() {
		setGenCell(new CandyGeneratorCell(this));
		super.fillCells();
	}
	
	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			getState().addMove();
		}
		return ret;
	}

	static public String levelInfo() {
		return "Original game. You should achieve a maximum score in "+MAX_MOVES+" moves";
	}

	// -------------------------------------------------------- GAME STATE --------------------------------------------------------

	private class Level1State extends GameState {

		private long requiredScore = Level1.REQUIRED_SCORE;
		private long maxMoves = Level1.MAX_MOVES;

		public boolean gameOver() {
			return playerWon() || getMoves() >= maxMoves;
		}
		
		public boolean playerWon() {
			return getScore() > requiredScore;
		}

		@Override
		public String toString() {
			return String.format("%s \nRemaining movements: %d", super.toString(), maxMoves-getMoves());
		}
	}

}
