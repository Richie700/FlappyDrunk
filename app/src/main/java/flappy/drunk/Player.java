package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Jykke on 25.3.2017.
 */

public class Player {

    private Bitmap bitmap;

    //Coordinates
    private int x;
    private int y;

    //Motion speed of the character
    private int speed = 0;

    //Boolean to track the player movement
    private boolean movingRight;
    private boolean movingLeft;

    //Gravity Value
    private final int GRAVITY = 0;

    //Controlling X coordinate so that player won't go outside the screen
    private int maxX;
    private int minX;

    //Speed limit
    private final int MIN_SPEED = -15;
    private final int MAX_SPEED = 15;

    //Constructor
    public Player(Context context, int screenX, int screenY) {
        x = 450;
        y = 1150;
        speed = 0;

        //Bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.player_back);

        //Calculating maxX
        maxX = screenX - bitmap.getWidth();

        //Left side x point is 0 so x will always be zero
        minX = 0;

    }

    public void userMovingRight() {
        movingRight = true;
        movingLeft = false;
    }
    public void userMovingLeft() {
        movingRight = false;
        movingLeft = true;
    }

    //Updates character coordinates
    public void update() {

        if (movingRight) {
            speed += 2;
        }
        if (movingLeft) {
            speed -= 2;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        x += speed + GRAVITY;

        //Player won't go off the screen
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
