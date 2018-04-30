package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String USER_NAME = "com.example.auxiliar.LoginActivity.USER_NAME";
    private IService iService;

    EditText eText_user, eText_pass;
    Button btn_login;

    public static final String USER_ADMIN = "User_Admin" ;
    public static final String user = "nameKey";
    public static final String uui = "phoneKey";

    SharedPreferences sharedpreferences;

    public static final String LIST_SPONSORS = "List_Sponsors" ;
    public static final String list_sponsors = "list_sponsors";

    public static final String ENDPOINT_URL = "https://dpe2.herokuapp.com/";


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
//        if(user.equals("tv1") && pass.equals("p1"))
//            return true;
//        if(user.equals("tv2") && pass.equals("p2"))
//            return true;
//        return false;
        //aca llamo al servicio si es 200 y con body correcto retorno true

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_URL).addConverterFactory(GsonConverterFactory.create()).build();
        iService= retrofit.create(IService.class);
        get_response();

        return true;

    }

    private void get_response(){
        Call<JsonObject> call = iService.select();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                //set_shared_preference(response.body());
                //Log.i("RESPONDE", String.valueOf(response.body().getAsJsonObject().toString()));
                Log.w("2.",new Gson().toJson(response.body()));


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                //Log.i("TAG",String.valueOf(call.isExecuted()));
                Alert.generar_alerta(findViewById(R.id.linear_layout).getContext(),"Sucedio un error", "Fallo de Servicio Login");


            }
        });

    }


    private void set_shared_preference(JsonObject object){
        //parcearlo
        

    }

    private void _inicializar_componentes(){

        eText_user = findViewById(R.id.eText_user);
        eText_pass = findViewById(R.id.eText_pass);

        btn_login = findViewById(R.id.btn_login);
    }

}
