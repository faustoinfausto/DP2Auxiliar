package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class RouletteActivity extends Activity{

    private Handler mHandler = new Handler();

    private int num_vuelta_ruleta, num_alpha_luz, contador_flash;

    private boolean ruleta_girando;

    ImageView ruleta_humo, blanco_humo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_roulette);

        _inicializar_componentes();

        ruleta_girando = true;
        _girar_ruleta();
    }

    private void _girar_ruleta(){

        for(int i = 0; i < (num_vuelta_ruleta/2); i++)
            ruleta_humo.setRotation(ruleta_humo.getRotation() + 1);

        num_vuelta_ruleta++;

        if(ruleta_girando) {
            ruleta_girando = false;
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    _brillo_aka_humo();
                }
            }, 5*1000);
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    _entrar_actividad_prize();
                }
            }, 5*1000 + 600);
        }
        mHandler.postDelayed(new Runnable() {
                public void run() {
                    _girar_ruleta();
                }
            }, 10);
    }

    private void _entrar_actividad_prize(){
        Intent intent = new Intent(this, PrizeRoulette.class);
        //String message = eText_user.getText().toString();
        //intent.putExtra(USER_NAME, message);
        startActivity(intent);
    }

    private void _brillo_aka_humo(){
        for(int i = 0; i < (num_alpha_luz/4); i++) {
            if (blanco_humo.getBackground().getAlpha() < 255)
                blanco_humo.getBackground().setAlpha(blanco_humo.getBackground().getAlpha() + 1);
            else
                _entrar_actividad_prize();
        }
        num_alpha_luz++;
        mHandler.postDelayed(new Runnable() {
            public void run() {
                _brillo_aka_humo();
            }
        }, 5);
    }

    private void _inicializar_componentes(){
        ruleta_humo = findViewById(R.id.ruleta_humo);
        blanco_humo = findViewById(R.id.blanco_humo);

        blanco_humo.getBackground().setAlpha(0);

        ruleta_girando = false;

        num_vuelta_ruleta = 0;
        num_alpha_luz = 0;
        contador_flash = 0;
    }
}
