package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;

public class Level2 extends Grid {
	
	private static int MAX_MOVES = 20;


	public Level2(){
		super();
		initialize(new Level2State(MAX_MOVES));
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
			setGoldenCross(i2, j2);
		}
		return ret;
	}


	private void setGoldenCross(int r, int c){

		int newGoldenCells = 0;

		for(int i=0; i<getSize(); i++){
			if(getGrid()[r][i].getEffect()==null){
				getGrid()[r][i].setEffect(Cell.CellEffect.GOLDEN);
				newGoldenCells += 1;
			}
			if(getGrid()[i][c].getEffect()==null){
				getGrid()[i][c].setEffect(Cell.CellEffect.GOLDEN);
				newGoldenCells += 1;
			}
		}
		((Level2State) getState()).addGoldenCells(newGoldenCells);
		gridUpdated();
	}


	// -------------------------------------------------------- GAME STATE --------------------------------------------------------

	private class Level2State extends GameState {

		private long maxMoves;
		private long goldenCells=0;
		
		public Level2State(int maxMoves) {
			this.maxMoves = maxMoves;
		}

		@Override
		public boolean gameOver() {
			return playerWon() || getMoves() >= maxMoves;
		}

		@Override
		public boolean playerWon() {
			return goldenCells==getSize()*getSize();
		}

		@Override
		public String toString() {
			return String.format("%s \nRemaining cells: %d", super.toString(),getSize()*getSize()-goldenCells);
		}

		private void addGoldenCells(int newGoldenCells){
			this.goldenCells += newGoldenCells;
		}

	}

}
