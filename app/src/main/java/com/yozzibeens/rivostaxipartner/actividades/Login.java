package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yozzibeens.rivostaxipartner.Main;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.controlador.CabbieController;
import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


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

    private static String KEY_SUCCESS = "Success";
    private static String KEY_ERROR = "Error";
    private static String KEY_ERROR_MSG = "Error_msg";
    private static String KEY_CABBIE_ID = "Cabbie_Id";
    private static String KEY_NAME = "Name";
    private static String KEY_PHONE = "Phone";
    private static String KEY_EMAIL = "Email";
    private static String KEY_CREATED_AT = "Created_At";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //btn_faqs_login = (Button) findViewById(R.id.btn_faqs_login);
        //mButtonLogout = (Button) findViewById(R.id.salir);
        //mTextStatus = (TextView) findViewById(R.id.mostrar);

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
        //check_terminos = (CheckBox) findViewById(R.id.check_terminos);

        /*btn_faqs_login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),faqs_login.class);
                startActivity(i);
            }
        });*/

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if (exiteConexionInternet()){
                    dialog = ProgressDialog.show(Login.this, "","Loading..Wait.." , true);
                    dialog.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            String email = inputEmail.getText().toString();
                            String password = inputPassword.getText().toString();

                            Servicios servicios = new Servicios();
                            Log.d("Button", "Login");
                            try {
                                JSONObject json = servicios.loginUser(email, password);
                                if (json.getString(KEY_SUCCESS) != null) {
                                    loginErrorMsg.setText("");
                                    String res = json.getString(KEY_SUCCESS);
                                    if (Integer.parseInt(res) == 1) {
                                       // DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                        JSONObject json_user = json.getJSONObject("User");

                                        servicios.logoutUser(getApplicationContext());

                                        Cabbie cabbie = new Cabbie(null, json_user.getString(KEY_CABBIE_ID),
                                                json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL),
                                                json_user.getString(KEY_PHONE));

                                        CabbieController cabbieController = new CabbieController(getApplicationContext());
                                        cabbieController.guardarCabbie(cabbie);

                                        Preferencias preferencias = new Preferencias(getApplicationContext());
                                        preferencias.setCabbie_Id(cabbie.getCabbie_Id());
                                        preferencias.setSesion(false);

                                        Intent main = new Intent(getApplicationContext(), Main.class);
                                        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(main);
                                        finish();
                                        dialog.dismiss();
                                    } else {
                                        loginErrorMsg.setText("Usuario/Contraseña Incorrecto(a)");
                                    }
                                }else {
                                    loginErrorMsg.setText("Error...");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }, 3000);  // 3000 milliseconds
                }else{
                    Toast.makeText(Login.this, "Verifica Tu Conexion a Internet", Toast.LENGTH_LONG).show();
                }
            }


        });



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
