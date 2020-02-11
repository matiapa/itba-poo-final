package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.frontend.BoardPanel;

public class Level2 extends Grid {

	
	private static int MAX_MOVES = 20;


	public Level2(){
		super(new Level1State(MAX_MOVES));
	}


	@Override
	protected void fillCells() {
		genCell = new CandyGeneratorCell(this);
		super.fillCells();
	}


	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			state().addMove();
			setGoldenCross(i2, j2);
		}
		return ret;
	}

	private void setGoldenCross(int r, int c){

		int newGoldenCells = 0;

		for(int i=0; i<SIZE; i++){
			if(g()[r][i].getEffect()==null){
				g()[r][i].setEffect(Cell.CellEffect.GOLDEN);
				newGoldenCells += 1;
			}
			if(g()[i][c].getEffect()==null){
				g()[i][c].setEffect(Cell.CellEffect.GOLDEN);
				newGoldenCells += 1;
			}
		}
		((Level1State) state()).addGoldenCells(newGoldenCells);
		wasUpdated();
	}


	// -------------------------------------------------------- GAME STATE --------------------------------------------------------

	static private class Level1State extends GameState {

		private long maxMoves;
		private long goldenCells=0;
		
		public Level1State(int maxMoves) {
			this.maxMoves = maxMoves;
		}

		@Override
		public boolean gameOver() {
			return playerWon() || getMoves() >= maxMoves;
		}

		@Override
		public boolean playerWon() {
			return goldenCells==SIZE*SIZE;
		}

		@Override
		public String toString() {
			return String.format("%s \nRemaining cells: %d", super.toString(), SIZE*SIZE-goldenCells);
		}

		private void addGoldenCells(int newGoldenCells){
			this.goldenCells += newGoldenCells;
		}

	}

}
