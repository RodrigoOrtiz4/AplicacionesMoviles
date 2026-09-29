package com.example.arrayimagenes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    private Button btnTeclado, btnAbecedario, btnAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        btnTeclado = (Button)findViewById(R.id.btnTeclado);
        btnAbecedario = (Button)findViewById(R.id.btnAbecedario);
        btnAcercaDe = (Button) findViewById(R.id.btnAbout);

        btnTeclado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent irPantallaTeclado = new Intent(Menu.this, Teclado.class);
                    startActivity(irPantallaTeclado);
                }
                catch (Exception ex){
                    Toast.makeText(Menu.this, "Ocurrió un problema al intentar cargar la pantalla Menu." + ex.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ONCLICK", "Error: btnTeclado" + ex.getMessage() );
                }
            }
        });

        btnAbecedario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent irPantallaAbecedario = new Intent(Menu.this, Abecedario.class);
                    startActivity(irPantallaAbecedario);
                }
                catch (Exception ex){
                    Toast.makeText(Menu.this, "Ocurrió un problema al intentar cargar la pantalla Abecedario." + ex.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ONCLICK", "Error: btnAbecedario" + ex.getMessage() );
                }
            }
        });

        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent irPantallaAcercaDe = new Intent(Menu.this, AcercaDe.class);
                    startActivity(irPantallaAcercaDe);
                }
                catch (Exception ex){
                    Toast.makeText(Menu.this, "Ocurrió un problema al intentar cargar la pantalla Acerca De." + ex.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ONCLICK", "Error: btnAbout" + ex.getMessage() );
                }
            }
        });
    }
}
