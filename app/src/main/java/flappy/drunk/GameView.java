package flappy.drunk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    //Boolean to track if the game is playing or not
    volatile boolean playing;

    //Game thread
    private Thread gameThread = null;

    //Adding Player class
    private Player player;

    //Object for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Constructor
    public GameView(Context context) {
        super(context);

        //Init player
        player = new Player(context);

        //Init drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run() {
        while (playing) {
            //update frame
            update();

            //draw frame
            draw();

            //control
            control();
        }
    }
    //Here we will update the coordinate of our characters.
    private void update() {
        //Updating player position
        player.update();
    }
    //Here we will draw the characters to the canvas.
    private void draw() {
        //Checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //Locking canvas
            canvas = surfaceHolder.lockCanvas();
            //Drawing the player
            canvas.drawBitmap(player.getBitmap(), player.getX(),player.getY(), paint);
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    //This method will control the frames per seconds drawn. Here we are calling the delay method of Thread. And this is actually making our frame rate to aroud 60fps.
    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        //Stopping the gameThread
        try {
            gameThread.join();
        }catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
