package com.fireblend.uitest.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Sergio on 8/20/2017.
 */

@DatabaseTable(tableName = "contacts")
public class Contact {
    //Clase entidad para contener cada elemento de la lista, el cual representa un Contacto.

    @DatabaseField(generatedId = true,columnName = "contact_id")
    public int contact_id;

    @DatabaseField(columnName = "contact_name",canBeNull = false)
    public String name;

    @DatabaseField(columnName = "contact_age",canBeNull = false)
    public int age;

    @DatabaseField(columnName = "contact_email",canBeNull = false)
    public String email;

    @DatabaseField(columnName = "contact_phone",canBeNull = false)
    public String phone;

    @DatabaseField(columnName = "contact_city",canBeNull = false)
    public String city;

    /*public Contact(String name, int age, String email, String phone,String city){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }*/

    public Contact(){}
}
