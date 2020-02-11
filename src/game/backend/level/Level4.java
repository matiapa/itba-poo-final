package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.TimeProviderCandyGeneratorCell;

public class Level4 extends Grid {

    private static int INITIAL_TIME = 60;

    private static int NUMBER_OF_TIME_PROVIERS = 15;
    public static int ADDITIONAL_TIME = 10;
    private static double BOMB_FREQUENCY = 0.1;

    public Level4() {
        super(new Level4State());
    }

    @Override
    protected void fillCells() {
        genCell = new TimeProviderCandyGeneratorCell(this, NUMBER_OF_TIME_PROVIERS, BOMB_FREQUENCY);
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

    static private class Level4State extends GameState {

        private int timeProviderConsumed = 0;

        private boolean timesUp = false;
        private int timer = INITIAL_TIME;
        /*private Timer timer = new Timer();//TODO


        private TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new TimerTask() {
                    @Override
                    public void run() {

                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
        */

        public boolean gameOver() {
            if (timer == 0)//TODO
                timesUp();

            return playerWon() || timesUp;
        }

        public boolean playerWon() {//TODO y timer es distinto de cero

            return timeProviderConsumed == NUMBER_OF_TIME_PROVIERS && timer != 0;
        }

        private void timesUp() {
            timesUp = true;
        }

        private void timeProviderConsumed(){
            timeProviderConsumed++;
            timer += ADDITIONAL_TIME;
        } //TODO timer

        @Override
        public String toString() {
            return String.format("%s \nRemaining time: %d", super.toString(), timer);
        }
    }
}