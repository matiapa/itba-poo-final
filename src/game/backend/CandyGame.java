package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;

public class CandyGame implements GameListener {
	
	private Grid grid;

	public CandyGame(Class<Grid> clazz) {
		// Create the grid
		try {
			grid = clazz.newInstance();
		} catch(IllegalAccessException | InstantiationException e) {
			System.out.println("ERROR AL INICIAR");
		}

		// Add a GameListener that adds score on explosions
		addGameListener(this);
	}


	// Grid accessors

	public int getSize() {
		return Grid.getSize();
	}
	
	public boolean tryMove(int i1, int j1, int i2, int j2){
		return grid.tryMove(i1, j1, i2, j2);
	}
	
	public Cell get(int i, int j){
		return grid.getCell(i, j);
	}
	
	public void addGameListener(GameListener listener) {
		grid.addListener(listener);
	}


	// State accessors

	@Override
	public String toString(){ return grid.getState().toString(); }

	public boolean gameOver(){ return grid.getState().gameOver(); }

	public boolean playerWon(){ return grid.getState().playerWon(); }


	// GameListener Overrides
	
	@Override
	public void cellExplosion(Element e) {
		grid.getState().addScore(e.getScore());
	}
	
	@Override public void gridUpdated(){}

	@Override public void onValidMove(){}

	@Override public void scorePanelUpdated() { }
}
