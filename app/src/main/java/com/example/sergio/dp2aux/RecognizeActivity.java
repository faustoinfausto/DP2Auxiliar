package com.example.sergio.dp2aux;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.hardware.camera2.CameraCaptureSession;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class RecognizeActivity extends Activity implements SurfaceHolder.Callback{
    int TAKE_PHOTO_CODE = 1;
    Bitmap imageBitmap;
    ImageView imageview;
    public static final String USER_NAME = "com.example.auxiliar.LoginActivity.USER_NAME";
    public static final String USER_ID = "com.example.auxiliar.LoginActivity.USER_ID";
    boolean keep_going=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recognize);
        _inicializar_backgorund();
        _inicializar_camara();
        wait_for_recognition();
        RelativeLayout relativeLayout= findViewById(R.id.recognizing_layout);
//        relativeLayout.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                start_recognizing();
//                return false;
//            }
//        });
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                start_recognizing();
//            }
//        });

    }

    private void _inicializar_backgorund(){

        RelativeLayout relativeLayout= findViewById(R.id.recognizing_layout);
        relativeLayout.setBackgroundResource(R.drawable.coca_cola_background);

    }


    private  void _tomar_foto (){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore., outputFileUri);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            _procesar_imagen();
        }

    }



    private void _procesar_imagen(){
        JSONObject objeto=Recognize.start_recognition(imageBitmap); //objeto json con datos de la sesion del cliente
        if(objeto !=null){
            //inicia sesion menu principal cards
            _inicio_sesion_cliente(objeto);

        }
        else{
            //error en el reconocimiento
            Alert.generar_alerta(this,"Error en reconocimiento", "Error Login Cliente");
        }
    }

    private void _inicio_sesion_cliente(JSONObject objeto){
        if(es_cumpleano(objeto)){
            //ir a la pantalla de cumplea;os y hacer el flujo de cumplea;os
        }else{
            //ir a menu principal
            _entrar_menu_principal(objeto);
        }
    }

    private boolean es_cumpleano(JSONObject objeto){
        return false;
    }

    private void _entrar_menu_principal(JSONObject objeto){
        Intent intent = new Intent(this, MenuClientActivity.class);
        String name = null;
        String dni = null;
        try {
            name = objeto.get("Nombre").toString();
            dni = objeto.get("Dni").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra(USER_NAME, name);
        intent.putExtra(USER_ID, dni);

        startActivity(intent);
    }


    Camera mCamera;
    SurfaceView mPreview;
    SurfaceHolder surfaceHolder;
    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;
    private final String tag = "VideoServer";
    private  final Integer MY_PERMISSIONS_REQUEST_CAMERA=100;

    private void _inicializar_camara(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    this, Manifest.permission.CAMERA)) {


            } else {
                ActivityCompat.requestPermissions((Activity)this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        }

        mPreview = (SurfaceView)findViewById(R.id.surfaceView);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCamera = Camera.open();

        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                    outStream = new FileOutputStream(new File(getFilesDir(), "tours"));
                    //outStream = new FileOutputStream(String.format("%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                Toast.makeText(getApplicationContext(), "Rostro Capturado", 3000).show();
                start_recognizing();
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);

        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW","surfaceDestroyed");
    }


    public void captureImage(View v) throws IOException {
        //take the picture
        mCamera.takePicture(null, null, jpegCallback);
    }


    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {

        }
    }

    private void wait_for_recognition(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                // do something
                try {
                    captureImage(findViewById(R.id.imageview));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 5000);



    }
    private void start_recognizing(){
        Intent intent = new Intent(this, RecognizeWaitActivity.class);
        startActivity(intent);
    }


}
