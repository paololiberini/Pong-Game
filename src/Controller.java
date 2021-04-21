import javax.swing.*;
import java.awt.event.*;

public class Controller {

    private PongWorld pongWorld;

    //Variable used to save a previous status of the game
    private int previousPongWorldStatus;

    //Save the activation of W, S, UP, DOWN button
    private boolean wPressed, sPressed, upPressed, downPressed;

    private Timer timer;

    public ActionListener timerListener;
    public KeyListener keyListener;

    public Controller(PongWorld pongWorld) {
        this.pongWorld = pongWorld;

        timerListener = createTimerListener();
        keyListener = createKeyListener();

        this.timer = new Timer(1000/60, timerListener);
    }

    private ActionListener createTimerListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(wPressed && pongWorld.getGameStatus() == pongWorld.RUNNING) {
                    pongWorld.getBarOne().movementUp();
                }
                if(sPressed && pongWorld.getGameStatus() == pongWorld.RUNNING) {
                    pongWorld.getBarOne().movementDown();
                }
                //In single player mode, there is no button listener for the second bar but the position is calculated based on the position of the Y axis of the ball
                if(pongWorld.getGameMode() == pongWorld.SINGLE_PLAYER && pongWorld.getGameStatus() == pongWorld.RUNNING) {
                    //Se la pallina è sopra la barra, questa viene spostata in alto fino ad allinearsi (< perchè lo (0,0) è in alto sx e cresce scendendo)
                    if(pongWorld.getBall().getPosition().getY() < pongWorld.getBarTwo().getPosition().getY() + 50) {
                        pongWorld.getBarTwo().movementUp();
                    }
                }
                if(pongWorld.getGameMode() == pongWorld.SINGLE_PLAYER && pongWorld.getGameStatus() == pongWorld.RUNNING) {
                    //Se la pallina è sotto la barra, questa viene spostata in basso fino ad allinearsi
                    if (pongWorld.getBall().getPosition().getY() > pongWorld.getBarTwo().getPosition().getY() + 50) {
                        pongWorld.getBarTwo().movementDown();
                    }
                }
                //In dobule player mode, is used the button pression of UP and DOWN arrow button
                if(pongWorld.getGameMode() == pongWorld.MULTI_PLAYER && pongWorld.getGameStatus() == pongWorld.RUNNING) {
                    if (upPressed) {
                        pongWorld.getBarTwo().movementUp();
                    }
                }
                if(pongWorld.getGameMode() == pongWorld.MULTI_PLAYER && pongWorld.getGameStatus() == pongWorld.RUNNING) {
                        if (downPressed) {
                        pongWorld.getBarTwo().movementDown();
                    }
                }

                //Call of update method to update game world. Its call is based on the refresh rate
                pongWorld.update();
            }
        };
    }

    //Listener of keyboard button, whith pression and release listener
    private KeyListener createKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //Pression of W, S, UPArrow, DOWNArrow
                if(e.getExtendedKeyCode() == KeyEvent.VK_W) {
                    wPressed = true;
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_S) {
                    sPressed = true;
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_UP) {
                    upPressed = true;
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
                    downPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //Release of W, S, UPArrow, DOWNArrow
                if(e.getExtendedKeyCode() == KeyEvent.VK_W) {
                    wPressed = false;
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_S) {
                    sPressed = false;
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_UP) {
                    upPressed = false;
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
                    downPressed = false;
                }

                //In GameOver or Pause status, load a new match pressing SPACE
                if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE && (pongWorld.getGameStatus() == pongWorld.GAME_OVER || pongWorld.getGameStatus() == pongWorld.PAUSE)) {
                    pongWorld.readyToStart();
                }
                //In ReadyToStart status, start a new match pressing ENTER
                if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER && pongWorld.getGameStatus() == pongWorld.START_READY) {
                    pongWorld.startGame();
                }
                //In StartMenu or GameOver status, set single player mode pressing 1 button
                if(e.getExtendedKeyCode() == KeyEvent.VK_1 && (pongWorld.getGameStatus() == pongWorld.STARTED_MENU || pongWorld.getGameStatus() == PongWorld.GAME_OVER)) {
                    pongWorld.setGameMode(1);
                    pongWorld.readyToStart();
                }
                //In StartMenu or GameOver status, set double player mode pressing 2 button
                if(e.getExtendedKeyCode() == KeyEvent.VK_2 && (pongWorld.getGameStatus() == PongWorld.STARTED_MENU || pongWorld.getGameStatus() == PongWorld.GAME_OVER)) {
                    pongWorld.setGameMode(2);
                    pongWorld.readyToStart();
                }
                //In Execution or Pause status, set o remove the Pause mode pressing ESC button
                if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE && (pongWorld.getGameStatus() == PongWorld.RUNNING || pongWorld.getGameStatus() == PongWorld.PAUSE)) {
                    pongWorld.pauseGame();
                }
                //In StartMenu, Pause, GameOver or in Manual status, open and close the Manual pressing M button
                if(e.getExtendedKeyCode() == KeyEvent.VK_M && (pongWorld.getGameStatus() == PongWorld.STARTED_MENU || pongWorld.getGameStatus() == PongWorld.PAUSE || pongWorld.getGameStatus() == PongWorld.GAME_OVER || pongWorld.getGameStatus() == PongWorld.MANUAL)) {
                    //Se non si è nello stato 6 (manuale), viene salvato lo stato precedente e si passa allo stato 6. Altrimenti viene reimpostato lo stato precedente
                    if(pongWorld.getGameStatus() != 6) {
                        previousPongWorldStatus = pongWorld.getGameStatus();
                        pongWorld.setGameStatus(6);
                    }
                    else {
                        pongWorld.setGameStatus(previousPongWorldStatus);
                    }
                }
            }
        };
    }

    public void start() {
        timer.start();
    }
}
