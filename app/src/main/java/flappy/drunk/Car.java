package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.Log;

import java.util.Random;

public class Car {
    private int x;
    private int y;
    private int speed;

    private Bitmap bitmap;

    private int minY;
    private int maxY;
    private int minX;
    private int maxX;

    private int randomLane;
    private int lane1, lane2, lane3;
    //Collision detector
    private Rect detectCollision;

    //Constructor
    public Car(Context context,int screenX, int screenY,int lane) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.car_black_1);
        Log.d("BitmapWidth = ",String.valueOf(bitmap.getWidth()));

        maxX = screenX;
        Log.d("maxX = ",String.valueOf(maxX));
        maxY = screenY;
        minX = 0;
        minY = 0;

        //Car lanes
        lane1 = (maxX / 3) - (maxX / 6);
        lane2 = (maxX / 3) + (maxX / 3) - (maxX / 6);
        lane3 = (maxX / 3) + (maxX / 3) + (maxX / 3) - (maxX / 6);

        Random randomGenerator = new Random();

        //Speed
        if (screenY > 2000) {
            speed = randomGenerator.nextInt(15)+15;
        } else {
            speed = randomGenerator.nextInt(10)+10;
        }

        //Random lane
        y = minY - bitmap.getHeight();
        //randomLane = randomGenerator.nextInt(3);
        randomLane = lane;
        Log.d("Lane = ",String.valueOf(randomLane));
        if (randomLane == 0) {
            x = lane1 - (bitmap.getWidth() / 2 );
        } else if (randomLane == 1) {
            x = lane2 - (bitmap.getWidth() / 2 );
        } else if (randomLane == 2) {
            x = lane3 - (bitmap.getWidth() / 2 );
        }

        //Init collision detector
        detectCollision = new Rect(x,y, bitmap.getWidth(),bitmap.getHeight());
    }

    public void update(int screenY) {
        y += speed;

        //If car reaches bottom of the screen, it will spawn again at the top of the screen
        if ( y > maxY + bitmap.getHeight()) {
            Random randomGenerator = new Random();

            y = minY - bitmap.getHeight();
            randomLane = randomGenerator.nextInt(3);
            Log.d("Lane = ",String.valueOf(randomLane));
            if (randomLane == 0) {
                x = lane1 - (bitmap.getWidth() / 2 );
            } else if (randomLane == 1) {
                x = lane2 - (bitmap.getWidth() / 2 );
            } else if (randomLane == 2) {
                x = lane3 - (bitmap.getWidth() / 2 );
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
