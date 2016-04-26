package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.Main;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.gcm.Config;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.modelo.RivosPartnerDB;
import com.yozzibeens.rivostaxipartner.modelosApp.Solicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoObtenerSolicitudes;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoRegistrarGcmId;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudRegistrarGcmId;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudVerificarTodo;
import com.yozzibeens.rivostaxipartner.utilerias.ListRequest;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by danixsanc on 24/01/2016.
 */
public class Splash extends Activity {


    String regId;
    Context context;
    GoogleCloudMessaging gcm;
    public static final String REG_ID = "regId";
    static final String TAG = "Register Activity";

    private Gson gson;
    private ResultadoRegistrarGcmId resultadoRegistrarGcmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        this.gson = new Gson();
        RivosPartnerDB.initializeInstance();
        GoogleCloudMessaging.getInstance(this);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);

                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    String Cabbie_Id = preferencias.getCabbie_Id();

                    //regId = registerGCM();
                    Log.d("Registro", "GCM RegId: " + regId);
                    //servicio.Register_GcmId(regId, Cabbie_Id);
                    /*SolicitudRegistrarGcmId oData = new SolicitudRegistrarGcmId();
                    oData.setCabbie_Id(Cabbie_Id);
                    oData.setGcmId(regId);
                    RegistrarGcmIdWebService(gson.toJson(oData));*/

                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Intent intent = new Intent(Splash.this, Main.class);
                    startActivity(intent);

                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
//gcm

    private void RegistrarGcmIdWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Register_GcmIdWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoRegistrarGcmId = gson.fromJson(result.get("Resultado").toString(), ResultadoRegistrarGcmId.class);

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
                if (resultadoRegistrarGcmId.isError()) {
                    Toast.makeText(getApplicationContext(), resultadoRegistrarGcmId.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }



    public String registerGCM() {

        gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId(context);

        if (TextUtils.isEmpty(regId)) {

            registerInBackground();

            Log.d("Registro",
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            //Toast.makeText(getApplicationContext(), "RegId already available. RegId: " + regId, Toast.LENGTH_LONG).show();
            System.out.print("RegId already available. RegId: " + regId);
        }
        return regId;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getSharedPreferences(
                Splash.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        return registrationId;
    }


    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration id, app versionCode, and expiration time in the
     * application's shared preferences.
     */


    private void registerInBackground() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(context, "Listo", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(context, "!!!!!", Toast.LENGTH_LONG).show();
                        break;
                }
                super.handleMessage(msg);
            }

        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register("1001209534751");

                    /*if (sendRegistrationIdToBackend(regId)) {
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }*/

                    saveRegisterId(context, regId);
                } catch (IOException ex) {
                    handler.sendEmptyMessage(1);
                    Log.e(TAG, ex.getMessage(), ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /*private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    Log.d("Registro", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("Registro", "Error: " + msg);
                }
                Log.d("Registro", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                *//*//**//*Toast.makeText(getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();*//*
                saveRegisterId(context, regId);
            }
        }.execute(null, null, null);
    }*/



    private void saveRegisterId(Context context, String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                Splash.class.getSimpleName(), Context.MODE_PRIVATE);
        Log.i(TAG, "Saving regId on app version ");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.commit();
    }

}
