package com.quique.cice_imc2;

/**
 * Created by Quique on 03/02/2017.
 */

public class Usuario {
    private String NRID;
    private String Login;
    private String Password;
    private String Nombre;

    public String getNRID() { return NRID; }
    public void setNRID(String valor) { this.NRID = valor; }
    public String getLogin() { return Login; }
    public void setLogin(String valor) { this.Login = valor; }
    public String getPassword() { return Password; }
    public void setPassword(String valor) { this.Password = valor; }
    public String getNombre() { return Nombre; }
    public void setNombre(String valor) {this. Nombre = valor; }

    public Usuario(String NRID, String Login, String Password, String Nombre) {
        setNRID(NRID);
        setLogin(Login);
        setPassword(Password);
        setNombre(Nombre);
    }
}
