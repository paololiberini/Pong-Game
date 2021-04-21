import java.awt.geom.Point2D;

public class IABar extends AbstractBar {

    //Point2D position
    public IABar() {
        active = false;
    }

    //Up movement of the bar
    public void movementUp() {
        if(position.getY() > 0 && active) {
            double x = position.getX();
            double y = position.getY() - 10;
            position = new Point2D.Double(x,y);
        }
    }

    //Down movement of the bar
    public void movementDown() {
        if(position.getY() < 525 && active) {
            double x = position.getX();
            double y = position.getY() + 10;
            position = new Point2D.Double(x,y);
        }
    }
}

