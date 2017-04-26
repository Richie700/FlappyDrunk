package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Timer;

import static android.content.ContentValues.TAG;

public class Player {

    private Bitmap bitmap;

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
        //Player position and speed
        x = screenX / 2 - 43;
        y = screenY - 500;

        //Bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.survivor1_stand);

        //Calculating maxX
        maxX = screenX - bitmap.getWidth();

        //Left side x point is 0 so x will always be zero
        minX = 0;

        //Init collision detector
        detectCollision = new Rect(x,y, bitmap.getWidth(),bitmap.getHeight());

    }

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void handleActionDown(int eventX) {
        if (eventX >= (x - bitmap.getWidth()) && (eventX <= (x + bitmap.getWidth()))) {
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
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) { this.x = x;}

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
