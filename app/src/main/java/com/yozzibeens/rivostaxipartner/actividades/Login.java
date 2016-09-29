package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yozzibeens.rivostaxipartner.Main;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.controlador.CabbieController;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoLogin;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudLogin;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;

import java.io.IOException;
import java.util.HashMap;


public class Login extends Activity {

    private Button btnLogin;
    private Button btnOlvidePass;
    private MaterialEditText inputEmail;
    private MaterialEditText inputPassword;
    private TextView loginErrorMsg;
    private ProgressDialog progressdialog;
    private Gson gson;
    private ResultadoLogin resultadoLogin;
    private CabbieController cabbieController;
    private Typeface RobotoCondensed_Regular;
    private String regId;
    private Context context;
    private GoogleCloudMessaging gcm;
    private static final String REG_ID = "regId";
    private static final String TAG = "Register Activity";

    /*hola*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");
        this.gson = new Gson();
        this.cabbieController = new CabbieController(this);
        this.inputEmail = (MaterialEditText) findViewById(R.id.loginEmail);
        this.inputEmail.setTypeface(RobotoCondensed_Regular);
        this.inputPassword = (MaterialEditText) findViewById(R.id.loginPassword);
        this.inputPassword.setTypeface(RobotoCondensed_Regular);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnLogin.setTypeface(RobotoCondensed_Regular);
        this.btnOlvidePass = (Button) findViewById(R.id.btnOlvidePass);
        this.btnOlvidePass.setTypeface(RobotoCondensed_Regular);
        this.loginErrorMsg = (TextView) findViewById(R.id.login_error);
        this.loginErrorMsg.setTypeface(RobotoCondensed_Regular);

        this.context = getApplicationContext();
        this.regId = registerGCM();

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                SolicitudLogin oData = new SolicitudLogin();
                oData.setEmail(email);
                oData.setPassword(password);
                oData.setGcm_Id(regId);
                oData.setUser_Type("1");
                LoginWebService(gson.toJson(oData));
            }


        });
    }

    private void LoginWebService(String rawJson) {
        ServicioAsyncService servicioAsyncService = new ServicioAsyncService(this, WebService.LoginWebService, rawJson);
        servicioAsyncService.setOnCompleteListener(new AsyncTaskListener() {
            @Override
            public void onTaskStart() {
                progressdialog = new ProgressDialog(Login.this);
                progressdialog.setMessage("Iniciando, espere");
                progressdialog.setCancelable(true);
                progressdialog.setCanceledOnTouchOutside(false);
                progressdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        progressdialog.dismiss();
                    }
                });
                progressdialog.show();
            }

            @Override
            public void onTaskDownloadedFinished(HashMap<String, Object> result) {
                try {
                    int statusCode = Integer.parseInt(result.get("StatusCode").toString());
                    if (statusCode == 0) {
                        resultadoLogin = gson.fromJson(result.get("Resultado").toString(), ResultadoLogin.class);
                    }
                }
                catch (Exception error) {
                    String messageError = "Ocurrio un error inesperado";
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this, R.style.AlertDialogStyle);
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
            public void onTaskUpdate(String result) {

            }

            @Override
            public void onTaskComplete(HashMap<String, Object> result) {
                progressdialog.dismiss();
                if ((!resultadoLogin.isError()) && resultadoLogin.getData() != null) {
                    cabbieController.eliminarTodo();
                    cabbieController.guardarOActualizarCabbie(resultadoLogin.getData());

                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    String clientId = resultadoLogin.getData().getCabbie_Id();
                    preferencias.setCabbie_Id(clientId);
                    preferencias.setSesion(false);

                    Intent main = new Intent(getApplicationContext(), Main.class);
                    startActivity(main);
                    finish();
                }
                else if (resultadoLogin.isError())
                {
                    String messageError = resultadoLogin.getMessage();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this, R.style.AlertDialogStyle);
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
                progressdialog.dismiss();
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

                    saveRegisterId(context, regId);
                } catch (IOException ex) {
                    handler.sendEmptyMessage(1);
                    Log.e(TAG, ex.getMessage(), ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    private void saveRegisterId(Context context, String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                Splash.class.getSimpleName(), Context.MODE_PRIVATE);
        Log.i(TAG, "Saving regId on app version ");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.commit();
    }
}
