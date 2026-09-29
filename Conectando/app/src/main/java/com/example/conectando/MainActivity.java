package com.example.conectando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Splash
    private ProgressBar barraProgreso;
    private int tiempoProgreso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* SPLASH INICIO */
        barraProgreso = (ProgressBar) findViewById(R.id.pbProgreso);
        final Timer timer = new Timer();
        TimerTask tk = new TimerTask() {
            @Override
            public void run() {
                tiempoProgreso = tiempoProgreso + 6;
                barraProgreso.setProgress(tiempoProgreso);
                if(barraProgreso.getProgress()==100)
                {
                    Intent barraProgres = new Intent(MainActivity.this, Menu.class);
                    MainActivity.this.startActivity(barraProgres);
                    timer.cancel();
                }
            }
        };
        timer.schedule(tk,0,100);
        /* SPLASH FIN */
    }
}