package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TakePictureFBActivity extends Activity implements View.OnClickListener {

    ImageView picture_fb;

    Button btn_back, btn_reintentar, btn_enviar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_take_picture_fb);

        _inicializar_componentes();

        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture_fb.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                _regresar_menu_usuario();
                break;
            case R.id.btn_reintentar:
                dispatchTakePictureIntent();
                break;
            case R.id.btn_enviar:
                //Servicio de postear en FB
                _regresar_menu_usuario();
                break;
        }
    }

    private void _regresar_menu_usuario(){
        Intent intent = new Intent(this, MenuClientActivity.class);
        //String message = eText_user.getText().toString();
        //intent.putExtra(USER_NAME, message);
        startActivity(intent);
    }

    private void _inicializar_componentes(){
        picture_fb = findViewById(R.id.picture_fb);

        btn_back = findViewById(R.id.btn_back);
        btn_reintentar = findViewById(R.id.btn_reintentar);
        btn_enviar = findViewById(R.id.btn_enviar);

        btn_back.setOnClickListener(this);
        btn_reintentar.setOnClickListener(this);
        btn_enviar.setOnClickListener(this);
    }
}
