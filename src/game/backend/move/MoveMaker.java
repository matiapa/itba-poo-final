package game.backend.move;

import game.backend.Grid;
import game.backend.element.Bomb;
import game.backend.element.Candy;
import game.backend.element.HorizontalStripedCandy;
import game.backend.element.VerticalStripedCandy;
import game.backend.element.WrappedCandy;

import java.util.HashMap;
import java.util.Map;

public class MoveMaker {

	private Map<String, Move> map;
	private Grid grid;
	
	public MoveMaker(Grid grid) {
		this.grid = grid;
		initMap();
	}


	/* Creates a map of all combinations and their corresponding movements:
		Candy 	+ Candy = CandyMove
				+ HorizontalStriped = CandyMove
				+ VerticalStriped = CandyMove
				+ Wrapped = CandyMove
				+ Bomb = BombMove

		HorizontalStriped	+ HorizontalStriped = TwoStripedMove
							+ VerticalStriped = TwoStripedMove
							+ WrappedCandy = WrappedStripedMove
							+ BombMove = BombMove

		VerticalStriped		Equal to HorizontalStriped

		Wrapped		+ Wrapped = TwoWrappedMove
					+ Bomb = BombWrappedMove

		Bomb		+ Bomb = TwoBombMove

	 */

	private void initMap(){
		map = new HashMap<>();
		map.put(new Candy().getKey() + new Candy().getKey(), new CandyMove(grid, null));
		map.put(new Candy().getKey() + new HorizontalStripedCandy().getKey(), new CandyMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));
		map.put(new Candy().getKey() + new VerticalStripedCandy().getKey(), new CandyMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));
		map.put(new Candy().getKey() + new WrappedCandy().getKey(), new CandyMove(grid, null));
		map.put(new Candy().getKey() + new Bomb().getKey(), new BombMove(grid, null));
	
		map.put(new HorizontalStripedCandy().getKey() + new Candy().getKey(), new CandyMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));
		map.put(new HorizontalStripedCandy().getKey() + new HorizontalStripedCandy().getKey(), new TwoStripedMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));
		map.put(new HorizontalStripedCandy().getKey() + new VerticalStripedCandy().getKey(), new TwoStripedMove(grid, null));
		map.put(new HorizontalStripedCandy().getKey() + new WrappedCandy().getKey(), new WrappedStripedMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));
		map.put(new HorizontalStripedCandy().getKey() + new Bomb().getKey(), new BombStrippedMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));

		map.put(new VerticalStripedCandy().getKey() + new Candy().getKey(), new CandyMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));
		map.put(new VerticalStripedCandy().getKey() + new HorizontalStripedCandy().getKey(), new TwoStripedMove(grid, null));
		map.put(new VerticalStripedCandy().getKey() + new VerticalStripedCandy().getKey(), new TwoStripedMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));
		map.put(new VerticalStripedCandy().getKey() + new WrappedCandy().getKey(), new WrappedStripedMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));
		map.put(new VerticalStripedCandy().getKey() + new Bomb().getKey(), new BombStrippedMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));

		map.put(new WrappedCandy().getKey() + new Candy().getKey(), new CandyMove(grid, null));
		map.put(new WrappedCandy().getKey() + new HorizontalStripedCandy().getKey(), new WrappedStripedMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));
		map.put(new WrappedCandy().getKey() + new VerticalStripedCandy().getKey(), new WrappedStripedMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));
		map.put(new WrappedCandy().getKey() + new WrappedCandy().getKey(), new TwoWrappedMove(grid, null));
		map.put(new WrappedCandy().getKey() + new Bomb().getKey(), new BombWrappedMove(grid, null));

		map.put(new Bomb().getKey() + new Candy().getKey(), new BombMove(grid, null));
		map.put(new Bomb().getKey() + new HorizontalStripedCandy().getKey(), new BombStrippedMove(grid, Move.SpecialAction.WALL_HOR_REMOVE));
		map.put(new Bomb().getKey() + new VerticalStripedCandy().getKey(), new BombStrippedMove(grid, Move.SpecialAction.WALL_VERT_REMOVE));
		map.put(new Bomb().getKey() + new WrappedCandy().getKey(), new BombWrappedMove(grid, null));
		map.put(new Bomb().getKey() + new Bomb().getKey(), new TwoBombMove(grid, null));

	}

	// Returns the Move for a specific pair of coords
	public Move getMove(int i1, int j1, int i2, int j2) {
		System.out.println(grid.get(i1, j1).getKey());
		System.out.println(grid.get(i2, j2).getKey());
		Move move = map.get(grid.get(i1, j1).getKey() + grid.get(i2, j2).getKey());
		move.setCoords(i1, j1, i2, j2);
		return move;
	}

}
