package flappy.drunk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Init game view object
        gameView = new GameView(this);
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
