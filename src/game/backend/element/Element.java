package game.backend.element;

import game.backend.Grid;
import game.backend.move.Direction;

public abstract class Element{

	protected Grid grid;

	Element(){ }

	Element(Grid grid){
		this.grid = grid;
	}

	public abstract boolean isMovable();
	
	public abstract String getKey();
	
	public String getFullKey() {
		return getKey();
	}

	public boolean isSolid() {
		return true;
	}
	
	public Direction[] explode() {
		return null;
	}
	
	public long getScore() {
		return 0;
	}
	
}
