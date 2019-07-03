package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    SeekBar seekBar;
    TextView textView;
    Button go;
    Boolean counterisactive=false;
    MediaPlayer mediaPlayer;
    CountDownTimer timer;

    public  void reset(){
        counterisactive= false;
        textView = findViewById(R.id.textView);
        go = findViewById(R.id.go);
        go.setText("Start");
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.egg);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setEnabled(true);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        updateTimer(30);
        //mediaPlayer.stop();
    }
    public void restore(View view){
        go.setVisibility(view.VISIBLE);
        timer.cancel();
        reset();
    }

    public void updateTimer(int secLeft ){
        int min = secLeft/60;
        int sec = secLeft - min*60;

        String secString = Integer.toString(sec);
        if(sec<10){
            secString="0"+secString;
        }
        textView.setText(Integer.toString(min)+":"+secString);
    }

    public void ControlTimer(View view){

        if(!counterisactive) {
            counterisactive = true;
            seekBar.setEnabled(false);
            go.setVisibility(view.INVISIBLE);

            Log.i("Button", "Pressed");
            timer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //Log.i("secs ", Long.toString(millisUntilFinished));
                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {

                    //counterisactive = false;

                    imageView.setImageResource(R.drawable.chick);

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.birds);
                    mediaPlayer.start();

                    //Toast.makeText(getApplicationContext(),"Finished",Toast.LENGTH_SHORT).show();

                }
            }.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            reset();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Progress = ", Integer.toString(progress));
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
