package game.backend.move;

import game.backend.Grid;

public class TwoStripedMove extends Move {

	public TwoStripedMove(Grid grid, SpecialAction action) {
		super(grid, action);
	}
	
	@Override
	public void removeElements() {
		for(int i = 0; i < Grid.getSize(); i++) {
			clearContent(i, j2);
		}
		for(int j = 0; j < Grid.getSize(); j++) {
			clearContent(i2, j);
		}
	}

}
