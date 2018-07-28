package com.averias.cristian.reporteaverias.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuarios")
public class Usuario {

    @DatabaseField(columnName = "nombre_usuario",  canBeNull = false, id = true)
    public String nombreUsuario;

    @DatabaseField(columnName = "contrasena_usuario",  canBeNull = false)
    public String contrasena;

    @DatabaseField(columnName = "nombre",  canBeNull = false)
    public String nombre;

    @DatabaseField(columnName = "correo",  canBeNull = false)
    public String correo;

    @DatabaseField(columnName = "telefono",  canBeNull = false)
    public String telefono;

    @DatabaseField(columnName = "cedula",  canBeNull = false)
    public String cedula;

    public Usuario(){}

    public Usuario(String nombre,String usuario,String contrasena,String correo,String telefono,String cedula){
        this.nombre = nombre;
        this.nombreUsuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.cedula = cedula;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
