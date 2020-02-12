package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.TimeBonusCandyGeneratorCell;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

public class Level4 extends Grid {

    private static int INITIAL_TIME = 60;
    private static int TIME_BONUS = 10;
    private static int NUMBER_OF_TIME_BONUS = 20;
    private static double TIME_BONUS_FREQUENCY = 0.1;

    public Level4() {
        super();
        initialize(new Level4State());
    }

    @Override
    protected void fillCells() {
        setGenCell(new TimeBonusCandyGeneratorCell(this, NUMBER_OF_TIME_BONUS, TIME_BONUS_FREQUENCY));
        super.fillCells();
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            getState().addMove();
            gridUpdated();
        }
        return ret;
    }

    public static int getAdditionalTime() { return TIME_BONUS; }

    public void timeBonusConsumed() {
        ((Level4State) getState()).timeProviderConsumed();
    }

    static public String levelInfo() {
        return TIME_BONUS+" time bonuses will be placed in the game, you have to consume them all through valid moves. If you run out of time, you loose";
    }


// -------------------------------------------------------- GAME STATE --------------------------------------------------------

    private class Level4State extends GameState {

        private int timeBonusConsumed = 0;
        private boolean timesUp = false;
        private int timeCount = Level4.INITIAL_TIME;


        Level4State(){
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new TimerTask() {
                        @Override
                        public void run() {
                            timeCount -= 1;
                            if (timeCount == 0)
                                timesUp();
                            scorePanelUpdated();
                        }
                    });
                }
            };
            (new Timer()).scheduleAtFixedRate(timerTask, 0, 1000);
        }


        public boolean gameOver() {
            return playerWon() || timesUp;
        }

        public boolean playerWon() {
            return timeBonusConsumed == NUMBER_OF_TIME_BONUS && timeCount != 0;
        }

        @Override
        public String toString() {
            return String.format("%s \nRemaining time: %d", super.toString(), timeCount);
        }


        private void timesUp() {
            timesUp = true;
        }

        private void timeProviderConsumed(){
            timeBonusConsumed++;
            timeCount += TIME_BONUS;
        }


    }

}