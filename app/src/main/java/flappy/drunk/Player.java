package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Timer;

import static android.content.ContentValues.TAG;

public class Player {

    private Bitmap playerBitmap;

    // New variables for the sprite sheet animation

    // These next two values can be anything you like
    // As long as the ratio doesn't distort the sprite too much
    private int frameWidth = 32;
    private int frameHeight = 32;

    // How many frames are there on the sprite sheet?
    private int frameCount = 2;

    // Start at the first frame
    private int currentFrame = 0;

    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;

    // How long should each frame last
    private int frameLengthInMilliseconds = 100;

    //Coordinates
    private int x;
    private int y;

    //Controlling X coordinate so that player won't go outside the screen
    private int maxX;
    private int minX;

    //Collision detector
    private Rect detectCollision;

    //Touch detector
    private boolean touched;

    //Constructor
    public Player(Context context, int screenX, int screenY) {
        //Bitmap from drawable resource
        playerBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.sprite_0);


        //Player position and speed
        x = screenX / 2 - (playerBitmap.getWidth() / 2);
        y = screenY - (screenY / 4);

      //Calculating maxX
        maxX = screenX - playerBitmap.getWidth();

        //Left side x point is 0 so x will always be zero
        minX = 0;

        //Init collision detector
        detectCollision = new Rect(x,y, playerBitmap.getWidth(),playerBitmap.getHeight());

    }

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void handleActionDown(int eventX) {
        if (eventX >= (x - playerBitmap.getWidth()) && (eventX <= (x + playerBitmap.getWidth()))) {
            setTouched(true);
        } else {
            setTouched(false);
        }
    }

    //Updates character coordinates
    public void update() {

        //Player won't go off the screen
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + playerBitmap.getWidth();
        detectCollision.bottom = y + playerBitmap.getHeight();
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) { this.x = x - playerBitmap.getWidth() / 2;}

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return playerBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.playerBitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
