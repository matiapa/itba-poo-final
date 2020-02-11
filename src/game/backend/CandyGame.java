package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;

public class CandyGame implements GameListener {
	
	private Class<?> levelClass;
	private Grid grid;
	private GameState state;
	
	public CandyGame(Class<?> clazz) {
		this.levelClass = clazz;
	}
	
	public void initGame() {

		// Create the grid
		try {
			grid = (Grid)levelClass.newInstance();
		} catch(IllegalAccessException | InstantiationException e) {
			System.out.println("ERROR AL INICIAR");
		}

		state = grid.state();

		// Add a GameListener that adds score on explosions
		addGameListener(this);
	}


	// Grid accessors

	public int getSize() {
		return Grid.SIZE;
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
	public String toString(){ return state.toString(); }

	public boolean gameOver(){ return state.gameOver(); }

	public boolean playerWon(){ return state.playerWon(); }


	// GameListener Overrides
	
	@Override
	public void cellExplosion(Element e) {
		state.addScore(e.getScore());
	}
	
	@Override public void gridUpdated(){}

	@Override public void onValidMove(){}

}
