package game.backend;

import game.backend.cell.Cell;
import game.backend.cell.GeneratorCell;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.element.Wall;
import game.backend.move.Move;
import game.backend.move.MoveMaker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Grid {

	private static final int SIZE = 9;
	protected Cell[][] grid = new Cell[SIZE][SIZE];
	private Map<Cell, Point> gMap = new HashMap<>();

	private GameState state;
	private List<GameListener> listeners = new ArrayList<>();
	private MoveMaker moveMaker;
	private FigureDetector figureDetector;

	private GeneratorCell genCell;
	private boolean gridLoadReady;

	// -------------------------------------------------------- INITIALIZATION --------------------------------------------------------

	public Grid(){

		moveMaker = new MoveMaker(this);
		figureDetector = new FigureDetector(this);

		// Creates an array of Cells and a map of Cells->Points
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				grid[i][j] = new Cell(this);
				gMap.put(grid[i][j], new Point(i,j));
			}
		}

	}


	protected void initialize(GameState state){
		this.state = state;
		fillCells();
		fallElements();
		gridLoadReady = true;
	}


	protected void fillCells(){

		// Creates a Wall cell
		Cell wallCell = new Cell(this);
		wallCell.setContent(new Wall());

		// Fills the grid

		// Corners
		grid[0][0].setAround(genCell, grid[1][0], wallCell, grid[0][1]);
		grid[0][getSize()-1].setAround(genCell, grid[1][getSize()-1], grid[0][getSize()-2], wallCell);
		grid[getSize()-1][0].setAround(grid[getSize()-2][0], wallCell, wallCell, grid[getSize()-1][1]);
		grid[getSize()-1][getSize()-1].setAround(grid[getSize()-2][getSize()-1], wallCell, grid[getSize()-1][getSize()-2], wallCell);

		// Upper line
		for (int j = 1; j < getSize()-1; j++) {
			grid[0][j].setAround(genCell, grid[1][j], grid[0][j-1], grid[0][j+1]);
		}

		// Bottom line
		for (int j = 1; j < getSize()-1; j++) {
			grid[getSize()-1][j].setAround(grid[getSize()-2][j], wallCell, grid[getSize()-1][j-1], grid[getSize()-1][j+1]);
		}

		// Left line
		for (int i = 1; i < getSize()-1; i++) {
			grid[i][0].setAround(grid[i-1][0], grid[i+1][0], wallCell , grid[i][1]);
		}

		// Right line
		for (int i = 1; i < getSize()-1; i++) {
			grid[i][getSize()-1].setAround(grid[i-1][getSize()-1], grid[i+1][getSize()-1], grid[i][getSize()-2], wallCell);
		}

		// Central cells
		for (int i = 1; i < getSize()-1; i++) {
			for (int j = 1; j < getSize()-1; j++) {
				grid[i][j].setAround(grid[i-1][j], grid[i+1][j], grid[i][j-1], grid[i][j+1]);
			}
		}
	};


	private void fallElements() {
		int i = getSize() - 1;
		while (i >= 0) {
			int j = 0;
			while (j < getSize()) {
				if (grid[i][j].isEmpty()) {
					if (grid[i][j].fallUpperContent()) {
						i = getSize();
						j = -1;
						break;
					}
				}
				j++;
			}
			i--;
		}
	}


	// -------------------------------------------------------- MOVEMENT  --------------------------------------------------------


	// Checks if movement is valid, if so, removes elements of the move and makes the other elements fall
	public boolean tryMove(int i1, int j1, int i2, int j2) {

		Move move = moveMaker.getMove(i1, j1, i2, j2);

		swapContent(i1, j1, i2, j2);

		if (move.isValid()) {
			validMoveMade();
			move.removeElements();
			fallElements();
			return true;
		} else {
			swapContent(i1, j1, i2, j2);
			return false;
		}

	}


	private void swapContent(int i1, int j1, int i2, int j2) {
		Element e = grid[i1][j1].getContent();
		grid[i1][j1].setContent(grid[i2][j2].getContent());
		grid[i2][j2].setContent(e);
		gridUpdated();
	}


	public Figure tryRemove(Cell cell) {
		if (gMap.containsKey(cell)) {
			Point p = gMap.get(cell);
			Figure f = figureDetector.checkFigure(p.x, p.y);
			if (f != null) {
				removeFigure(p.x, p.y, f);
			}
			return f;
		}
		return null;
	}


	private void removeFigure(int i, int j, Figure f) {
		CandyColor color = ((Candy)get(i, j)).getColor();
		if (f.hasReplacement()) {
			setContent(i, j, f.generateReplacement(color));
		} else {
			clearContent(i, j);
		}
		for (Point p: f.getPoints()) {
			clearContent(i + p.x, j + p.y);
		}
	}


	// -------------------------------------------------- LISTENERS --------------------------------------------------------


	public void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public void gridUpdated(){
		if (listeners.size() > 0) {
			for (GameListener gl: listeners) {
				gl.gridUpdated();
			}
		}
	}
	
	public void cellExploded(Element e) {
		for (GameListener gl: listeners) {
			gl.cellExplosion(e);
		}
	}

	public void validMoveMade(){
		for (GameListener gl: listeners) {
			gl.onValidMove();
		}
	}

	protected void scorePanelUpdated(){
		if (listeners.size() > 0) {
			for (GameListener gl: listeners) {
				gl.scorePanelUpdated();
			}
		}
	}


	// ------------------------------------------------ CELL ACCESSORS --------------------------------------------------------


	public Cell getCell(int i, int j) {
		return grid[i][j];
	}


	public Element get(int i, int j) {
		return grid[i][j].getContent();
	}


	public void clearContent(int i, int j) {
		grid[i][j].clearContent();
	}


	public void setContent(int i, int j, Element e) {
		grid[i][j].setContent(e);
	}


	// ------------------------------------------------ PROPERTY ACCESSORS --------------------------------------------------------


	public static int getSize() { return SIZE; }
	public boolean getGridLoadReady(){ return gridLoadReady; }

	protected void setGenCell(GeneratorCell genCell) { this.genCell = genCell; }
	protected GameState getState(){ return state; }

}
