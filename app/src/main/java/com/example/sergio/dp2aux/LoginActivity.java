package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String USER_NAME = "com.example.auxiliar.LoginActivity.USER_NAME";

    EditText eText_user, eText_pass;
    Button btn_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        _inicializar_componentes();

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if( _usuario_valido(eText_user.getText().toString(), eText_pass.getText().toString()) )
            _entrar_actividad_servicio();
        else{

            Alert.generar_alerta(this, "El usuario o contrase;a no son validos", "Usuario Incorrecto");
        }
    }

    private void _entrar_actividad_servicio(){
        Intent intent = new Intent(this, IddleActivity.class);
        String message = eText_user.getText().toString();
        intent.putExtra(USER_NAME, message);
        startActivity(intent);
    }

    //Deberia ser la respuesta de un servicio
    private boolean _usuario_valido(String user, String pass){
        if(user.equals("tv1") && pass.equals("p1"))
            return true;
        if(user.equals("tv2") && pass.equals("p2"))
            return true;
        return false;
    }

    private void _inicializar_componentes(){

        eText_user = findViewById(R.id.eText_user);
        eText_pass = findViewById(R.id.eText_pass);

        btn_login = findViewById(R.id.btn_login);
    }

}
