package com.example.conectando;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Teclado extends AppCompatActivity {

    //App
    Button btnA,btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnENE,btnO,btnP,btnQ,btnR,btnS,btnT,btnU,btnV,btnW,btnX,btnY, btnZ;
    ImageView imaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teclado);


        int[] intImageID = {R.drawable.logosplash, R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
                R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.ene, R.drawable.o, R.drawable.p, R.drawable.q,
                R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z};

        imaView = findViewById(R.id.imvImage);
        imaView.setImageResource(intImageID[0]);

        btnA = findViewById(R.id.btn_a);
        btnB = findViewById(R.id.btn_b);
        btnC = findViewById(R.id.btn_c);
        btnD = findViewById(R.id.btn_d);
        btnE = findViewById(R.id.btn_e);
        btnF = findViewById(R.id.btn_f);
        btnG = findViewById(R.id.btn_g);
        btnH = findViewById(R.id.btn_h);
        btnI = findViewById(R.id.btn_i);
        btnJ = findViewById(R.id.btn_j);
        btnK = findViewById(R.id.btn_k);
        btnL = findViewById(R.id.btn_l);
        btnM = findViewById(R.id.btn_m);
        btnN = findViewById(R.id.btn_n);
        btnENE = findViewById(R.id.btn_ene);
        btnO = findViewById(R.id.btn_o);
        btnP = findViewById(R.id.btn_p);
        btnQ = findViewById(R.id.btn_q);
        btnR = findViewById(R.id.btn_r);
        btnS = findViewById(R.id.btn_s);
        btnT = findViewById(R.id.btn_t);
        btnU = findViewById(R.id.btn_u);
        btnV = findViewById(R.id.btn_v);
        btnW = findViewById(R.id.btn_w);
        btnX = findViewById(R.id.btn_x);
        btnY = findViewById(R.id.btn_y);
        btnZ = findViewById(R.id.btn_z);

        btnA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[1]);
            }
        });

        btnB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[2]);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[3]);
            }
        });

        btnD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[4]);
            }
        });

        btnE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[5]);
            }
        });

        btnF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[6]);
            }
        });

        btnG.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[7]);
            }
        });

        btnH.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[8]);
            }
        });

        btnI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[9]);
            }
        });

        btnJ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[10]);
            }
        });

        btnK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[11]);
            }
        });

        btnL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[12]);
            }
        });

        btnM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[13]);
            }
        });

        btnN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[14]);
            }
        });

        btnENE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[15]);
            }
        });

        btnO.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[16]);
            }
        });

        btnP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[17]);
            }
        });

        btnQ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[18]);
            }
        });

        btnR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[19]);
            }
        });

        btnS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[20]);
            }
        });

        btnT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[21]);
            }
        });

        btnU.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[22]);
            }
        });

        btnV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[23]);
            }
        });

        btnW.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[24]);
            }
        });

        btnX.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[25]);
            }
        });

        btnY.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[26]);
            }
        });

        btnZ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                imaView.setImageResource(intImageID[27]);
            }
        });


    }
}
