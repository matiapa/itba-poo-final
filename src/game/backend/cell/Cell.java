package game.backend.cell;

import game.backend.Grid;
import game.backend.element.Element;
import game.backend.element.Nothing;
import game.backend.move.Direction;

public class Cell {
	
	protected Grid grid;
	private Cell[] around = new Cell[Direction.values().length];
	private Element content;
	private CellEffect effect;

	public Cell(Grid grid) {
		this.grid = grid;
		this.content = new Nothing();
	}


	// -------------------------------------------------------- AROUND --------------------------------------------------------


	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	private boolean hasFloor() {
		return !around[Direction.DOWN.ordinal()].isEmpty();
	}


	// -------------------------------------------------------- EXPLOSION --------------------------------------------------------


	private void expandExplosion(Direction[] explosion) {
		for(Direction d: explosion) {
			this.around[d.ordinal()].explode(d);
		}
	}
	
	private void explode(Direction d) {
		clearContent();
		if (this.around[d.ordinal()] != null)
			this.around[d.ordinal()].explode(d);
	}


	// -------------------------------------------------------- CONTENT --------------------------------------------------------


	public void setContent(Element content) {
		this.content = content;
	}

	public Element getContent() {
		return content;
	}

	public void clearContent() {
		if (content.isMovable()) {
			Direction[] explosionCascade = content.explode();
			grid.cellExploded(content);
			this.content = new Nothing();
			if (explosionCascade != null) {
				expandExplosion(explosionCascade);
			}
			this.content = new Nothing();
		}
	}

	public boolean fallUpperContent() {
		Cell up = around[Direction.UP.ordinal()];
		if (this.isEmpty() && !up.isEmpty() && up.isMovable()) {
			this.content = up.getAndClearContent();
			grid.gridUpdated();
			if (this.hasFloor()) {
				grid.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent();
			}
		} 
		return false;
	}

	public boolean isEmpty() {
		return !content.isSolid();
	}

	protected boolean isMovable(){
		return content.isMovable();
	}

	protected Element getAndClearContent() {
		if (content.isMovable()) {
			Element ret = content;
			this.content = new Nothing();
			return ret;
		}
		return null;
	}


	// -------------------------------------------------------- EFFECT --------------------------------------------------------

	public CellEffect getEffect(){ return effect; }

	public void setEffect(CellEffect effect){ this.effect=effect; }

	public enum CellEffect{ GOLDEN, SANDYBROWN }


	// -------------------------------------------------------- GRID --------------------------------------------------------

}
