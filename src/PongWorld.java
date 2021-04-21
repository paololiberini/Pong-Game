import java.awt.geom.Point2D;

public class PongWorld {

    //Constant about world status and game mode
    public static final int STARTED_MENU = 1;
    public static final int START_READY = 2;
    public static final int RUNNING = 3;
    public static final int PAUSE = 4;
    public static final int GAME_OVER = 5;
    public static final int MANUAL = 6;

    public static final int SINGLE_PLAYER = 1;
    public static final int MULTI_PLAYER = 2;

    //Elements of the world
    private Ball ball;
    private AbstractBar barOne;
    private AbstractBar barTwo;

    //Variables about game mode and game status
    private int gameMode;
    private int gameStatus;

    //Player score
    private int playerOneScore;
    private int playerTwoScore;

    private PongWorldView pongWorldView;
    private PongWorldViewStatus pongWorldViewStatus;

    public PongWorld(PongWorldView pongWorldView) {
        this.pongWorldView = pongWorldView;

        gameStatus = STARTED_MENU;
        playerOneScore = 0;
        playerTwoScore = 0;

        ball = new Ball();
        barOne = new HumanBar();
        barTwo = new IABar();
    }

    public void addPongWorldViewStatus(PongWorldViewStatus pongWorldViewStatus) {
        this.pongWorldViewStatus = pongWorldViewStatus;
    }

    //Update of every component of the world and check of collision
    public void update() {
        ball.movement();
        collision();
        pongWorldView.update();
        pongWorldViewStatus.update();
    }

    //Check and management of collisions of the ball in the world (7 given by dimension of ball / 2)
    public void collision() {
        if(gameStatus == RUNNING) {
            //Collision with up border
            if (ball.getPosition().getY() <= 0 + 7  || ball.getPosition().getY() >= 600 + 7 ) {
                ball.changeDirY();
            }

            //Collision with left bar
            if ((ball.getPosition().getY() > barOne.getPosition().getY() && ball.getPosition().getY() < barOne.getPosition().getY() + 100) && ball.getPosition().getX() + ball.getSpeedX() / 2 <= 60 + 7 && ball.getPosition().getX() >= 40) {
                ball.changeDirX();
            }

            //Collision with right bar
            if ((ball.getPosition().getY() > barTwo.getPosition().getY() && ball.getPosition().getY() < barTwo.getPosition().getY() + 100) && ball.getPosition().getX() + ball.getSpeedX() / 2 >= 940 - 7 && ball.getPosition().getX() <= 960) {
                ball.changeDirX();
            }

            //Collision with left border
            if (ball.getPosition().getX() <= 10 && ball.isActive()) {
                ball.stopMovement();
                gameStatus = GAME_OVER;
                playerTwoScore++;
                barOne.setActive(false);
                barTwo.setActive(false);
            }

            //Collision with right border
            if (ball.getPosition().getX() >= 990 && ball.isActive()) {
                ball.stopMovement();
                gameStatus = GAME_OVER;
                playerOneScore++;
                barOne.setActive(false);
                barTwo.setActive(false);
            }
        }
    }

    //Start method
    public void startGame() {
        ball.startGame();
        gameStatus = RUNNING;
        barOne.setActive(true);
        barTwo.setActive(true);
    }

    //Load in start position of every element of the world
    public void readyToStart() {
        ball.readyToStart(new Point2D.Double(pongWorldView.getDimension().getWidth() / 2, pongWorldView.getDimension().getHeight() / 2));
        barOne.readyToStart(new Point2D.Double(50,pongWorldView.getDimension().getHeight() / 2 - 50));
        barTwo.readyToStart(new Point2D.Double(pongWorldView.getDimension().getWidth() - 60,pongWorldView.getDimension().getHeight() / 2 -50));
        gameStatus = START_READY;
    }

    //If not in pause, set the game in pause. If in pause, remove the pause
    public void pauseGame() {
        if(gameStatus != PAUSE) {
            ball.setActive(false);
            barOne.setActive(false);
            barTwo.setActive(false);
            gameStatus = PAUSE;
        }
        else {
            ball.setActive(true);
            barOne.setActive(true);
            barTwo.setActive(true);
            gameStatus = RUNNING;
        }
    }

    public Ball getBall() {
        return ball;
    }

    public AbstractBar getBarOne() {
        return barOne;
    }

    public AbstractBar getBarTwo() {
        return barTwo;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    //Set of game mode. Changing game mode, change the type of bar of the second player (IA or Human)
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
        if(gameMode == STARTED_MENU) {
            barTwo = new IABar();
        }
        if(gameMode == START_READY) {
            barTwo = new HumanBar();
        }
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }
}
