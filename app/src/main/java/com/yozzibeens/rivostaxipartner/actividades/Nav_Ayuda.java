package com.yozzibeens.rivostaxipartner.actividades;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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

import com.yozzibeens.rivostaxipartner.R;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_ayuda);

        final Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                final ProgressDialog dialog = ProgressDialog.show(Nav_Ayuda.this, "", "Loading..Wait..", true);
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        input_asunto = (EditText) findViewById(R.id.etxt_asunto);
                        input_asunto.setTypeface(RobotoCondensed_Regular);
                        asunto = input_asunto.getText().toString();
                        input_mensaje = (EditText) findViewById(R.id.etxt_mensaje);
                        input_mensaje.setTypeface(RobotoCondensed_Regular);
                        mensaje = input_mensaje.getText().toString();

                        if ((asunto.length() > 0) && (mensaje.length() > 0)) {
                            //SendMsg(asunto, mensaje);
                            input_asunto.setText("");
                            input_mensaje.setText("");
                        } else {
                            //Toast.makeText(this, "Hay Campos En Blanco", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                }, 2000);

            }
        });


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
