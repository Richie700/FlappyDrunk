package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Buttons {
    private Bitmap bitmap;

    boolean touched;
    int x,y;

    public Buttons(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_pause_white_48dp);
        x = screenX - bitmap.getWidth() ;
        y = 5;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void buttonTouch(int eventX, int eventY) {

        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
            if (eventY >= (y - bitmap.getHeight()) && (eventY <= (y + bitmap.getHeight()))) {
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}