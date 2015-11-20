package com.example.sachi.pig;

import android.app.AlertDialog;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button roll;
    Button hold;
    Button reset;
    TextView pScore;
    TextView cScore;
    TextView pTotalScore;
    TextView cTotalScore;
    Random random = new Random();
    SoundPool pool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int soundID;
    Handler handler;
    Timer timer = new Timer();
    Player player;
    Computer computer;
    Boolean rolling = false;
    int score=0;
    int turn=1;
    AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new Player("Sachith");
        computer = new Computer();
        imageView = (ImageView) findViewById(R.id.dice);

        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Pig");


        roll = (Button) findViewById(R.id.roll);
        hold = (Button) findViewById(R.id.hold);
        reset = (Button) findViewById(R.id.reset);
        soundID = pool.load(this,R.raw.shake_dice,1);

        pScore = (TextView)findViewById(R.id.scoreP1);
        cScore = (TextView)findViewById(R.id.scoreP2);
        pTotalScore = (TextView)findViewById(R.id.tScoreP1);
        cTotalScore = (TextView)findViewById(R.id.tscoreP2);
        Computer computer = new Computer();
        handler = new Handler(callback);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rolling){
                    rolling = true;
                    roll.setEnabled(false);
                    pool.play(soundID, 1.0f, 1.0f, 0, 0, 1.0f);
                    imageView.setImageResource(R.drawable.dice3d);
                    timer.schedule(new roll(),400);

                }
            }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn = 2;
                player.updateScore(0);
                pScore.setText("Score: "+0);
                play();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re_set();
                turn = 1;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void play(){
        if(turn == 1){
            int temp = player.updateScore(score);
            pScore.setText("Score: " + player.getScore());
            pTotalScore.setText("Total Score: "+player.getTotalScore());
            if(temp == 2){
                won();
            }
            if(score == 1){
                score = 0;
                turn = 2;
                roll.setEnabled(false);
                hold.setEnabled(false);

                play();
            }

        }
        else{


            int temp = computer.updateScore(score);
            cScore.setText("Score: " +computer.getScore());
            cTotalScore.setText("Total score: "+computer.getTotalScore());
            if(temp == 2){
                won();
                return;
            }
            if(score == 1 || temp == 1){
                turn = 1;
                roll.setEnabled(true);
                hold.setEnabled(true);
                return;

            }

                pool.play(soundID, 1.0f, 1.0f, 0, 0, 1.0f);
                timer.schedule(new roll(), 800);
        }
    }

    private void won() {
        if(turn == 1){
            dialog.setMessage("Payer won!");
            dialog.setCancelable(true);
            dialog.create().show();

        }
        else{
            dialog.setMessage("cmputer won!");
            dialog.setCancelable(true);
            dialog.create().show();
        }
        re_set();
    }

    private void re_set() {
        player.reset();
        computer.reset();
        pScore.setText("Score: " + 0);
        pTotalScore.setText("Total Score: " + 0);
        cScore.setText("Score: " +0);
        cTotalScore.setText("Total score: "+0);
    }

    Callback callback = new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch(random.nextInt(6)+1) {
                case 1:
                    imageView.setImageResource(R.drawable.one);
                    score = 1;

                    break;
                case 2:
                    imageView.setImageResource(R.drawable.two);
                    score = 2;
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.three);
                    score = 3;
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.four);
                    score = 4;
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.five);
                    score = 5;
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.six);
                    score = 6;
                    break;
                default:
            }
            play();
            rolling=false;
            	//user can press again
            roll.setEnabled(true);
            return true;
        }

    };

    class roll extends TimerTask{

        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }
}
