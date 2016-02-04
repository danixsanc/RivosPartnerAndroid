package com.yozzibeens.rivostaxipartner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yozzibeens.rivostaxipartner.actividades.Login;
import com.yozzibeens.rivostaxipartner.actividades.On_Process;
import com.yozzibeens.rivostaxipartner.fragmentos.DrawerMenu;
import com.yozzibeens.rivostaxipartner.utilerias.ListRequest;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;
import com.yozzibeens.rivostaxipartner.utilerias.Servicios;
import com.yozzibeens.rivostaxipartner.actividades.View_Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    private DrawerMenu mDrawerMenu;

    String link = "http://appm.rivosservices.com/";
    ListView listView ;
    //UserFunctions userFunctions = new UserFunctions();
    private static String KEY_SUCCESS = "Success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_APE = "ape";
    private static String KEY_USERNAME = "username";
    private static String KEY_PHONE = "phone";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    private LocationManager locManager;
    private LocationListener locListener;

    private SwipeRefreshLayout swipeRefreshLayout;

    double lat;
    double lon;

    ListView listrequestList;
    ListRequestCustomAdapter listRequestAdapter;
    ArrayList<ListRequest> listrequestArray = new ArrayList<ListRequest>();
    Servicios servicios = new Servicios();
    int client_id[] = new int[0];
    int request_id[] = new int[0];
    String gcm_id[] = new String[0];
    Button btn_en_proceso;
    TextView txt_pendientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        Preferencias preferencias = new Preferencias(getApplicationContext());
        String Cabbie_Id = preferencias.getCabbie_Id();
        boolean check = preferencias.getSesion();

        if (check){
            Intent intent2 = new Intent(Main.this, Login.class);
            startActivity(intent2);
            finish();
        }
        else
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mDrawerMenu = (DrawerMenu) getSupportFragmentManager().findFragmentById(R.id.left_drawer);
            mDrawerMenu.setUp(R.id.left_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar, getSupportActionBar(), this);





            btn_en_proceso = (Button) findViewById(R.id.btn_en_proceso);
            txt_pendientes = (TextView) findViewById(R.id.text_pendientes);



            listrequestList = (ListView)findViewById(R.id.list_solicitudes);
            //listrequestArray.add(new ListRequest("Date"));


            CargarLista(Cabbie_Id);
            VerifyAll(Cabbie_Id);


            btn_en_proceso.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent i = new Intent(Main.this, On_Process.class);
                    startActivity(i);
                }
            });


            listRequestAdapter = new ListRequestCustomAdapter(this, R.layout.row_request, listrequestArray);
            listrequestList.setItemsCanFocus(false);
            listrequestList.setAdapter(listRequestAdapter);
        }



    }

    public void CargarLista(String Cabbie_Id){

        try {
            JSONObject json = servicios.getRequest(Cabbie_Id);
            if (json.getString(KEY_SUCCESS) != null) {
                String res = json.getString(KEY_SUCCESS);
                if (Integer.parseInt(res) == 1)
                {

                    int val = json.getInt("num");

                    client_id = new int[val];
                    request_id = new int[val];
                    gcm_id = new String[val];


                    for (int i = 0; i < val; i++)
                    {
                        JSONObject json_user = json.getJSONObject("Request"+(i+1));
                        listrequestArray.add(new ListRequest(json_user.getString("Date")));
                        client_id[i] = Integer.parseInt(json_user.getString("Client_Id"));
                        request_id[i] = Integer.parseInt(json_user.getString("Request_Id"));
                        gcm_id[i] = json_user.getString("gcm_id");
                    }
                }
                else
                {
                    listrequestArray.clear();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void VerifyAll(String Cabbie_Id)
    {
        JSONObject json2 = servicios.verifyAll(Cabbie_Id);
        try {

            if (json2.getString(KEY_SUCCESS) != null) {
                String res = json2.getString(KEY_SUCCESS);
                if (Integer.parseInt(res) == 0)
                {
                    txt_pendientes.setVisibility(View.VISIBLE);
                }
                else{
                    if (listrequestArray.size() == 0)
                    {
                        btn_en_proceso.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 312:
                if (resultCode == Activity.RESULT_OK) {
                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    String Cabbie_Id = preferencias.getCabbie_Id();
                    CargarLista(Cabbie_Id);
                    VerifyAll(Cabbie_Id);
                }
                break;

        }
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
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
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
}
