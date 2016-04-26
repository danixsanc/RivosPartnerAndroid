package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.modelosApp.DatosSolicitud;
import com.yozzibeens.rivostaxipartner.modelosApp.Solicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoAceptarSolicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoNotificacion;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoObtenerDatosSolicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoRegistrarGcmId;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudAceptarSolicitud;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudNotificacion;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudObtenerDatosSolicitud;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudRechazarSolicitud;
import com.yozzibeens.rivostaxipartner.utilerias.FechasBD;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by danixsanc on 09/01/2016.
 */
public class View_Request extends AppCompatActivity {

    int client_id = 0;
    int request_id = 0;
    TextView txt_Nombre, txt_Request_Id, txt_Fecha, txt_Hora, txt_Inicio, txt_Destino;
    Button btn_aceptar;
    Button btn_rechazar;
    int  client_list[];
    int  request_list[];
    String  gcm_id_list[];
    int position;
    String Gcm_Id;

    private ResultadoNotificacion resultadoNotificacion;

    private Gson gson;
    private ResultadoAceptarSolicitud resultadoAceptarSolicitud;
    private ResultadoObtenerDatosSolicitud resultadoObtenerDatosSolicitud;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.gson = new Gson();

        Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");

        txt_Nombre = (TextView) findViewById(R.id.txt_Nombre);
        txt_Request_Id = (TextView) findViewById(R.id.txt_Request_Id);
        txt_Fecha = (TextView) findViewById(R.id.txt_Fecha);
        txt_Hora = (TextView) findViewById(R.id.txt_Hora);
        txt_Inicio = (TextView) findViewById(R.id.txt_Inicio);
        txt_Destino = (TextView) findViewById(R.id.txt_Destino);


        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_aceptar.setTypeface(RobotoCondensed_Regular);

        btn_rechazar = (Button) findViewById(R.id.btn_rechazar);
        btn_rechazar.setTypeface(RobotoCondensed_Regular);

        final Preferencias preferencias = new Preferencias(this);
        final String Cabbie_Id = preferencias.getCabbie_Id();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            client_id = Integer.valueOf(bundle.getString("Client_Id"));
            request_id = Integer.valueOf(bundle.getString("Request_Id"));

            Gcm_Id = bundle.getString("Gcm_Id");
            client_list = bundle.getIntArray("List_Client");
            request_list = bundle.getIntArray("List_Request");
            gcm_id_list = bundle.getStringArray("List_gcm_id");
        }

        SolicitudObtenerDatosSolicitud oData = new SolicitudObtenerDatosSolicitud();
        oData.setRequest_Id(String.valueOf(request_id));
        oData.setCabbie_Id(Cabbie_Id);
        ObtenerDatosSolicitudWebService(gson.toJson(oData));


        btn_rechazar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SolicitudRechazarSolicitud oData2 = new SolicitudRechazarSolicitud();
                oData2.setClient_Id(String.valueOf(client_id));
                oData2.setRequest_Id(String.valueOf(request_id));
                oData2.setCabbie_Id(Cabbie_Id);
                RefuseRequestWebService(gson.toJson(oData2));

                SolicitudNotificacion oDataN = new SolicitudNotificacion();
                oDataN.setGcm_Id(Gcm_Id);
                oDataN.setMessage("Tu solicitud cambio de taxista.");
                PushNotificationWebService(gson.toJson(oDataN));

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SolicitudAceptarSolicitud oData = new SolicitudAceptarSolicitud();
                oData.setCabbie_Id(Cabbie_Id);
                oData.setRequest_Id(String.valueOf(request_id));
                oData.setClient_Id(String.valueOf(client_id));
                AcceptRequestWebService(gson.toJson(oData));

                for (int i = 0; i < client_list.length; i++) {

                    position = i;

                    if (client_id != client_list[i]) {
                        SolicitudRechazarSolicitud oData2 = new SolicitudRechazarSolicitud();
                        oData2.setClient_Id(String.valueOf(client_list[i]));
                        oData2.setRequest_Id(String.valueOf(request_list[i]));
                        oData2.setCabbie_Id(Cabbie_Id);
                        RefuseRequestWebService(gson.toJson(oData2));
                        SolicitudNotificacion oDataN = new SolicitudNotificacion();
                        oDataN.setGcm_Id(gcm_id_list[i]);
                        oDataN.setMessage("Tu solicitud cambio de taxista.");
                        oDataN.setType("B");
                        PushNotificationWebService(gson.toJson(oDataN));
                    } else if (client_id == client_list[i]) {
                        SolicitudNotificacion oDataN = new SolicitudNotificacion();
                        oDataN.setGcm_Id(Gcm_Id);
                        oDataN.setMessage("Tu taxista esta en camino.");
                        oDataN.setType("B");
                        preferencias.setGcm_Id(Gcm_Id);
                        preferencias.setClientId(String.valueOf(client_id));
                        PushNotificationWebService(gson.toJson(oDataN));
                    }
                }

                /*Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();*/
            }
        });

    }


    private void ObtenerDatosSolicitudWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.GetRequestDataWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoObtenerDatosSolicitud = gson.fromJson(result.get("Resultado").toString(), ResultadoObtenerDatosSolicitud.class);
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
                if (resultadoObtenerDatosSolicitud.isError())
                {
                    String messageError = resultadoObtenerDatosSolicitud.getMessage();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(View_Request.this);
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
                else
                {
                    ArrayList<DatosSolicitud> solicitud = resultadoObtenerDatosSolicitud.getData();

                    String Fecha = solicitud.get(0).getDate();
                    FechasBD fechasBD = new FechasBD();
                    String Fechaf = fechasBD.ObtenerFecha(Fecha);
                    String Horaf = fechasBD.ObtenerHora(Fecha);

                    txt_Nombre.setText(solicitud.get(0).getName());
                    txt_Request_Id.setText(solicitud.get(0).getRequest_Id());
                    txt_Fecha.setText(Fechaf);
                    txt_Hora.setText(Horaf);
                    txt_Inicio.setText(solicitud.get(0).getInicio());
                    txt_Destino.setText(solicitud.get(0).getDestino());

                }

            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }



    private void AcceptRequestWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Accept_RequestWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoAceptarSolicitud = gson.fromJson(result.get("Resultado").toString(), ResultadoAceptarSolicitud.class);
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
                if (resultadoAceptarSolicitud.isError())
                {
                    String messageError = resultadoAceptarSolicitud.getMessage();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(View_Request.this);
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

            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }

    private void RefuseRequestWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Refuse_RequestWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
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

    private void PushNotificationWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.NotificationWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
            }

            @Override
            public void onTaskUpdate(String result) {

            }

            @Override
            public void onTaskComplete(HashMap<String, Object> result) {

                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoNotificacion = gson.fromJson(result.get("Resultado").toString(), ResultadoNotificacion.class);
                        if (resultadoNotificacion.getSuccess() == 1 ) {
                            int var = client_list.length;
                            if (var == position+1)
                            {
                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        }
                    }
                }
                catch (Exception error) {

                }






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
