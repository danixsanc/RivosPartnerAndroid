package com.yozzibeens.rivostaxipartner.actividades;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoMensajeAyuda;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudMensajeAyuda;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;

import java.util.HashMap;


/**
 * Created by danixsanc on 12/01/2016.
 */
public class Nav_Ayuda extends AppCompatActivity {


    Button btnenviarmensaje;
    Button btn_faqs;
    Button makeCall;
    EditText input_asunto;
    EditText input_mensaje;
    TextView Txt_Contact_Us;
    String asunto;
    String mensaje;

    private Gson gson;

    private ProgressDialog progressdialog;
    private ResultadoMensajeAyuda resultadoMensajeAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_ayuda);

        final Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        this.gson = new Gson();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        input_asunto = (EditText) findViewById(R.id.etxt_asunto);
        input_mensaje  = (EditText) findViewById(R.id.etxt_mensaje);

        Txt_Contact_Us = (TextView) findViewById(R.id.Txt_Contact_Us);
        Txt_Contact_Us.setTypeface(RobotoCondensed_Regular);

        makeCall = (Button) findViewById(R.id.btn_makecall);
        makeCall.setTypeface(RobotoCondensed_Regular);
        makeCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                call();
            }
        });

        btn_faqs = (Button) findViewById(R.id.btn_faqs);
        btn_faqs.setTypeface(RobotoCondensed_Regular);
        btn_faqs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Preguntas_Frecuentes.class);
                startActivity(i);
            }
        });

        btnenviarmensaje = (Button) findViewById(R.id.btn_enviarmensaje);
        btnenviarmensaje.setTypeface(RobotoCondensed_Regular);
        btnenviarmensaje.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String asunto = input_asunto.getText().toString();
                String mensaje = input_mensaje.getText().toString();
                if (checkdata(asunto, mensaje))
                {
                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    String Cabbie_Id = preferencias.getCabbie_Id();
                    SolicitudMensajeAyuda oData = new SolicitudMensajeAyuda();
                    oData.setSubject(asunto);
                    oData.setMessage(mensaje);
                    oData.setCabbie_Id(Cabbie_Id);
                    SendMessageWebService(gson.toJson(oData));
                    input_asunto.setText("");
                    input_mensaje.setText("");
                }

            }
        });


    }
    private void SendMessageWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.MessageWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
                progressdialog = new ProgressDialog(Nav_Ayuda.this);
                progressdialog.setMessage("Enviando mensaje, espere");
                progressdialog.setCancelable(true);
                progressdialog.setCanceledOnTouchOutside(false);
                progressdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        progressdialog.dismiss();
                    }
                });
                progressdialog.show();
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoMensajeAyuda = gson.fromJson(result.get("Resultado").toString(), ResultadoMensajeAyuda.class);
                    }
                }
                catch (Exception error) {

                }
            }

            @Override
            public void onTaskUpdate(String result) {
            }

            @Override
            public void onTaskComplete(HashMap<String, Object> result) {
                progressdialog.dismiss();
                String messageError = resultadoMensajeAyuda.getMessage();
                AlertDialog.Builder dialog = new AlertDialog.Builder(Nav_Ayuda.this);
                dialog.setMessage(messageError);
                dialog.setCancelable(true);
                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
                progressdialog.dismiss();
            }
        });
        servicioAsyncService.execute();
    }

    private boolean checkdata(String asunto, String mensaje){

        int cont = 0;

        if ((asunto.length()>0) && (mensaje.length()>0))
        {
            if (asunto.length() > 30)
            {
                cont++;
            }
            if (mensaje.length() > 180)
            {
                cont++;
            }
            if (cont > 0){
                AlertDialog.Builder dialog = new AlertDialog.Builder(Nav_Ayuda.this);
                dialog.setMessage("Hay campos mal escritos.");
                dialog.setCancelable(true);
                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                return false;
            }
            else {
                return true;
            }
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(Nav_Ayuda.this);
            dialog.setMessage("Hay campos vacios.");
            dialog.setCancelable(true);
            dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();

            return false;
        }

    }

    public void call() {
        Intent callIntent;
        try {
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:6673171415"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        }
        catch (ActivityNotFoundException activityException)
        {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
