package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
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

    SharedPreferences sharedpreferences;

    public static final String ENDPOINT_URL = "http://200.16.7.150:8083/api/v1/";


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
        //Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_URL).addConverterFactory(GsonConverterFactory.create()).build();
        //iService= retrofit.create(IService.class);
        //get_response();

        User_Admin user_admin=create_mock_user();
        set_shared_preference(user_admin);

        return true;

    }

    private void get_response() {
        Test test= new Test("test.test@pucp.pe", "pucppass2018");
        Log.i("2.", new Gson().toJson(test));
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("email", "test.test@pucp.pe");
        jsonParams.put("password", "pucppass2018");

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("email", "test.test@pucp.pe");
            jsonObject.put("password", "pucppass2018");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        Log.i("2.",body.toString());

        Call<JsonObject> call = iService.select(jsonObject.toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //TODO funcion parcear la respuesta y crear el objeto para setear preference user
//                //User_Admin user_admin=create_mock_user();
//                //set_shared_preference(user_admin);
                Integer a=response.code();
                String ab=a.toString();
                Log.i("2.",ab);
                Log.i("2.",new Gson().toJson(response.body()));
//
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
                Alert.generar_alerta(findViewById(R.id.linear_layout).getContext(),"Sucedio un error", "Fallo de Servicio Login");
            }
        });

    }


    private void set_shared_preference(User_Admin user_admin){

        sharedpreferences = getSharedPreferences("UserPrefs",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user_admin);
        prefsEditor.putString("User_Admin", json);
        prefsEditor.commit();
    }

    private void _inicializar_componentes(){

        eText_user = findViewById(R.id.eText_user);
        eText_pass = findViewById(R.id.eText_pass);

        btn_login = findViewById(R.id.btn_login);
    }


    private  User_Admin create_mock_user(){
        Sponsor sp1 = new Sponsor(1, "17:00", "18:00");
        Sponsor sp2 = new Sponsor(2, "18:30","19:30" );
        List<Sponsor> list_sp= new ArrayList<>();
        User_Admin user_admin= new User_Admin("tv1", "qwerty", list_sp);
        return user_admin;
    }

    public class Test{
        private String email;
        private String password;

        public Test(String mail, String password){
            this.email=mail;
            this.password=password;
        }
    }

}
