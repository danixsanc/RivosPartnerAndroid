package com.yozzibeens.rivostaxipartner.actividades;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.modelosApp.Solicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoFinalizarSolicitud;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoObtenerSolicitudEnProceso;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoObtenerSolicitudes;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudFinalizarSolicitud;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudObtenerSolicitudEnProceso;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudVerificarTodo;
import com.yozzibeens.rivostaxipartner.utilerias.ListRequest;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by danixsanc on 10/01/2016.
 */


public class On_Process extends AppCompatActivity implements OnMapReadyCallback {


    private static String KEY_SUCCESS = "Success";

    Button bnt_finalizar;
    String Client_Id;
    String Longitude_Client;
    String Latitude_Client;
    double latitude;
    double longitude;

    private GoogleMap mapa;

    private Gson gson;
    private ResultadoObtenerSolicitudEnProceso resultadoObtenerSolicitudEnProceso;
    private ResultadoFinalizarSolicitud resultadoFinalizarSolicitud;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_process);

        MapsInitializer.initialize(getApplicationContext());
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.gson = new Gson();
        onMapReady(mapa);



        Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");

        bnt_finalizar = (Button) findViewById(R.id.finalizar);
        bnt_finalizar.setTypeface(RobotoCondensed_Regular);
        bnt_finalizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(getApplicationContext());
                String Cabbie_Id = preferencias.getCabbie_Id();


                if (((Math.abs(Double.valueOf(Longitude_Client) - Double.valueOf(longitude)) < 0.025))
                        && (Math.abs(Double.valueOf(Latitude_Client) - Double.valueOf(latitude)) < 0.025)) {

                    SolicitudFinalizarSolicitud oData = new SolicitudFinalizarSolicitud();
                    oData.setCabbie_Id(Cabbie_Id);
                    FinalizarWebService(gson.toJson(oData));

                } else {
                    Snackbar.make(view, "No se puede finalizar aun...", Snackbar.LENGTH_LONG).show();
                }


            }
        });







    }


    private void GetRequestOnProccessWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Get_Request_On_ProcessWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoObtenerSolicitudEnProceso = gson.fromJson(result.get("Resultado").toString(), ResultadoObtenerSolicitudEnProceso.class);
                    }
                } catch (Exception error) {

                }
            }

            @Override
            public void onTaskUpdate(String result) {
            }

            @Override
            public void onTaskComplete(HashMap<String, Object> result) {
                if (!resultadoObtenerSolicitudEnProceso.isError() && resultadoObtenerSolicitudEnProceso.getData() != null) {
                    ArrayList<Solicitud> solicitudes = resultadoObtenerSolicitudEnProceso.getData();
                    Client_Id = solicitudes.get(0).getClient_Id();
                    Latitude_Client = solicitudes.get(0).getLatitude_Fn();
                    Longitude_Client = solicitudes.get(0).getLongitude_Fn();
                    double latitudeInicio = Double.valueOf(solicitudes.get(0).getLatitude_In());
                    double longitudInicio = Double.valueOf(solicitudes.get(0).getLongitude_In());

                    MarkerOptions marker3 = new MarkerOptions()
                            .position(new LatLng(latitudeInicio, longitudInicio))
                            .title("Inicio").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    mapa.addMarker(marker3);

                    MarkerOptions marker2 = new MarkerOptions()
                            .position(new LatLng(Double.valueOf(Latitude_Client), Double.valueOf(Longitude_Client)))
                            .title("Destino").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    mapa.addMarker(marker2);
                }

            }

            @Override
            public void onTaskCancelled(HashMap<String, Object> result) {
            }
        });
        servicioAsyncService.execute();
    }

    private void FinalizarWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.Verify_FinalizeWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoFinalizarSolicitud = gson.fromJson(result.get("Resultado").toString(), ResultadoFinalizarSolicitud.class);
                    }
                } catch (Exception error) {

                }
            }

            @Override
            public void onTaskUpdate(String result) {
            }

            @Override
            public void onTaskComplete(HashMap<String, Object> result) {
                if (!resultadoFinalizarSolicitud.isError()) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
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


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(false);
            mapa.getUiSettings().setCompassEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            if ((latitude == 0) && (longitude == 0)) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(On_Process.this);
                dialog1.setMessage("El GPS esta desactivado, ¿Desea Activarlo?");
                dialog1.setCancelable(false);
                dialog1.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                dialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog1.show();
            } else {
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aqui Me Encuentro");
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                mapa.addMarker(marker);


                //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 15));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(16).build();
                mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }



        }

        Preferencias preferencias = new Preferencias(getApplicationContext());
        String Cabbie_Id = preferencias.getCabbie_Id();

        SolicitudObtenerSolicitudEnProceso oData = new SolicitudObtenerSolicitudEnProceso();
        oData.setCabbie_Id(Cabbie_Id);
        GetRequestOnProccessWebService(gson.toJson(oData));




    }
}