package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.Main;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.controlador.CabbieController;
import com.yozzibeens.rivostaxipartner.listener.AsyncTaskListener;
import com.yozzibeens.rivostaxipartner.listener.ServicioAsyncService;
import com.yozzibeens.rivostaxipartner.respuesta.ResultadoLogin;
import com.yozzibeens.rivostaxipartner.servicios.WebService;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudLogin;
import com.yozzibeens.rivostaxipartner.solicitud.SolicitudLogin2;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class Login extends Activity {

    protected static final String TAG ="";
    private Button mButtonLogin;
    private Button mButtonLogout;
    private TextView mTextStatus;
    private int NOTIF_ALERTA_ID = 0;


    Button btnLogin;
    Button btnOlvidePass;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    CheckBox check_terminos;
    ProgressDialog dialog;

    private ProgressDialog progressdialog;
    private Gson gson;
    private ResultadoLogin resultadoLogin;
    private CabbieController cabbieController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");


        this.gson = new Gson();
        cabbieController = new CabbieController(this);



        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputEmail.setTypeface(RobotoCondensed_Regular);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        inputPassword.setTypeface(RobotoCondensed_Regular);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTypeface(RobotoCondensed_Regular);
        btnOlvidePass = (Button) findViewById(R.id.btnOlvidePass);
        btnOlvidePass.setTypeface(RobotoCondensed_Regular);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        loginErrorMsg.setTypeface(RobotoCondensed_Regular);


        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                SolicitudLogin2 oUsuario = new SolicitudLogin2();
                oUsuario.setEmail(email);
                oUsuario.setPassword(password);
                LoginWebService(gson.toJson(oUsuario));
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
                        if ((!resultadoLogin.isError()) && resultadoLogin.getData() != null) {
                            cabbieController.eliminarTodo();
                            cabbieController.guardarOActualizarCabbie(resultadoLogin.getData());

                            Preferencias preferencias = new Preferencias(getApplicationContext());
                            String clientId = resultadoLogin.getData().get(0).getCabbie_Id();
                            preferencias.setCabbie_Id(clientId);
                            preferencias.setSesion(false);

                            Intent main = new Intent(getApplicationContext(), Main.class);
                            startActivity(main);
                            finish();
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
                progressdialog.dismiss();
                if (resultadoLogin.isError())
                {
                    String messageError = resultadoLogin.getMessage();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
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

    public boolean exiteConexionInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void resetpass (View view) {goToUrl ("http://appm.rivosservices.com/reset_pass.php");}

    public void faqs (View view) { goToUrl("http://appm.rivosservices.com/faqs.html");}

    public void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
