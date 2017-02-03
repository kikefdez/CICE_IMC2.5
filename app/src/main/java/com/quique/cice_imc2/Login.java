package com.quique.cice_imc2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        if(sp.getBoolean("autoLogin", false)) {
            setContentView(R.layout.activity_main);
            ((Button)findViewById(R.id.btnCalcular)).setOnClickListener(new BtnListener(this));
            ((Button)findViewById(R.id.btnSalir)).setOnClickListener(new BtnListener(this));
        } else {
            setContentView(R.layout.activity_login);
            ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new BtnListener(this));
            ((Button) findViewById(R.id.btnRegistro)).setOnClickListener(new BtnListener(this));
        }
    }
}
