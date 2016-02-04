package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danixsanc on 10/01/2016.
 */



public class On_Process extends AppCompatActivity {


    private static String KEY_SUCCESS = "Success";

    Button bnt_finalizar;
    String Client_Id;
    String Longitude_Client;
    String Latitude_Client;
    double latitude;
    double longitude;

    private GoogleMap mapa;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_process);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(false);
        mapa.getUiSettings().setCompassEnabled(true);


















        final Servicios servicios = new Servicios();
        Preferencias preferencias = new Preferencias(getApplicationContext());
        String Cabbie_Id = preferencias.getCabbie_Id();
        JSONObject json = servicios.getRequestOnProcess(Cabbie_Id);
        try {

            if (json.getString(KEY_SUCCESS) != null) {
                String res = json.getString(KEY_SUCCESS);
                if (Integer.parseInt(res) == 1)
                {
                    JSONObject json_user = json.getJSONObject("Request1");
                    Client_Id = json_user.getString("Client_Id");
                    Latitude_Client = json_user.getString("Latitude_Fn");
                    Longitude_Client= json_user.getString("Longitude_Fn");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bnt_finalizar = (Button) findViewById(R.id.finalizar);
        bnt_finalizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(getApplicationContext());
                String Cabbie_Id = preferencias.getCabbie_Id();
                JSONObject json = servicios.getCabbieCoordinates(Cabbie_Id);
                try {

                    if (json.getString(KEY_SUCCESS) != null) {
                        String res = json.getString(KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1) {
                            String latitude = json.getString("Latitude");
                            String longitude = json.getString("Longitude");

                            double var1 = Math.abs(Double.valueOf(Longitude_Client) - Double.valueOf(longitude));
                            double var2 = Math.abs(Double.valueOf(Latitude_Client) - Double.valueOf(latitude));

                            if (((Math.abs(Double.valueOf(Longitude_Client) - Double.valueOf(longitude)) < 0.005))
                                    && (Math.abs(Double.valueOf(Latitude_Client) - Double.valueOf(latitude)) < 0.005)) {
                                servicios.finalizeRequest(Cabbie_Id);
                                finish();
                            } else {
                                Snackbar.make(view, "No se puede finalizar aun...", Snackbar.LENGTH_LONG).show();
                            }


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



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