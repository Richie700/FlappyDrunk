package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Car {
    private int x;
    private int y;
    private int speed = 0;

    private Bitmap bitmap;

    private int minY;
    private int maxY;
    private int minX;
    private int maxX;

    //Collision detector
    private Rect detectCollision;

    //Constructor
    public Car(Context context,int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.car_black_1);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        //Generating a random coordinate but keeping the coordinate inside the screen size
        Random randomGenerator = new Random();
        speed = randomGenerator.nextInt(6)+8;
        y = minY - bitmap.getHeight();
        x = randomGenerator.nextInt(maxX) - bitmap.getWidth();
        if (x < bitmap.getWidth()) {
            x = x + bitmap.getWidth();
        }

        //Init collision detector
        detectCollision = new Rect(x,y, bitmap.getWidth(),bitmap.getHeight());
    }

    public void update() {
        y += speed;

        //If car reaches bottom of the screen, it will spawn again at the top of the screen
        if ( y > maxY + getBitmap().getHeight()) {
            Random randomGenerator = new Random();
            speed = randomGenerator.nextInt(6) + 8;
            y = minY - bitmap.getHeight();
            x = randomGenerator.nextInt(maxX) - bitmap.getWidth();
            if (x < bitmap.getWidth()) {
                x = x + bitmap.getWidth();
            }
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
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
