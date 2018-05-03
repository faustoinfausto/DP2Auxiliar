package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MenuClientActivity extends Activity {

    private String user_name;
    private String user_id;
    private boolean flag_idle=true;
    private Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_client_recycleviewer);
        Intent intent = getIntent();
        //user_name = intent.getStringExtra(RecognizeActivity.USER_NAME);
        user_name="Blanca Rezza";
        user_id = intent.getStringExtra(RecognizeActivity.USER_ID);
        _set_components();
        _set_not_idle();
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer = new Timer();
        timer.schedule(logoutTimeTask, 60000);



    }
    private void _set_components(){
        TextView textView= findViewById(R.id.nombre_cliente);
        textView.setText("Hola " + user_name + "!");
        textView.setTextSize(40);
        //cards se inicializan
        initializeData();
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(opciones);
        rv.setAdapter(adapter);

    }

    class Object_opcion {
        String name;
        Uri uri;

        Object_opcion(String name, Uri uri) {
            this.name = name;
            this.uri = uri;
        }
    }

    private List<Object_opcion> opciones;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        String uriPath = "android.resource://"+getPackageName()+ "/raw/ruleta";
        Uri uri1 = Uri.parse(uriPath);
        uriPath = "android.resource://"+getPackageName()+ "/raw/camara";
        Uri uri2 = Uri.parse(uriPath);
        opciones = new ArrayList<>();
        opciones.add(new Object_opcion("Ruleta de Promociones", uri1));
        opciones.add(new Object_opcion("Tomate una Foto", uri2));
    }


    private void _set_not_idle(){
         LinearLayout linearLayout= findViewById(R.id.linear_layout);
         linearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 flag_idle=false;
             }
         });
    }

    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {

            //redirect user to login screen
            if(flag_idle==true){
                Intent i = new Intent(MenuClientActivity.this, IddleActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
            flag_idle=false;
            finish();
        }

    }

}
