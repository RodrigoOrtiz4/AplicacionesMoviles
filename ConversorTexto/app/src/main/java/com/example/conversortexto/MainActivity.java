package com.example.conversortexto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int PICK_IMAGE = 100;
    Uri imagenUri;
    Button btnConvertir;
    GridView gvImagenes;
    EditText editText;
    int [] intImageID;
    List<Uri> listaImagenes = new ArrayList<>();
    GridViewAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.Theme_ConversorTexto);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConvertir = findViewById(R.id.btnConversor);
        gvImagenes = findViewById(R.id.gvImagenes);
        editText = findViewById(R.id.editTxtFrase);

        /*btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<=editText.length(); i++){
                    char letter = editText.getText().charAt(i);
                    intImageID= new int[]{R.mipmap.a};

                    gvImagenes.setImageResource(intImageID[i]);
                }
            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClipData clipData = data.getClipData();

        for(int i=0; i<clipData.getItemCount(); i++){
            listaImagenes.add(clipData.getItemAt(i).getUri());
        }

        baseAdapter = new GridViewAdapter(MainActivity.this, listaImagenes);
        gvImagenes.setAdapter(baseAdapter);

    }
}