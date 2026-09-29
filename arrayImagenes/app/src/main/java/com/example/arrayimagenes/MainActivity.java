package com.example.arrayimagenes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ProgressBar barraProgreso;
    private int tiempoProgreso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barraProgreso = (ProgressBar) findViewById(R.id.pbProgreso);
        final Timer timer = new Timer();
        TimerTask tk = new TimerTask() {
            @Override
            public void run() {
                tiempoProgreso ++;
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
    }
}