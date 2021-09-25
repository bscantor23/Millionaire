package com.bscantor.millionaire.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingletonFirebase {
    private final static SingletonFirebase INSTANCE = new SingletonFirebase();
    private static DatabaseReference mDatabase;

    public static DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public static SingletonFirebase getInstance(){
        return INSTANCE;
    }

    public SingletonFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /*public void guardar(String codigo, String nombre, String apellido, String correo){
        Estudiante estudiante = new Estudiante(codigo, nombre, apellido, correo);
        this.mDatabase.child("estudiantes").child(codigo).setValue(estudiante);
    }*/

}
