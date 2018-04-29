package com.example.sergio.dp2aux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

public class IddleActivity extends Activity {

    private String smartTV_user_name;

    VideoView video_iddle;

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
        if( smartTV_user_name.equals("tv1") )
            uriPath = "android.resource://"+getPackageName()+ "/raw/video_coca_cola";
        if( smartTV_user_name.equals("tv2") )
            uriPath = "android.resource://"+getPackageName()+ "/raw/video_ripley";
        Uri uri = Uri.parse(uriPath);
        video_iddle.setVideoURI(uri);
    }

    private void _entrar_actividad_reconocimiento(){
        Intent intent = new Intent(this, RecognizeActivity.class);
        startActivity(intent);
    }



}