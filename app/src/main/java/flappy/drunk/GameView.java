package flappy.drunk;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class GameView extends SurfaceView implements Runnable {

    Context context;
    public int screenY;
    public int screenX;
    MediaPlayer mediaPlayer;

    //Boolean to track if the game is playing or not
    volatile boolean playing;

    //Game over indicator
    private boolean isGameOver;

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

    //Highscore
    int score;
    int highScore[] = new int[4];
    //Shared Preferences to store scores
    SharedPreferences sharedPreferences;

    Buttons pauseButton;

    //Constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.context = context;
        this.screenY = screenY;
        this.screenX = screenX;

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

        //Init buttons
        pauseButton = new Buttons(context,screenX,screenY);

        //Init score
        score = 0;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        //Init Game Over
        isGameOver = false;

        //Init music
        mediaPlayer = MediaPlayer.create(context,R.raw.rocketman);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

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

        if (isGameOver == false) {
            score++;
        }

        player.update();

        bottle.update();

        for (Dirt d: dirts) {
            d.update();
        }

        for (int i = 0; i < carCount; i++) {
            cars[i].update(screenY);

            Random randomGenerator = new Random();

            if (screenY > 2000) {
                cars[i].setSpeed(randomGenerator.nextInt(15)+15);
            } else {
                cars[i].setSpeed(randomGenerator.nextInt(10)+10);
            }

            //More points = more speed
            if (score > 1000) {
                cars[i].setSpeed(randomGenerator.nextInt(30-20)+20);
            }

            if (score > 2000) {
                cars[i].setSpeed(randomGenerator.nextInt(40-30)+30);
            }

            if (score > 3000) {
                cars[i].setSpeed(randomGenerator.nextInt(50-40)+40);
            }

            //Collision between car and player
            if (Rect.intersects(player.getDetectCollision(),cars[i].getDetectCollision())) {
                player.setY(-200);
                for (Dirt d:dirts) {
                    d.setSpeed(0);
                }
                bottle.setSpeed(0);

                //Storing scores
                isGameOver = true;

                for (int j = 0; j < 4; j++) {
                    if (highScore[j] < score) {
                        highScore[j] = score;
                        break;
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for (int j = 0; j < 4; j++) {
                    int k = j + 1;
                    editor.putInt("score" + k, highScore[j]);
                }
                editor.apply();
            }

            if (Rect.intersects(bottle.getDetectCollision(),cars[i].getDetectCollision())) {
                bottle.setX(-500);
            }
        }

            if (Rect.intersects(player.getDetectCollision(),bottle.getDetectCollision())) {
                bottle.setX(-500);
                score +=100;
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

            //Drawing the score
            paint.setTextSize(40);
            canvas.drawText("Score:" + score, canvas.getWidth() / 2 - 60, 50, paint);

            //Drawin buttons
            canvas.drawBitmap(pauseButton.getBitmap(), pauseButton.getX(), pauseButton.getY(), paint);

            //Draw Game Over
            if (isGameOver) {
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos = (int) ((canvas.getHeight() / 2) - (paint.descent() + paint.ascent() / 2));
                canvas.drawText("Game Over",canvas.getWidth() / 2, yPos,paint);
            }

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
        mediaPlayer.pause();
        //Stopping the gameThread
        try {
            gameThread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resume() {
        playing = true;
        mediaPlayer.start();
        gameThread = new Thread(this);
        gameThread.start();
        pauseButton.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_pause_white_48dp));

    }

    //*******************
    //Movement
    //*******************

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            player.handleActionDown((int)motionEvent.getX());
            pauseButton.buttonTouch((int)motionEvent.getX(), (int)motionEvent.getY());
            if (pauseButton.isTouched() && playing) {
                pauseButton.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_play_arrow_white_48dp));
                pause();
            } else if (pauseButton.isTouched() && !playing) {
                resume();
            }
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (player.isTouched()) {
                player.setX((int) motionEvent.getX());
            }
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (player.isTouched()) {
                player.setTouched(false);
            }
            if (pauseButton.isTouched()) {
                pauseButton.setTouched(false);
            }
        }

        if(isGameOver) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }
        return true;
    }
}

