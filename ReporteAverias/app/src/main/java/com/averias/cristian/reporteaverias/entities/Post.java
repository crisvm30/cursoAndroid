package com.averias.cristian.reporteaverias.entities;

public class Post {

    public String id;
    public String tipo;
    public UsuarioPost usuario;
    public String imagen;
    public Ubicacion ubicacion;
    public String descripcion;
    public String nombre;
    public String fecha;


    public Post(String id, String tipo, UsuarioPost usuario, String imagen, Ubicacion ubicacion, String descripcion, String nombre, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.usuario = usuario;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.fecha = fecha;
    }


}
