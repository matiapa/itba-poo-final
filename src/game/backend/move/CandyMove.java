package game.backend.move;

import game.backend.Figure;
import game.backend.FigureDetector;
import game.backend.Grid;

public class CandyMove extends Move {

	private Figure f1;
	private Figure f2;
	
	private FigureDetector detector;
	private Grid grid;
	
	public CandyMove(Grid grid) {
		super(grid);
		this.grid = grid;
	}

	// Checks if a figure appears on any of the points
	@Override
	public boolean internalValidation() {
		System.out.println(String.format("CandyMove %d,%d - %d,%d",i1,j1,i2,j2));
		this.detector = new FigureDetector(grid);
		f1 = detector.checkFigure(i1, j1);
		f2 = detector.checkFigure(i2, j2);
		return f1 != null || f2 != null;
	}	

	@Override
	public void removeElements() {
		if (f1 != null) {
			detector.removeFigure(i1, j1, f1);
		}
		if (f2 != null) {
			detector.removeFigure(i2, j2, f2);
		}
	}

}