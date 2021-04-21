import java.awt.geom.Point2D;

public abstract class AbstractBar {

    //Variables for position and status of bar
    protected Point2D position;
    protected boolean active;

    abstract public void movementUp();

    abstract public void movementDown();

    //Set the initial position of the bar
    public void readyToStart(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
