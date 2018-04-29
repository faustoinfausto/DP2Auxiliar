package com.example.sergio.dp2aux;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Recognize {
    public static JSONObject start_recognition(Bitmap imagen){
        JSONObject objeto = toJSON();

        return objeto;
    }

    //esto deberia estar en la clase de cada uno de los objetos pero por ahora es utilitario
    public static JSONObject toJSON() {
        // TODO Auto-generated method stub
        JSONObject inner=new JSONObject();

        try {
            inner.put("Nombre", "Sergio Cama");
            inner.put("Dni", "12345678");
            inner.put("Cumpleaños", "23/04");
        } catch (JSONException e) {

            //e.printStackTrace();
        }

        return inner;
    }

}
