package flappy.drunk;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonPlay;
    private Button buttonHighScore;
    private TextView title;
    private ImageView titleImg;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.rocketman);
        mediaPlayer.start();

        //Setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface pixelFont = Typeface.createFromAsset(getAssets(), "Kemco.ttf");
        title = (TextView) findViewById(R.id.textView);
        title.setTypeface(pixelFont);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setTypeface(pixelFont);
        buttonHighScore = (Button) findViewById(R.id.buttonHighScore);
        buttonHighScore.setTypeface(pixelFont);
        buttonPlay.setOnClickListener(this);
        buttonHighScore.setOnClickListener(this);

        titleImg = (ImageView)findViewById(R.id.imageView) ;
        titleImg.setImageResource(R.drawable.player_animation);
        AnimationDrawable titleAnimation = (AnimationDrawable)titleImg.getDrawable();
        titleAnimation.start();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonPlay) {
            startActivity(new Intent(MainActivity.this,GameActivity.class));
            mediaPlayer.stop();
        }
        if (view == buttonHighScore) {
            startActivity(new Intent(MainActivity.this,HighScoreActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
