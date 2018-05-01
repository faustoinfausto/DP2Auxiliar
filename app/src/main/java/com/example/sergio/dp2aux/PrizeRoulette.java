package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PrizeRoulette extends Activity {

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_prize_roulette);
        //_inicializar_componentes();
        //btn_login.setOnClickListener(this);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                _entrar_actividad_menu();
            }
        }, 2*1000);
    }

    private void _entrar_actividad_menu(){
        Intent intent = new Intent(this, MenuClientActivity.class);
        //String message = eText_user.getText().toString();
        //intent.putExtra(USER_NAME, message);
        startActivity(intent);

    }
}
