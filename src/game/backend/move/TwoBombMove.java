package game.backend.move;

import game.backend.Grid;

public class TwoBombMove extends Move {
	
	public TwoBombMove(Grid grid) {
		super(grid);
	}
	
	@Override
	public void removeElements() {
		for(int i = 0; i < Grid.getSize(); i++) {
			for(int j = 0; j < Grid.getSize(); j++) {
				clearContent(i,j);
			}
		}
	}

}
