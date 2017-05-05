package flappy.drunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class ButtonMute {
    private Bitmap bitmap;

    int x,y;

    private Rect detectCollision;

    public ButtonMute(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_volume_off_white_48dp);
        x = 1;
        y = 5;

        detectCollision = new Rect(x,y, bitmap.getWidth(),bitmap.getHeight());
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();

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


    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}