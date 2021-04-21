import java.awt.geom.Point2D;
import java.util.Random;

public class Ball {

    //Variables of position, velocity over the X and Y axis and status of the ball
    private Point2D position;
    private double speedX;
    private double speedY;
    private boolean active;
    private int radius;

    private static final int MAX_VELOCITY = 20;
    private static final double INCREMENT_VALUE = 0.2;

    private Random rand = new Random();

    //Point2D position
    public Ball() {
        speedX = 0;
        speedY = 0;
        radius = 7;
        active = false;
    }

    //Set initial velocity and activate the status of the ball
    public void startGame() {
        speedX = randomVelocity();
        speedY = randomVelocity();
        active = true;
    }

    //If status is active, the movement is made by the sum of position (X, Y) and velocity
    public void movement() {
        if(active) {
            double x = position.getX() + speedX;
            double y = position.getY() + speedY;
            position = new Point2D.Double(x, y);
        }
    }

    //Stop the movement and deactivate the ball
    public void stopMovement() {
        speedY = 0;
        speedX = 0;
        active = false;
    }

    //Generator of a random velocity, both positive and negative
    private int randomVelocity() {
        int neg = rand.nextInt((2 - 1) + 1) +1;

        if(neg == 1) {
            return rand.nextInt((8 - 4) + 1) + 4;
        }
        else {
            return rand.nextInt((-4 + 8) + 1) - 8;
        }
    }

    //Increments of velocity. If the velocity is negative, in a negative way. If it's positive, in a positive way
    private void incrementVelocity() {
        if(Math.abs(speedX) < MAX_VELOCITY) {
            if (speedX < 0) {
                speedX = speedX - INCREMENT_VALUE;
            } else {
                speedX = speedX + INCREMENT_VALUE;
            }

            if (speedY < 0) {
                speedY = speedY - INCREMENT_VALUE;
            } else {
                speedY = speedY + INCREMENT_VALUE;
            }
        }
    }

    //Set the ball position based on the input
    public void readyToStart(Point2D position) {
        this.position = position;
    }

    //Change direction over the Y axis
    public void changeDirY() {
        speedY = -speedY;
    }

    //Change direction over the X axis and increment velocity
    public void changeDirX() {
        speedX = -speedX;
        incrementVelocity();
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRadius() {
        return radius;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }
}
