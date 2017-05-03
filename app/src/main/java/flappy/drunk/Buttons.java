package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Buttons {
    private Bitmap bitmap;

    private int x, y;
    boolean setTouched, touched;

    public Buttons(Context context, int screenX, int screenY) {

    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void buttonTouch(int eventX, int eventY) {

        if (eventX >= (x - bitmap.getWidth()) && (eventX <= (x + bitmap.getWidth()))) {
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

    public boolean isSetTouched() {
        return setTouched;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}