package com.quique.cice_imc2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Quique on 27/01/2017.
 */

public class BtnListener implements View.OnClickListener {
    private Context contexto;

    public Context getContexto() { return this.contexto; }
    public void setContexto(Context valor) { this.contexto = valor; }
    public BtnListener(Context valor) { setContexto(valor); }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCalcular:
                btnCalcular(v);
                break;
            case R.id.btnSalir:
                btnSalir(v);
                break;
            case R.id.btnLogin:
                btnLogin(v);
                break;
            case R.id.btnRegistro:
                btnRegistro(v);
                break;
            case R.id.btnRegistrarse:
                btnRegistrarse(v);
                break;
        }
    }

    public void btnCalcular(View v){
        Log.d(getClass().getCanonicalName(), "Detectado click en botón - CALCULAR");
        Activity actividad = (Activity)contexto;

        Log.d(getClass().getCanonicalName(), "Recuperamos los valores introducidos - CALCULAR");
        String cajaAltura = "" + ((EditText)actividad.findViewById(R.id.cajaAltura)).getText().toString();
        String cajaPeso = "" + ((EditText)actividad.findViewById(R.id.cajaPeso)).getText().toString();

        if(cajaAltura != "" && cajaPeso != "") {
            Log.d(getClass().getCanonicalName(), "Valor recuperado de Altura: " + cajaAltura + " cm.");
            Log.d(getClass().getCanonicalName(), "Valor recuperado de Peso: " + cajaPeso + " kg.");

            float valorIMC = IMCalc.calcularIMC(Float.parseFloat(cajaAltura), Float.parseFloat(cajaPeso));

            Log.d(getClass().getCanonicalName(), "Creamos el intent para enviar el hilo de ejecución a la respuesta");
            Intent respuesta = new Intent(contexto,Resultados.class);

            Log.d(getClass().getCanonicalName(), "Cargamos en en Bundle los valores de altura, peso y valorIMC");
            respuesta.putExtra("valorAltura", cajaAltura);
            respuesta.putExtra("valorPeso", cajaPeso);
            respuesta.putExtra("valorIMC", "" + valorIMC);

            Log.d(getClass().getCanonicalName(), "Lanzamos el hilo a la siguiente actividad: Resultados");
            contexto.startActivity(respuesta);

        } else {
            if(cajaAltura == "" && cajaPeso =="")
                Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorAmbos), Toast.LENGTH_SHORT).show();
            else{
                if(cajaAltura=="")
                    Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorAltura), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorPeso), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void btnSalir(View v){
        Log.d(getClass().getCanonicalName(), "Detectado click en botón - SALIR");
        Activity actividad = (Activity)contexto;

        SharedPreferences sp = contexto.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putBoolean("autoLogin", false);
        spEditor.commit();

        actividad.startActivity(new Intent(actividad, Login.class));
        //actividad.finish();
    }
    public void btnLogin(View v){
        Log.d(getClass().getCanonicalName(), "Detectado click en botón - LOGIN");
        Activity actividad = (Activity)contexto;

        Log.d(getClass().getCanonicalName(), "Recuperamos los valores introducidos - LOGIN");
        String cajaLogin = "" + ((EditText)actividad.findViewById(R.id.cajaLogin)).getText().toString();
        String cajaPassword = "" + ((EditText)actividad.findViewById(R.id.cajaPassword)).getText().toString();

        if(cajaLogin != "" && cajaPassword != ""){
            BBDD sqlDB = new BBDD(contexto, "Usuario", null, 1);
            List<Usuario> listado = sqlDB.recuperarDatos("Login = '" + cajaLogin + "' AND Password = '" + cajaPassword + "'");
            if(listado.isEmpty()){
                if(null == v.getTag()){
                    v.setTag(1);
                } else {
                    int intentos =(int)v.getTag();
                    if (intentos < 3) {
                        v.setTag((int) v.getTag() + 1);
                    } else {
                        actividad.finishActivity(0);
                    }
                }
                Toast.makeText(contexto, contexto.getResources().getString(R.string.respFailLongin), Toast.LENGTH_SHORT).show();
            } else {
                if(((CheckBox)actividad.findViewById(R.id.checkRecordarme)).isChecked()) {
                    SharedPreferences sp = contexto.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sp.edit();
                    spEditor.putBoolean("autoLogin", true);
                    spEditor.commit();
                }
                Log.d(getClass().getCanonicalName(), "Usuario encontrado en base de datos - Autenticado");
                actividad.startActivity(new Intent(actividad, MainActivity.class));
                //actividad.finish();
            }

        } else {
            if(cajaLogin == "" && cajaPassword =="")
                Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorLoginAmbos), Toast.LENGTH_SHORT).show();
            else{
                if(cajaLogin=="")
                    Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorLoginUsuario), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorLoginPassword), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void btnRegistro (View v) {
        Log.d(getClass().getCanonicalName(), "Detectado click en botón - REGISTRO");
        Activity actividad = (Activity)contexto;
        actividad.startActivity(new Intent(actividad, Registro.class));
        actividad.finish();
    }
    public void btnRegistrarse (View v) {
        Log.d(getClass().getCanonicalName(), "Detectado click en botón - REGISTRARSE");
        Activity actividad = (Activity)contexto;

        Log.d(getClass().getCanonicalName(), "Recuperamos los valores introducidos - REGISTRARSE");
        String cajaNombre = "" + ((EditText)actividad.findViewById(R.id.cajaNombreREG)).getText().toString();
        String cajaLogin = "" + ((EditText)actividad.findViewById(R.id.cajaLoginREG)).getText().toString();
        String cajaPassword = "" + ((EditText)actividad.findViewById(R.id.cajaPasswordREG)).getText().toString();

        if(cajaNombre != "" && cajaLogin != "" && cajaPassword != ""){
            BBDD sqlDB = new BBDD(contexto, "Usuario", null, 1);
            String respuesta = sqlDB.actualizarDatos(new Usuario("0", cajaLogin, cajaPassword, cajaNombre));

            if(respuesta == "Todo OK"){
                if(((CheckBox)actividad.findViewById(R.id.checkRecordarmeREG)).isChecked()) {
                    SharedPreferences sp = contexto.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sp.edit();
                    spEditor.putBoolean("autoLogin", true);
                    spEditor.commit();
                }
                actividad.startActivity(new Intent(actividad, MainActivity.class));
                //actividad.finish();
            } else {
                Toast.makeText(contexto, respuesta, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(contexto, contexto.getResources().getString(R.string.respErrorRegistro), Toast.LENGTH_SHORT).show();
        }
    }
}
