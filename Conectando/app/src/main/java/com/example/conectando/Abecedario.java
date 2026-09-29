package com.example.conectando;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Abecedario extends AppCompatActivity {
    private Button btnTeclado, btnAbecedario, btnAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abecedario);
    }
}