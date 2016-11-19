package com.so2.ace.sendingsimpledata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.so2.ace.sendingsimpledata.MESSAGE";

    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //======== codigo nuevo ========
           boton = (Button) findViewById(R.id.btnTomaFoto);
           //Si no existe crea la carpeta donde se guardaran las fotos
           file.mkdirs();
           //accion para el boton
           boton.setOnClickListener(new View.OnClickListener() {

                        @Override
                public void onClick(View v) {
                     String file = ruta_fotos + getCode() + ".jpg";
                     File mi_foto = new File( file );
                     try {
                                      mi_foto.createNewFile();
                                  } catch (IOException ex) {
                                   Log.e("ERROR ", "Error:" + ex);
                                  }
                              //
                              Uri uri = Uri.fromFile( mi_foto );
                              //Abre la camara para tomar la foto
                              Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                              //Guarda imagen
                              cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                              //Retorna a la actividad
                              startActivityForResult(cameraIntent, 0);
                    }

                       });
           //====== codigo nuevo:end ======
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void simpleData(View view)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.edit_message)));
    }
    



    /**
       * Metodo privado que genera un codigo unico segun la hora y fecha del sistema
       * @return photoCode
       * */
      @SuppressLint("SimpleDateFormat")
      private String getCode()
      {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
           String date = dateFormat.format(new Date() );
           String photoCode = "pic_" + date;
           return photoCode;
          }

}
