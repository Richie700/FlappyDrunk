package flappy.drunk;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);
        //Init game view object
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);

    }

    //pausing game when activity is paused
    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
