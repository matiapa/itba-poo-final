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
	
	public static final int SIZE = 9;

	private Cell[][] g = new Cell[SIZE][SIZE];
	protected Cell[][] g() { return g; }

	private Map<Cell, Point> gMap = new HashMap<>();

	private GameState state;
	protected GameState state(){ return state; }

	private List<GameListener> listeners = new ArrayList<>();

	private MoveMaker moveMaker;
	private FigureDetector figureDetector;

	protected GeneratorCell genCell;

	// -------------------------------------------------------- CONSTRUCTION --------------------------------------------------------

	public Grid(){

		moveMaker = new MoveMaker(this);
		figureDetector = new FigureDetector(this);

		// Creates an array of Cells and a map of Cells->Points
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				g[i][j] = new Cell(this);
				gMap.put(g[i][j], new Point(i,j));
			}
		}

	}

	public void initialize(GameState state){
		this.state = state;
		fillCells();
		fallElements();
	}

	public Grid(GameState state){
		this();
		initialize(state);
	}


	protected void fillCells(){

		// Creates a Wall cell
		Cell wallCell = new Cell(this);
		wallCell.setContent(new Wall());

		// Fills the grid

		// Corners
		g()[0][0].setAround(genCell, g()[1][0], wallCell, g()[0][1]);
		g()[0][SIZE-1].setAround(genCell, g()[1][SIZE-1], g()[0][SIZE-2], wallCell);
		g()[SIZE-1][0].setAround(g()[SIZE-2][0], wallCell, wallCell, g()[SIZE-1][1]);
		g()[SIZE-1][SIZE-1].setAround(g()[SIZE-2][SIZE-1], wallCell, g()[SIZE-1][SIZE-2], wallCell);

		// Upper line
		for (int j = 1; j < SIZE-1; j++) {
			g()[0][j].setAround(genCell, g()[1][j], g()[0][j-1], g()[0][j+1]);
		}

		// Bottom line
		for (int j = 1; j < SIZE-1; j++) {
			g()[SIZE-1][j].setAround(g()[SIZE-2][j], wallCell, g()[SIZE-1][j-1],g()[SIZE-1][j+1]);
		}

		// Left line
		for (int i = 1; i < SIZE-1; i++) {
			g()[i][0].setAround(g()[i-1][0],g()[i+1][0], wallCell ,g()[i][1]);
		}

		// Right line
		for (int i = 1; i < SIZE-1; i++) {
			g()[i][SIZE-1].setAround(g()[i-1][SIZE-1],g()[i+1][SIZE-1], g()[i][SIZE-2], wallCell);
		}

		// Central cells
		for (int i = 1; i < SIZE-1; i++) {
			for (int j = 1; j < SIZE-1; j++) {
				g()[i][j].setAround(g()[i-1][j],g()[i+1][j],g()[i][j-1],g()[i][j+1]);
			}
		}
	};


	private void fallElements() {
		int i = SIZE - 1;
		while (i >= 0) {
			int j = 0;
			while (j < SIZE) {
				if (g[i][j].isEmpty()) {
					if (g[i][j].fallUpperContent()) {
						i = SIZE;
						j = -1;
						break;
					}
				}
				j++;
			}
			i--;
		}
	}


	// -------------------------------------------------------- Try movement  --------------------------------------------------------


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


	public void swapContent(int i1, int j1, int i2, int j2) {
		Element e = g[i1][j1].getContent();
		g[i1][j1].setContent(g[i2][j2].getContent());
		g[i2][j2].setContent(e);
		wasUpdated();
	}


	// -------------------------------------------------------- Removing --------------------------------------------------------


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


	// ------------------------------------------------------------------------------------------------------------------


	public Element get(int i, int j) {
		return g[i][j].getContent();
	}


	public Cell getCell(int i, int j) {
		return g[i][j];
	}

	
	public void clearContent(int i, int j) {
		g[i][j].clearContent();
	}


	public void setContent(int i, int j, Element e) {
		g[i][j].setContent(e);
	}


	public void addListener(GameListener listener) {
		listeners.add(listener);
	}


	public void wasUpdated(){
		if (listeners.size() > 0) {
			for (GameListener gl: listeners) {
				gl.gridUpdated();
			}
		}
	}
	
	public void cellExplosion(Element e) {
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

}
