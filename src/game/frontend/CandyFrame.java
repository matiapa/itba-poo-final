package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.BombCandy;
import game.backend.element.Element;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CandyFrame extends VBox {

	private static final int CELL_SIZE = 65;

	private BoardPanel boardPanel;
	private ScorePanel scorePanel;
	private ImageManager images;
	private Point2D lastPoint;
	private CandyGame game;

	public CandyFrame(CandyGame game) {

		this.game = game;

		// Build app menu
		getChildren().add(new AppMenu());

		// Build board panel
		images = new ImageManager();
		boardPanel = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
		getChildren().add(boardPanel);

		// Build score panel
		scorePanel = new ScorePanel();
		getChildren().add(scorePanel);

		// Start the game
		game.initGame();

		// Add a GameListener that re-renders the grid on gridUpdated
		GameListener listener;
		game.addGameListener(listener = new GameListener() {

			// Re-renders the grid
			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(100);
				Duration frameTime = Duration.ZERO;
				for (int i = game().getSize() - 1; i >= 0; i--) {
					for (int j = game().getSize() - 1; j >= 0; j--) {

						// Get the new image of the cell
						Cell cell = CandyFrame.this.game.get(i, j);
						Element element = cell.getContent();

						Image image = images.getImage(element);
						BoardPanel.CellEffect effect = cell.getEffect();
						String overlayText = (cell.getContent() instanceof BombCandy) ? ""+((BombCandy) cell.getContent()).getRemainingMoves() : null;

						// Adds to timeline a call to boardPanel.setImage with new image
						int finalI = i, finalJ = j;
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null, null, null)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image, effect, overlayText)));

					}
					frameTime = frameTime.add(frameGap);
				}
				timeLine.play();
			}

			@Override
			public void cellExplosion(Element e) {
				//
			}

		});

		listener.gridUpdated();

		// Adds handler for clicks
		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (lastPoint == null) {
				lastPoint = translateCoords(event.getX(), event.getY());
			} else {
				Point2D newPoint = translateCoords(event.getX(), event.getY());
				if (newPoint != null) {

					// Try a move
					game().tryMove((int)lastPoint.getX(), (int)lastPoint.getY(), (int)newPoint.getX(), (int)newPoint.getY());

					// Update the ScorePanel
					scorePanel.updateScore(game().toString());

					// Reset last point
					lastPoint = null;
				}
			}
		});

	}

	private CandyGame game() {
		return game;
	}

	private Point2D translateCoords(double x, double y) {
		double i = x / CELL_SIZE;
		double j = y / CELL_SIZE;
		return (i >= 0 && i < game.getSize() && j >= 0 && j < game.getSize()) ? new Point2D(j, i) : null;
	}

}
