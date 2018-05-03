package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

public class RecognizeWaitActivity  extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recognize_wait);
        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.face_recognition);
        RelativeLayout relativeLayout= findViewById(R.id.recognizing_layout_wait);
        relativeLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                start_new_activity_menu();
                return false;
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity_menu();
            }
        });


    }

    private  void start_new_activity_menu(){
        Intent intent = new Intent(this, MenuClientActivity.class);
        startActivity(intent);
    }
}
