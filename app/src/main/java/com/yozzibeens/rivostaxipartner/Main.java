package com.yozzibeens.rivostaxipartner;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yozzibeens.rivostaxipartner.actividades.Login;
import com.yozzibeens.rivostaxipartner.actividades.On_Process;
import com.yozzibeens.rivostaxipartner.fragmentos.DrawerMenu;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.modelosApp.Solicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoAgregarCoordenadasTaxista;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoConsultarReferencia;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoNotificacion;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoObtenerSolicitudes;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoVerificarTodo;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudAgregarCoordenadasTaxista;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudConsultarReferencia;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudNotificacion;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudObtenerSolicitudes;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudVerificarTodo;
import com.yozzibeens.rivostaxipartner.utilerias.FechasBD;
import com.yozzibeens.rivostaxipartner.utilerias.ListRequest;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;
import com.yozzibeens.rivostaxipartner.actividades.View_Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Main extends AppCompatActivity implements LocationListener {

    private DrawerMenu mDrawerMenu;

    ListView listrequestList;
    ListRequestCustomAdapter listRequestAdapter;
    ArrayList<ListRequest> listrequestArray = new ArrayList<ListRequest>();
    int client_id[] = new int[0];
    int request_id[] = new int[0];
    String gcm_id[] = new String[0];
    LinearLayout btn_en_proceso;
    LinearLayout btn_avisar;
    LinearLayout btn_qrcode;
    LinearLayout linear_btn;
    TextView txt_pendientes;
    TextView txt_btnSolicitud;
    TextView txt_btnCodigo;
    TextView txt_btnAvisar;
    private Runnable runnable;
    private Handler handler;
    private Gson gson;
    private ResultadoAgregarCoordenadasTaxista resultadoAgregarCoordenadasTaxista;
    private ResultadoObtenerSolicitudes resultadoObtenerSolicitudes;
    private ResultadoVerificarTodo resultadoVerificarTodo;
    private ResultadoNotificacion resultadoNotificacion;

    double latitude;
    double longitude;

    Location locationf;

    private ResultadoConsultarReferencia resultadoConsultarReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.gson = new Gson();
        this.resultadoVerificarTodo = new ResultadoVerificarTodo();


        final Preferencias preferencias = new Preferencias(getApplicationContext());
        String Cabbie_Id = preferencias.getCabbie_Id();
        boolean check = preferencias.getSesion();

        if (check) {
            Intent intent2 = new Intent(Main.this, Login.class);
            startActivity(intent2);
            finish();
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mDrawerMenu = (DrawerMenu) getSupportFragmentManager().findFragmentById(R.id.left_drawer);
            mDrawerMenu.setUp(R.id.left_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar, getSupportActionBar(), this);


            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener();
            if (ActivityCompat.checkSelfPermission(Main.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) locationListener);
            }




            this.handler = new Handler();
            this.runnable = new Runnable() {
                public void run() {
                    mandarCoordenadasAlServidor();
                    handler.postDelayed(runnable, 10000);
                }
            };

            handler.post(runnable);


            Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");


            btn_en_proceso = (LinearLayout) findViewById(R.id.btn_en_proceso);
            txt_btnSolicitud = (TextView) findViewById(R.id.txt_btnSolicitud);
            txt_btnSolicitud.setTypeface(RobotoCondensed_Regular);

            btn_avisar = (LinearLayout) findViewById(R.id.btn_avisar);
            txt_btnAvisar = (TextView) findViewById(R.id.txt_btnAvisar);
            txt_btnAvisar.setTypeface(RobotoCondensed_Regular);

            btn_qrcode = (LinearLayout) findViewById(R.id.btn_qrcode);
            txt_btnCodigo = (TextView) findViewById(R.id.txt_btnCodigo);
            txt_btnCodigo.setTypeface(RobotoCondensed_Regular);

            linear_btn = (LinearLayout) findViewById(R.id.linear_btn);

            txt_pendientes = (TextView) findViewById(R.id.text_pendientes);
            txt_pendientes.setTypeface(RobotoCondensed_Regular);


            SolicitudObtenerSolicitudes oData = new SolicitudObtenerSolicitudes();
            oData.setCabbie_Id(Cabbie_Id);
            GetRequestWebService(gson.toJson(oData));

            //VerifyAll(Cabbie_Id);

            btn_avisar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SolicitudNotificacion oDataN = new SolicitudNotificacion();
                    String Gcm_Id = preferencias.getGcm_Id();
                    oDataN.setGcm_Id(Gcm_Id);
                    oDataN.setMessage("Tu taxista ha llegado por ti.");
                    oDataN.setType("C");
                    PushNotificationWebService(gson.toJson(oDataN));

                    SweetAlertDialog dialog = new SweetAlertDialog(Main.this, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitleText("Mensaje enviado!").show();
                }

            });




            btn_qrcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator scanIntegrator = new IntentIntegrator(Main.this);
                    System.out.println("**** iniciamos el escaneo");
                    scanIntegrator.initiateScan();
                }
            });


            btn_en_proceso.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent i = new Intent(Main.this, On_Process.class);
                    startActivityForResult(i, 312);
                }
            });
        }

    }

    public class MyCurrentLoctionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            locationf = location;
            location.getLatitude();
            location.getLongitude();

            //String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

            //I make a log to see the results
            //Log.e("MY CURRENT LOCATION", myLocation);


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }








    public void mandarCoordenadasAlServidor() {

            Preferencias preferencias = new Preferencias(getApplicationContext());
            String cabbie_id = preferencias.getCabbie_Id();

           // Toast.makeText(Main.this, "Lat:"+latitude + " Long:"+longitude, Toast.LENGTH_SHORT).show();



        if (locationf != null)
        {

            Log.d("MYGPSLL", "Lat:" + locationf.getLatitude() + ", Long:"+locationf.getLongitude());


            SolicitudAgregarCoordenadasTaxista oData = new SolicitudAgregarCoordenadasTaxista();
            oData.setCabbie_Id(cabbie_id);
            oData.setLatitude(String.valueOf(locationf.getLatitude()));
            oData.setLongitude(String.valueOf(locationf.getLongitude()));
            SetCoordinatesCabbieWebService(gson.toJson(oData));
        }


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
                            Toast.makeText(getApplicationContext(), "Avisado con exito", Toast.LENGTH_LONG).show();
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





    private void GetRequestWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Get_RequestWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    listrequestArray.clear();
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoObtenerSolicitudes = gson.fromJson(result.get("Resultado").toString(), ResultadoObtenerSolicitudes.class);
                        if (!resultadoObtenerSolicitudes.isError() && resultadoObtenerSolicitudes.getData() != null)
                        {
                            ArrayList<Solicitud> solicitudes = resultadoObtenerSolicitudes.getData();
                            client_id = new int[solicitudes.size()];
                            request_id = new int[solicitudes.size()];
                            gcm_id = new String[solicitudes.size()];

                            for (int i=0; i < solicitudes.size(); i++){
                                FechasBD fb = new FechasBD();
                                String fecha = fb.ObtenerFecha(solicitudes.get(i).getDate());
                                String hora = fb.ObtenerHora(solicitudes.get(i).getDate());
                                listrequestArray.add(new ListRequest(fecha + " a las " + hora));
                                client_id[i] = Integer.parseInt(solicitudes.get(i).getClient_Id());
                                request_id[i] = Integer.parseInt(solicitudes.get(i).getRequest_Id());
                                gcm_id[i] = solicitudes.get(i).getGcm_Id();
                            }
                        }
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
                listrequestList = (ListView)findViewById(R.id.list_solicitudes);
                listRequestAdapter = new ListRequestCustomAdapter(getApplicationContext(), R.layout.row_request, listrequestArray);
                listrequestList.setItemsCanFocus(false);
                listrequestList.setAdapter(listRequestAdapter);

                SolicitudVerificarTodo oData = new SolicitudVerificarTodo();
                Preferencias preferencias = new Preferencias(getApplicationContext());
                String Cabbie_Id = preferencias.getCabbie_Id();
                oData.setCabbie_Id(Cabbie_Id);
                VerifyAllVWebService(gson.toJson(oData));

            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }


    private void VerifyAllVWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Verify_AllWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoVerificarTodo = gson.fromJson(result.get("Resultado").toString(), ResultadoVerificarTodo.class);
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
                if (!resultadoVerificarTodo.isError()) {
                    if (listrequestArray.size() == 0)
                    {
                        txt_pendientes.setVisibility(View.GONE);
                        linear_btn.setVisibility(View.VISIBLE);
                        /*btn_en_proceso.setVisibility(View.VISIBLE);
                        btn_avisar.setVisibility(View.VISIBLE);
                        btn_qrcode.setVisibility(View.VISIBLE);*/
                        listrequestList.setVisibility(View.GONE);
                    }
                    else{
                        txt_pendientes.setVisibility(View.GONE);
                        btn_en_proceso.setVisibility(View.GONE);
                        btn_avisar.setVisibility(View.GONE);
                        btn_qrcode.setVisibility(View.GONE);
                        listrequestList.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    txt_pendientes.setVisibility(View.VISIBLE);
                    btn_en_proceso.setVisibility(View.GONE);
                    btn_avisar.setVisibility(View.GONE);
                    btn_qrcode.setVisibility(View.GONE);
                    listrequestList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 312:
                if (resultCode == Activity.RESULT_OK) {
                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    String Cabbie_Id = preferencias.getCabbie_Id();
                    SolicitudObtenerSolicitudes oData = new SolicitudObtenerSolicitudes();
                    oData.setCabbie_Id(Cabbie_Id);
                    GetRequestWebService(gson.toJson(oData));

                }
                break;

            case 49374:
                IntentResult resultadoScan = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (data == null)
                {
                    break;
                }
                else
                {
                    if (resultadoScan != null) {
                        System.out.println("**** Tenemos un resultado !");
                        String scanContenido = resultadoScan.getContents();
                        String scanFormato = resultadoScan.getFormatName();

                        SolicitudConsultarReferencia oData = new SolicitudConsultarReferencia();
                        oData.setReferencia(scanContenido);
                        ConsultarReferencia(gson.toJson(oData));
                    }
                    else
                    {
                        System.out.println("**** NO Tenemos un resultado !");
                        //formatoTxt.setText("FORMATO: Sin resultado");
                        //contenidoTxt.setText("CONTENIDO: Sin resultado ");
                    }
                }
                break;

        }
    }

    @Override
    public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            latitude=loc.getLatitude();
            longitude=loc.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class ListRequestCustomAdapter extends ArrayAdapter<ListRequest> {
        Context context;
        int layoutResourceId;
        ArrayList<ListRequest> data = new ArrayList<ListRequest>();

        public ListRequestCustomAdapter(Context context, int layoutResourceId, ArrayList<ListRequest> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.textName = (TextView) row.findViewById(R.id.textView1);
                //holder.btnView = (ImageButton) row.findViewById(R.id.button1);
                row.setTag(holder);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), View_Request.class);
                        i.putExtra("Client_Id", String.valueOf(client_id[position]));
                        i.putExtra("Request_Id", String.valueOf(request_id[position]));
                        i.putExtra("Gcm_Id", String.valueOf(gcm_id[position]));
                        i.putExtra("List_Client", client_id);
                        i.putExtra("List_Request", request_id);
                        i.putExtra("List_gcm_id", gcm_id);
                        startActivityForResult(i, 312);
                    }
                });
            } else {
                holder = (UserHolder) row.getTag();
            }

            ListRequest listRequest = data.get(position);


            holder.textName.setText(listRequest.getList_Request());

            /*holder.btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), View_Request.class);
                    i.putExtra("Client_Id", String.valueOf(client_id[position]));
                    i.putExtra("Request_Id", String.valueOf(request_id[position]));
                    i.putExtra("List_Client", client_id);
                    i.putExtra("List_Request", request_id);
                    i.putExtra("List_gcm_id", gcm_id);
                    startActivity(i);

                }
            });*/
            return row;

        }



        class UserHolder {
            TextView textName;
            //ImageButton btnView;
        }
    }

    private void ConsultarReferencia(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.ConsultarReferencia, rawJson);
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
                        resultadoConsultarReferencia = gson.fromJson(result.get("Resultado").toString(), ResultadoConsultarReferencia.class);

                        if (resultadoConsultarReferencia.isError()) {
                            Toast.makeText(getApplicationContext(), resultadoConsultarReferencia.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            String Cabbie = resultadoConsultarReferencia.getData().get(0).getCabbie_Id();
                            String Client = resultadoConsultarReferencia.getData().get(0).getClient_Id();
                            String status = resultadoConsultarReferencia.getData().get(0).getStatus();

                            Preferencias preferencias = new Preferencias(getApplicationContext());
                            String Cabbie_Id = preferencias.getCabbie_Id();
                            String Client_Id = preferencias.getClient_Id();

                            if ((Cabbie.equals(Cabbie_Id)) && (Client.equals(Client_Id)) && (status.equals("1"))){
                                Toast.makeText(getApplicationContext(), "Todo Correcto", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Los datos no coinciden", Toast.LENGTH_LONG).show();
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





    /*public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class MyLocationListener implements LocationListener {
        Main mainActivity;

        public Main getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(Main mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {

            latitude = loc.getLatitude();
            longitude = loc.getLongitude();

            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }*/

    private void SetCoordinatesCabbieWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Set_Coordinates_CabbieWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoAgregarCoordenadasTaxista = gson.fromJson(result.get("Resultado").toString(), ResultadoAgregarCoordenadasTaxista.class);
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
                if (resultadoAgregarCoordenadasTaxista.isError()) {
                    Toast.makeText(getApplicationContext(), resultadoAgregarCoordenadasTaxista.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }
}
