import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        //Load of game world, views and controller
        PongWorldView pongWorldView = new PongWorldView();
        PongWorld pongWorld = new PongWorld(pongWorldView);
        PongWorldViewStatus pongWorldViewStatus = new PongWorldViewStatus(pongWorld);
        Controller controller = new Controller(pongWorld);

        pongWorld.addPongWorldViewStatus(pongWorldViewStatus);
        pongWorldView.addPongWorld(pongWorld);

        //Creation of the frame
        JFrame frame = new JFrame("Pong: The Game");

        //Setup of the frame, adding the views and listeners
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pongWorldView);
        frame.addKeyListener(controller.keyListener);
        frame.add(pongWorldViewStatus, BorderLayout.NORTH);
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);

        controller.start();
    }
}
