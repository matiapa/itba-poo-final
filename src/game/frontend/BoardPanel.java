package game.frontend;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class BoardPanel extends TilePane {

	private ImageView[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {

		// Set up the TilePane (grid)
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);

		// Fill it with empty ImageViews
		this.cells = new ImageView[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new ImageView();
				getChildren().add(cells[i][j]);
			}
		}
	}

	// Updates the image of the ImageView
	public void setImage(int row, int column, Image image, CellEffect cellEffect) {
		cells[row][column].setImage(image);
		if(cellEffect!=null){
			cells[row][column].setEffect(cellEffect.effect);
		}
	}


	public enum CellEffect{

		GOLDEN(createGoldenEffect());

		static Effect createGoldenEffect(){
			Light.Distant spotLight = new Light.Distant();
			spotLight.setColor(Color.YELLOW);
			spotLight.setElevation(100);
			return new Lighting(spotLight);
		}

		private Effect effect;

		CellEffect(Effect effect){
			this.effect = effect;
		}

	}

}