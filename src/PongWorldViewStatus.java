import javax.swing.*;
import java.awt.*;

public class PongWorldViewStatus extends JLabel implements IView {

    private PongWorld pongWorld;

    public PongWorldViewStatus(PongWorld pongWorld) {
        this.pongWorld = pongWorld;
        setText("StatusBar");
    }

    //Draw of status bar, with players score and selected game mode
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension(1000,25));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 17));
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        if(pongWorld.getGameStatus() == 1) {
            setText(" ");
        }
        else {
            setText("Player One: " + pongWorld.getPlayerOneScore() + ", Player Two: " + pongWorld.getPlayerTwoScore() + ", GameMode: " + pongWorld.getGameMode());
        }

    }

    @Override
    public void update() {
        repaint();
    }
}
