package game.backend.element;

import game.backend.Grid;
import game.backend.move.Direction;

public class HorizontalStripedCandy extends Candy {
	
	private Direction[] explosion = new Direction[2];
	
	public HorizontalStripedCandy() {
		explosion[0] = Direction.LEFT;
		explosion[1] = Direction.RIGHT;
	}

	public HorizontalStripedCandy(Grid grid, CandyColor color){
		super(color);
		explosion[0] = Direction.LEFT;
		explosion[1] = Direction.RIGHT;
		this.grid = grid;
	}
	
	@Override
	public String getKey() {
		return "HORIZ-STRIPED-" + super.getKey();
	}
	
	@Override
	public String getFullKey() {
		return "HORIZ-STRIPED-" + super.getFullKey();
	}
	
	@Override
	public Direction[] explode() {
		return explosion;
	}
	
	@Override
	public long getScore() {
		return 80;
	}

}
