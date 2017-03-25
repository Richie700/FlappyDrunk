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
    private boolean userMoving;

    //Gravity Value
    private final int GRAVITY = -10;

    //Controlling X coordinate so that player won't go outside the screen
    private int maxX;
    private int minX;

    //Controlling Y coordinate so that player won't go outside the screen
    private int maxY;
    private int minY;

    //Speed limit
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;


    public Player(Context context, int screenX, int screenY) {

        x = 75;
        y = 50;
        speed = 1;

        /*
        x = 450;
        y = 1000;
        speed = 1;
        */
        //Bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.player_back);

        //Calculating maxX
        maxX = screenX - bitmap.getWidth();

        //Calculating maxY
        maxY = screenY - bitmap.getHeight();

        //Left side x point is 0 so x will always be zero
        //minX = 0;

        minY = 0;

        //Init userMoving to false
        userMoving = false;
    }

    public void setUserMoving() {
        userMoving = true;
    }

    public void stopUserMoving() {
        userMoving = false;
    }

    //Updates character coordinates
    public void update() {

        if (userMoving) {
            speed += 2;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        //Moving player to right
        y -= speed + GRAVITY;
         /*
        //Player won't go off the screen
        if (y < minX) {
            y = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
        */
        //Player won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
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
