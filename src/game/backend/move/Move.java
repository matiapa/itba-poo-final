package game.backend.move;

import game.backend.Grid;
import game.backend.element.Element;
import game.backend.level.Level5;

public abstract class Move {
	
	private Grid grid;
	protected int i1, j1, i2, j2;
	private SpecialAction specialAction;
	
	public Move(Grid grid, SpecialAction action) {
		this.grid = grid;
		this.specialAction = action;
	}
	
	public void setCoords(int i1, int j1, int i2, int j2){
		this.i1 = i1;
		this.j1 = j1;
		this.i2 = i2;
		this.j2 = j2;
	}
	
	public boolean isValid() {
		// Checks if candies are adjacent
		if ( (i1 == i2 && Math.abs(j1-j2) == 1) || (j1 == j2 && Math.abs(i1-i2) == 1)) {
			return internalValidation();
		}
		return false;
	}
	
	protected boolean internalValidation() {
		return true;
	}
	
	protected Element get(int i, int j) {
		return grid.get(i, j);
	}
	
	protected void clearContent(int i, int j) {
		grid.clearContent(i, j);
	}
	
	protected void setContent(int i, int j, Element e){
		grid.setContent(i, j, e);
	}
	
	protected void wasUpdated(){
		grid.gridUpdated();
	}
	
	abstract public void removeElements();

	// Although this is only used in Level5, it wouldn't have been convenient to create new classes for Move and its descendants only for Level5
	public void executeSpecialAction(){
		if(specialAction==null || !(grid instanceof Level5)) return;
		switch(specialAction){
			case WALL_HOR_REMOVE:
				for (int j = 0; j < Grid.getSize(); j++) {
					((Level5) grid).removeCellWall(i2, j);
				}
				grid.gridUpdated();
				break;
			case WALL_VERT_REMOVE:
				for(int i = 0; i < Grid.getSize(); i++) {
					((Level5) grid).removeCellWall(i, j2);
				}
				grid.gridUpdated();
				break;
		}
	}

	public enum SpecialAction{ WALL_HOR_REMOVE, WALL_VERT_REMOVE }
}
