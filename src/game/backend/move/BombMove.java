package game.backend.move;

import game.backend.Grid;
import game.backend.element.Bomb;
import game.backend.element.Candy;

public class BombMove extends Move {
	
	public BombMove(Grid grid, SpecialAction action) {
		super(grid, action);
	}

	@Override
	public void removeElements() {
		Candy candy = (Candy) (get(i1, j1) instanceof Bomb ? get(i2, j2) : get(i1, j1));
		clearContent(i1, j1);
		clearContent(i2, j2);
		for(int i = 0; i < Grid.getSize(); i++) {
			for(int j = 0; j < Grid.getSize(); j++) {
				if (candy.equals(get(i, j))) {
					clearContent(i, j);
				}
			}
		}
	}

}
