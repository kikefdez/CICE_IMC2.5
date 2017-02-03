package com.quique.cice_imc2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ((Button) findViewById(R.id.btnRegistrarse)).setOnClickListener(new BtnListener(this));
    }
}
