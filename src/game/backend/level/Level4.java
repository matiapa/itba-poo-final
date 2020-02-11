package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.TimeProviderCandyGeneratorCell;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

public class Level4 extends Grid {

    private static int INITIAL_TIME = 60;
<<<<<<< HEAD

    private static int NUMBER_OF_TIME_PROVIERS = 15;
    public static int ADDITIONAL_TIME = 10;
    private static double BOMB_FREQUENCY = 0.1;
=======
    private static int TIME_BONUS_AMOUNT = 20;
    public static int TIME_BONUS_ADDITIONAL_TIME = 20;
    private static double TIME_BONUS_FREQUENCY = 0.1;
>>>>>>> 95d98f30f39709a05f5c309557587ed066099d4a

    public Level4() {
        super();
        initialize(new Level4State());
    }

    @Override
    protected void fillCells() {
<<<<<<< HEAD
        genCell = new TimeProviderCandyGeneratorCell(this, NUMBER_OF_TIME_PROVIERS, BOMB_FREQUENCY);
=======
        genCell = new TimeProviderCandyGeneratorCell(this, TIME_BONUS_AMOUNT, TIME_BONUS_FREQUENCY);
>>>>>>> 95d98f30f39709a05f5c309557587ed066099d4a
        super.fillCells();
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            state().addMove();
            wasUpdated();
        }
        return ret;
    }

    public static int getAdditionalTime() { return ADDITIONAL_TIME; }

    public void timeProviderConsumed() {
        ((Level4State) state()).timeProviderConsumed();
    }

// -------------------------------------------------------- GAME STATE --------------------------------------------------------

    private class Level4State extends GameState {

        private int timeProviderConsumed = 0;
        private boolean timesUp = false;
<<<<<<< HEAD
        private int timer = INITIAL_TIME;
        /*private Timer timer = new Timer();//TODO


        private TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new TimerTask() {
                    @Override
                    public void run() {
=======
        private int timeCount = Level4.INITIAL_TIME;


        Level4State(){
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new TimerTask() {
                        @Override
                        public void run() {
                            timeCount -= 1;
                            if (timeCount == 0)//TODO
                                timesUp();
                            scorePanelUpdated();
                        }
                    });
                }
            };
            (new Timer()).scheduleAtFixedRate(timerTask, 0, 1000);
        }
>>>>>>> 95d98f30f39709a05f5c309557587ed066099d4a


        public boolean gameOver() {
            return playerWon() || timesUp;
        }

<<<<<<< HEAD
        public boolean playerWon() {//TODO y timer es distinto de cero

            return timeProviderConsumed == NUMBER_OF_TIME_PROVIERS && timer != 0;
=======
        public boolean playerWon() {
            return timeProviderConsumed == TIME_BONUS_AMOUNT && timeCount != 0;
>>>>>>> 95d98f30f39709a05f5c309557587ed066099d4a
        }

        private void timesUp() {
            timesUp = true;
        }

        private void timeProviderConsumed(){
<<<<<<< HEAD
            timeProviderConsumed++;
            timer += ADDITIONAL_TIME;
        } //TODO timer
=======
            System.out.println("Consumed");
            //timeProviderConsumed++;
            //timeCount += TIME_BONUS_ADDITIONAL_TIME;
        }
>>>>>>> 95d98f30f39709a05f5c309557587ed066099d4a

        @Override
        public String toString() {
            return String.format("%s \nRemaining time: %d", super.toString(), timeCount);
        }
    }
}