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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        RivosPartnerDB.initializeInstance();
        GoogleCloudMessaging.getInstance(this);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);

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

}
