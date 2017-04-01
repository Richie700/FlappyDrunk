package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Bottle {

    private int x;
    private int y;
    private int speed = 3;

    private Bitmap bitmap;
    private Bitmap resizedBitmap;

    private int bitmapWidth;
    private int bitmapHeight;

    private int minY;
    private int maxY;
    private int minX;
    private int maxX;

    //Collision detector
    private Rect detectCollision;

    public Bottle(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.genericitem_color_127);
        //Resize bottle image
        bitmapWidth = 30;
        bitmapHeight = 60;
        resizedBitmap = Bitmap.createScaledBitmap(bitmap,bitmapWidth,bitmapHeight,false);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        //Generating a random coordinate but keeping the coordinate inside the screen size
        Random randomGenerator = new Random();
        y = minY - bitmapHeight;
        x = randomGenerator.nextInt(maxX) - bitmapWidth;
        if (x < bitmapWidth) {
            x = x + bitmapWidth;
        }

        //Init collision detector
        detectCollision = new Rect(x,y, bitmapWidth,bitmapHeight);
    }

    public void update() {
        y += speed;

        //If car reaches bottom of the screen, it will spawn again at the top of the screen
        if ( y > maxY + bitmapHeight) {
            Random randomGenerator = new Random();
            y = minY - bitmapHeight;
            x = randomGenerator.nextInt(maxX) - bitmapWidth;
            if (x < bitmapWidth) {
                x = x + bitmapWidth;
            }
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmapWidth;
        detectCollision.bottom = y + bitmapHeight;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return resizedBitmap;
    }


    public void setX(int x) {
        this.x = x;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
