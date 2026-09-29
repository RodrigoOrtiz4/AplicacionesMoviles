package com.example.arrayimagenes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] intImageID = {R.drawable.logosplash, R.drawable.ic_android, R.drawable.ic_adb, R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
                R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z};

        btnP = findViewById(R.id.btn_p);

        ImageView imvnImage = findViewById(R.id.imvImage);
        imvnImage.setImageResource(intImageID[2]);

    }
}