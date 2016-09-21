package com.yozzibeens.rivostaxipartner.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.controlador.CabbieController;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoCambiarStatus;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudCambiarEstatus;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;

import java.util.HashMap;


/**
 * Created by danixsanc on 12/01/2016.
 */
public class Nav_Perfil extends AppCompatActivity {

    TextView txt_phone_user;
    TextView txt_email_user;
    TextView txt_nombre_user;
    Button btn_modifydata;
    TextView txt_datos_personales,txt_nombre,txt_email,txt_phone;
    Switch sw_status;
    Gson gson;
    ResultadoCambiarStatus resultadoCambiarStatus;

    SolicitudCambiarEstatus solicitudCambiarEstatus;

    //-------------------------------------------------------
    //================NO SE SI OCUPES ESTOS==================
    //-------------------------------------------------------
    TextView textView1s0,textViasdew1s0,textViasdsdfew1s0;
    //=======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_perfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.gson = new Gson();
        Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");

        txt_phone_user = (TextView) findViewById(R.id.txt_phone_user);
        txt_phone_user.setTypeface(RobotoCondensed_Regular);
        txt_email_user = (TextView) findViewById(R.id.txt_email_user);
        txt_email_user.setTypeface(RobotoCondensed_Regular);
        txt_nombre_user = (TextView) findViewById(R.id.txt_nombre_user);
        txt_nombre_user.setTypeface(RobotoCondensed_Regular);
        txt_datos_personales = (TextView) findViewById(R.id.txt_datos_personales);
        txt_nombre = (TextView) findViewById(R.id.txt_nombre);
        txt_nombre.setTypeface(RobotoCondensed_Regular);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_email.setTypeface(RobotoCondensed_Regular);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_phone.setTypeface(RobotoCondensed_Regular);


        sw_status = (Switch) findViewById(R.id.sw_status);
        sw_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(Nav_Perfil.this);
                    dialog1.setMessage("Al desactivar tu perfil no podras recibir solicitudes de clientes. ¿De acuerdo?");
                    dialog1.setCancelable(false);
                    dialog1.setPositiveButton("De acuerdo", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Preferencias preferencias = new Preferencias(getApplicationContext());
                            String cabbieId = preferencias.getCabbie_Id();
                            SolicitudCambiarEstatus oData = new SolicitudCambiarEstatus();
                            oData.setCabbie_Id(cabbieId);
                            oData.setStatus("2");//Ocupado
                            ChangeStatusWebService(gson.toJson(oData));
                        }
                    });
                    dialog1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sw_status.setChecked(false);
                            dialog.cancel();
                        }
                    });
                    dialog1.show();



                }
                else{
                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    String cabbieId = preferencias.getCabbie_Id();
                    SolicitudCambiarEstatus oData = new SolicitudCambiarEstatus();
                    oData.setCabbie_Id(cabbieId);
                    oData.setStatus("1");//Desocupado
                    ChangeStatusWebService(gson.toJson(oData));
                }
            }
        });

        final Preferencias preferencias = new Preferencias(getApplicationContext());
        String Cabbie_Id = preferencias.getCabbie_Id();
        CabbieController cabbieController =  new CabbieController(getApplicationContext());
        Cabbie cabbie = new Cabbie();
        cabbie = cabbieController.obtenerCabbiePorCabbieId(Cabbie_Id);
        String Nombre = cabbie.getName();
        String Correo = cabbie.getEmail();
        String Telefono = cabbie.getPhone();

        txt_email_user.setText(Correo);
        txt_nombre_user.setText(Nombre);
        txt_phone_user.setText(Telefono);


    }
    private void ChangeStatusWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.ChangeStatusCabbie, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoCambiarStatus = gson.fromJson(result.get("Resultado").toString(), ResultadoCambiarStatus.class);

                            String message = resultadoCambiarStatus.getMessage();

                            AlertDialog.Builder dialog1 = new AlertDialog.Builder(Nav_Perfil.this);
                            dialog1.setMessage(message);
                            dialog1.setCancelable(false);
                            dialog1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            /*dialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });*/
                            dialog1.show();
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
            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
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
