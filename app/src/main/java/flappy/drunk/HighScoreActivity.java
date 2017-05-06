package flappy.drunk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Typeface;

public class HighScoreActivity extends AppCompatActivity {

    TextView scoreText1,scoreText2,scoreText3,scoreText4;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        //Init textViews
        scoreText1 = (TextView) findViewById(R.id.scoreText1);
        scoreText2 = (TextView) findViewById(R.id.scoreText2);
        scoreText3 = (TextView) findViewById(R.id.scoreText3);
        scoreText4 = (TextView) findViewById(R.id.scoreText4);

        sharedPreferences = getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);

        Typeface pixelFont = Typeface.createFromAsset(getAssets(), "Kemco.ttf");
                scoreText1.setTypeface(pixelFont);
                scoreText2.setTypeface(pixelFont);
                scoreText3.setTypeface(pixelFont);
                scoreText4.setTypeface(pixelFont);

        //Values to the textViews
        scoreText1.setText("1. " + sharedPreferences.getInt("score1", 0));
        scoreText2.setText("2. " + sharedPreferences.getInt("score2", 0));
        scoreText3.setText("3. " + sharedPreferences.getInt("score3", 0));
        scoreText4.setText("4. " + sharedPreferences.getInt("score4", 0));
    }
}
