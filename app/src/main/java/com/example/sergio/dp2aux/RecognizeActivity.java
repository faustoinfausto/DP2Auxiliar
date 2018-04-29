package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.hardware.camera2.CameraCaptureSession;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;



public class RecognizeActivity extends Activity{
    int TAKE_PHOTO_CODE = 1;
    Bitmap imageBitmap;
    ImageView imageview;
    public static final String USER_NAME = "com.example.auxiliar.LoginActivity.USER_NAME";
    public static final String USER_ID = "com.example.auxiliar.LoginActivity.USER_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recognize);
        _inicializar_backgorund();
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _tomar_foto ();
            }
        });

    }

    private void _inicializar_backgorund(){
        imageview = findViewById(R.id.imageview);
        String uriPath = "android.resource://"+getPackageName()+ "/raw/coca_cola_background";
//        if( smartTV_user_name.equals("tv1") )
//            uriPath = "android.resource://"+getPackageName()+ "/raw/video_coca_cola";
//        if( smartTV_user_name.equals("tv2") )
//            uriPath = "android.resource://"+getPackageName()+ "/raw/video_ripley";
        Uri uri = Uri.parse(uriPath);
        imageview.setImageURI(uri);
    }


    private  void _tomar_foto (){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore., outputFileUri);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            _procesar_imagen();
        }

    }



    private void _procesar_imagen(){
        JSONObject objeto=Recognize.start_recognition(imageBitmap); //objeto json con datos de la sesion del cliente
        if(objeto !=null){
            //inicia sesion menu principal cards
            _inicio_sesion_cliente(objeto);

        }
        else{
            //error en el reconocimiento
            Alert.generar_alerta(this,"Error en reconocimiento", "Error Login Cliente");
        }
    }

    private void _inicio_sesion_cliente(JSONObject objeto){
        if(es_cumpleano(objeto)){
            //ir a la pantalla de cumplea;os y hacer el flujo de cumplea;os
        }else{
            //ir a menu principal
            _entrar_menu_principal(objeto);
        }
    }

    private boolean es_cumpleano(JSONObject objeto){
        return false;
    }

    private void _entrar_menu_principal(JSONObject objeto){
        Intent intent = new Intent(this, MenuClientActivity.class);
        String name = null;
        String dni = null;
        try {
            name = objeto.get("Nombre").toString();
            dni = objeto.get("Dni").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra(USER_NAME, name);
        intent.putExtra(USER_ID, dni);

        startActivity(intent);
    }


}
