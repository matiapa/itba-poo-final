package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	
	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public abstract boolean gameOver();
	
	public abstract boolean playerWon();

	@Override
	public String toString(){

		String message = String.format("Score: %d", getScore());

		if(gameOver()){
			if (playerWon()) {
				message = message + "\nFinished - Player Won!";
			}else{
				message = message + "\nFinished - Loser !";
			}
		}

		return message;
	};

}
