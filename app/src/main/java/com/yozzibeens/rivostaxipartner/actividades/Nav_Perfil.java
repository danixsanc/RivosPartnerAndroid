package com.yozzibeens.rivostaxipartner.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.controlador.CabbieController;
import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;


/**
 * Created by danixsanc on 12/01/2016.
 */
public class Nav_Perfil extends AppCompatActivity {

    TextView txt_phone_user;
    TextView txt_email_user;
    TextView txt_nombre_user;
    Button btn_modifydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_perfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_phone_user = (TextView) findViewById(R.id.txt_phone_user);
        txt_email_user = (TextView) findViewById(R.id.txt_email_user);
        txt_nombre_user = (TextView) findViewById(R.id.txt_nombre_user);

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
