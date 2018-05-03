package com.example.sergio.dp2aux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.google.gson.Gson;

public class IddleActivity extends Activity {

    private String smartTV_user_name;

    VideoView video_iddle;

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_iddle);

        Intent intent = getIntent();
        smartTV_user_name = intent.getStringExtra(LoginActivity.USER_NAME);

        _inicializar_componentes();


    }

    private void _inicializar_componentes() {
        video_iddle = findViewById(R.id.video_iddle);
        _set_video_iddle();
        //Creamos el loop
        video_iddle.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        video_iddle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Log.i("TAG", "Video 1 clicked, starting playback");
                //System.out.print("CLICK");
                _entrar_actividad_reconocimiento();
                return false;
            }
        });
        //Iniciamos el video
        video_iddle.start();
    }

    //Deberia ser la respuesta de un servicio
    private void _set_video_iddle(){

        String uriPath = "";
        uriPath = "android.resource://"+getPackageName()+ "/raw/video_coca_cola";
        Uri uri = Uri.parse(uriPath);
        video_iddle.setVideoURI(uri);
        sharedpreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = sharedpreferences.getString("User_Admin", "");
        User_Admin obj = gson.fromJson(json, User_Admin.class);
        Log.i("IddleActivity", obj.getUsername());
    }

    private void _entrar_actividad_reconocimiento(){
        Intent intent = new Intent(this, RecognizeActivity.class);
        startActivity(intent);
    }



}