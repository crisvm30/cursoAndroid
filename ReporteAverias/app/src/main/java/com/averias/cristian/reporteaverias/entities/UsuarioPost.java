package com.averias.cristian.reporteaverias.entities;

public class UsuarioPost {



    public String nombre;

    public String correo;


    public String tel;

    public String cedula;


    public UsuarioPost(String nombre,String correo,String tel,String cedula){
        this.nombre = nombre;
        this.correo = correo;
        this.tel = tel;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
