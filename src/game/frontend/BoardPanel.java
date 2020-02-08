package game.frontend;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BoardPanel extends TilePane {

	private StackPane[][] cells;


	public BoardPanel(final int rows, final int columns, final int cellSize) {

		// Set up the TilePane (grid)
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);

		// Fill it with empty ImageViews
		this.cells = new StackPane[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new StackPane();
				getChildren().add(cells[i][j]);
			}
		}
	}


	// Updates the image of the ImageView
	public void setImage(int row, int column, Image image, CellEffect cellEffect, String overlayText) {

		// Create the image
		ImageView imageView = new ImageView(image);

		// Set effect if necessary
		if(cellEffect!=null){
			cells[row][column].setEffect(cellEffect.effect);
		}

		// Add it to the pane
		cells[row][column].getChildren().add(imageView);


		// Check for overlay text
		if(overlayText!=null){

			// Shadow
			DropShadow dropShadow = new DropShadow();
			dropShadow.setRadius(3.0);
			dropShadow.setOffsetX(3.0);
			dropShadow.setOffsetY(3.0);
			dropShadow.setColor(Color.ORANGERED);

			// Text
			Text text = new Text(overlayText);
			text.setFont(Font.font("Impact", FontWeight.BOLD, 40));
			text.setFill(Color.BLACK);
			text.setEffect(dropShadow);

			// Add it to the pane
			cells[row][column].getChildren().add(text);

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