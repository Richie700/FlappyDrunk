package flappy.drunk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GameView extends SurfaceView implements Runnable,GestureDetector.OnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    //Boolean to track if the game is playing or not
    volatile boolean playing;

    //GestureDetector
    GestureDetector gestureDetector;

    //Game thread
    private Thread gameThread = null;

    //Adding Player class
    private Player player;

    //Object for drawing
    private Paint paint;
    public Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Dirt list
    private ArrayList <Dirt> dirts = new ArrayList<Dirt>();

    //Object array for cars
    private Car[] cars;
    private int carCount = 2;

    //Object array for bottles
    private Bottle bottle;


    //Constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //Init player
        player = new Player(context, screenX, screenY);

        //Init drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //Adding dirt
        int dirtNumber = 40;
        for (int i = 0; i < dirtNumber; i++) {
            Dirt d = new Dirt(screenX,screenY);
            dirts.add(d);
        }

        //Init car object array
        cars = new Car[carCount];
        for (int i = 0; i < carCount; i++) {
            cars[i] = new Car(context,screenX,screenY);
        }

        //Init bottle object
        bottle = new Bottle(context,screenX,screenY);

        //Init gesture detector
        gestureDetector = new GestureDetector(context,this);
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
    //Here we will update the coordinate of our object.
    private void update() {
        player.update();

        for (Dirt d: dirts) {
            d.update();
        }

        for (int i = 0; i < carCount; i++) {
            cars[i].update();

            //Collision between car and player
            if (Rect.intersects(player.getDetectCollision(),cars[i].getDetectCollision())) {
                player.setY(-200);
                for (Dirt d:dirts) {
                    d.setSpeed(0);
                }
                bottle.setSpeed(0);
            }
        }

        bottle.update();

            if (Rect.intersects(player.getDetectCollision(),bottle.getDetectCollision())) {
                bottle.setX(-500);
            }
    }
    //Here we will draw the characters to the canvas.
    private void draw() {
        //Checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //Locking canvas
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(getResources().getColor(R.color.colorBackground));
            //Setting the paint color to light gray to draw the dirt
            paint.setColor(Color.LTGRAY);
            //Drawing dirt
            for (Dirt d : dirts) {
                paint.setStrokeWidth(4.0f);
                canvas.drawPoint(d.getX(),d.getY(),paint);
            }
            //Drawing the player
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            //Drawing the cars
            for (int i = 0; i < carCount; i++) {
                canvas.drawBitmap(cars[i].getBitmap(), cars[i].getX(), cars[i].getY(), paint);
            }
            //Drawing the bottles
            canvas.drawBitmap(bottle.getBitmap(), bottle.getX(), bottle.getY(), paint);

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
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //*******************
    //Movement
    //*******************

    public void onLeftSwipe() {
       player.userMovingLeft();
    }

    public void onRightSwipe() {
        player.userMovingRight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent first_down_motionEvent, MotionEvent last_move_motionEvent, float velocity_x, float velocity_y) {
        try {
            if (Math.abs(first_down_motionEvent.getY() - last_move_motionEvent.getY()) > SWIPE_MAX_OFF_PATH){
                return false;
            }
            // right to left swipe
            if (first_down_motionEvent.getX() - last_move_motionEvent.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocity_x) > SWIPE_THRESHOLD_VELOCITY) {
                onLeftSwipe();
                Log.d(TAG,"LeftSwipe");
            }
            // left to right swipe
            else if (last_move_motionEvent.getX() - first_down_motionEvent.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocity_x) > SWIPE_THRESHOLD_VELOCITY) {
                onRightSwipe();
                Log.d(TAG,"RightSwipe");
            }
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

}

