package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;

/**
 * Created by danixsanc on 09/01/2016.
 */
public class View_Request extends AppCompatActivity {

    int client_id = 0;
    int request_id = 0;
    TextView txt_id;
    Button btn_aceptar;
    int  client_list[];
    int  request_list[];
    String  gcm_id_list[];
    Servicios servicios = new Servicios();
    private static String KEY_SUCCESS = "Success";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_request);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (TextView) findViewById(R.id.txt_id);
        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);

        Preferencias preferencias = new Preferencias(this);
        final String Cabbie_Id = preferencias.getCabbie_Id();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            client_id = Integer.valueOf(bundle.getString("Client_Id"));
            request_id = Integer.valueOf(bundle.getString("Request_Id"));

            client_list = bundle.getIntArray("List_Client");
            request_list = bundle.getIntArray("List_Request");
            gcm_id_list = bundle.getStringArray("List_gcm_id");
        }

        txt_id.setText("id: " + client_id);


        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                servicios.acceptRequest(String.valueOf(request_id), String.valueOf(client_id), Cabbie_Id);
                //Metodo para agregar latitud y longitud a las preferencias

                for (int i = 0; i<client_list.length; i++)
                {
                    if (client_id != client_list[i])
                    {
                        servicios.refuseRequests(String.valueOf(request_list[i]), String.valueOf(client_list[i]), Cabbie_Id);
                        servicios.sendNotificationRefuse(gcm_id_list[i]);
                    }
                    else if (client_id == client_list[i])
                    {
                        servicios.sendNotificationAccept(gcm_id_list[i]);
                    }
                }
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
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
