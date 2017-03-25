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

    public Player(Context context) {
        x = 450;
        y = 1000;
        speed = 1;

        //Bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.player_back);
    }

    //Updates character coordinates
    public void update() {
        //Updating y coordinate
        y--;
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
