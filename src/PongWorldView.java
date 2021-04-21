import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;

public class PongWorldView extends JPanel implements IView {

    private PongWorld pongWorld;
    private Dimension dimension = new Dimension(1000, 625);

    public PongWorldView() {
        setPreferredSize(dimension);
        setBackground(Color.BLACK);
    }

    public void addPongWorld(PongWorld pongWorld) {
        this.pongWorld = pongWorld;
    }

    //Override of a Jcomponent method. Print the elements on the screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Antialiasing function
        ((Graphics2D)g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        if(pongWorld.getGameStatus() == PongWorld.STARTED_MENU) {
            drawStartString((Graphics2D)g);
        }

        if(pongWorld.getGameStatus() == 2 || pongWorld.getGameStatus() == PongWorld.RUNNING || pongWorld.getGameStatus() == PongWorld.PAUSE) {
            drawBall((Graphics2D) g, pongWorld.getBall().getPosition());
            drawBarOne((Graphics2D) g, pongWorld.getBarOne().getPosition());
            drawBarTwo((Graphics2D) g, pongWorld.getBarTwo().getPosition());
            if(pongWorld.getGameStatus() == PongWorld.PAUSE) {
                drawPauseString((Graphics2D)g);
            }
            if(pongWorld.getGameStatus() == PongWorld.START_READY) {
                drawGoString((Graphics2D)g);
            }
        }

        if(pongWorld.getGameStatus() == PongWorld.GAME_OVER) {
            drawGameOverString((Graphics2D)g);
        }

        if(pongWorld.getGameStatus() == PongWorld.MANUAL) {
            drawManual((Graphics2D)g);
        }

        //Continuous refresh
        Toolkit.getDefaultToolkit().sync();
    }

    //Component to write a new line every \n. (See documents for the source)
    private void drawString(Graphics g, String text, int x, int y) {
        int lineHeight = g.getFontMetrics().getHeight();
        int count = 0;
        for (String line : text.split("\n"))
            count++;
        for (String line : text.split("\n"))
            drawCenteredString(g, line, x, (y += lineHeight*2) - (lineHeight * count / 2) - 90);
    }

    //Component for center alignment of strings. (See documents for the source)
    private void drawCenteredString(Graphics g, String s, int w, int h) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    //Draw of initial menu
    private void drawStartString(Graphics2D g) {
        g.setColor(new Color(0x24A3FF));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        drawString(g, "Benvenuto in PONG!\n\n" +
                "Premi 1 per giocatore singolo\n" +
                "Premi 2 per multigiocatore\n" +
                "M per aprire il manuale", (int)dimension.getWidth(), (int)dimension.getHeight());
    }

    //Draw of GameOver message
    private void drawGameOverString(Graphics2D g) {
        g.setColor(new Color(0xDE101E));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        drawString(g,"GAME OVER!\n\n" +
                "Premi SPACE per ricaricare\n" +
                "Premi 1 per giocatore singolo\n" +
                "Premi 2 per multigiocatore\n" +
                "M per aprire il manuale", (int)dimension.getWidth(), (int)dimension.getHeight());
    }

    //Draw of Pause message
    private void drawPauseString(Graphics2D g) {
        g.setColor(new Color(0xFFCC11));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        drawString(g,"PAUSA\n\n" +
                "Premi ESC per riprendere\n" +
                "SPACE per azzerare la partita\n" +
                "M per aprire il manuale", (int)dimension.getWidth(), (int)dimension.getHeight());
    }

    //Draw of start message
    private void drawGoString(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        drawString(g,"Premi INVIO per iniziare", (int)dimension.getWidth(), (int)dimension.getHeight());
    }

    //Draw of manual
    private void drawManual(Graphics2D g) {
        g.setColor(new Color(0x18DE0E));
        g.setFont(new Font("Arial", Font.BOLD, 15));
        drawString(g, "In modalità giocatore singolo:\n" +
                "W muove in alto, S muove in basso\n\n" +
                "In modalità multigiocatore, per il secondo giocatore:\n" +
                "FECCIA-SU muove in alto, FRECCIA-GIU muove in basso\n\n" +
                "ESC mette in pausa la partita\n\n" +
                "In alto sono mostrati i punteggi e la modalità di gioco\n\n" +
                "M per chiudere il manuale", (int)dimension.getWidth(), (int)dimension.getHeight());
    }

    //Disegno della pallina. Le coordinate del centro vengono effettivamente spostate al centro, altrimenti fillOval(...) disegna partendo dall'alto a sx
    private void drawBall(Graphics2D g, Point2D p) {
        int x = (int)p.getX() - pongWorld.getBall().getRadius();
        int y = (int)p.getY() - pongWorld.getBall().getRadius();
        g.setColor(Color.WHITE);
        g.fillOval(x, y, pongWorld.getBall().getRadius() * 2,pongWorld.getBall().getRadius() * 2);
    }

    //Draw of bar number one
    private void drawBarOne(Graphics2D g, Point2D p) {
        g.setColor(Color.WHITE);
        g.fillRect((int)p.getX(),(int)p.getY(), 10,100);
    }

    //Draw of bar number two
    private void drawBarTwo(Graphics2D g, Point2D p) {
        g.setColor(Color.WHITE);
        g.fillRect((int)p.getX(),(int)p.getY(), 10,100);
    }

    @Override
    public void update() {
        repaint();
    }

    public Dimension getDimension() {
        return dimension;
    }
}
