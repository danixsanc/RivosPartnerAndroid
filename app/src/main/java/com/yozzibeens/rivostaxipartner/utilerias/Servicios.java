package com.yozzibeens.rivostaxipartner.utilerias;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class Servicios {

    private JSONParser jsonParser;

    private static String loginURL = "http://appm.rivosservices.com/index_c.php";
    private static String notfcURL = "http://appm.rivosservices.com/push.php";

    private static String login_tag = "Login";
    private static String loginf_tag = "loginf";
    private static String update_tag = "update";
    private static String register_gcmId = "Register_GcmId";
    private static String get_request = "Get_Request";
    private static String verify_all = "Verify_All";
    private static String verify_finalize = "Verify_Finalize";
    private static String get_request_on_process = "Get_Request_On_Process";
    private static String get_cabbie_coordinates = "Get_Cabbie_Coordinates";
    private static String accept_request = "Accept_Request";
    private static String refuse_request = "Refuse_Request";
    private static String set_coordinates_cabbie = "Set_Coordinates_Cabbie";
    private static String register_tag = "register";
    private static String gettaxistacercano_tag = "gettaxistacercano";
    public static String umail;
    private static String uname;
    public static String uuser;

    public Servicios()
    {
        jsonParser = new JSONParser();
    }

    public JSONObject loginUser(String email, String password) throws IOException, JSONException {

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", login_tag);
            params.put("Email", email);
            params.put("Password", password);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;



    }

    public JSONObject getTaxistaCercano()
    {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("tag", gettaxistacercano_tag);


            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;





    }

    public JSONObject loginUser2(String email)
    {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("tag", update_tag);
            params.put("email", email);


            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public JSONObject sendNotificationRefuse(String reg_Id) {

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("push", "1");
            params.put("message", "tu solicitud ha cambiado de taxista, click para ver.");
            params.put("reg_id", reg_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(notfcURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject sendNotificationAccept(String reg_Id) {

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("push", "1");
            params.put("message", "tu solicitud ha sido aceptara por el taxista, click para ver.");
            params.put("reg_id", reg_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(notfcURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject registerUser(String name,String ape, String username, String phone, String email, String password)
    {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("tag", register_tag);
            params.put("name", name);
            params.put("ape", ape);
            params.put("username", username);
            params.put("phone", phone);
            params.put("email", email);
            params.put("password", password);


            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;



    }

    public JSONObject updateUser(String name,String ape, String username, String phone, String email, String password)
    {


        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("tag", update_tag);
            params.put("name", name);
            params.put("ape", ape);
            params.put("username", username);
            params.put("phone", phone);
            params.put("email", email);
            params.put("password", password);


            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getRequestOnProcess(String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", get_request_on_process);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject verifyAll(String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", verify_all);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject Register_GcmId(String gcm_id, String Cabbie_Id)
    {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", register_gcmId);
            params.put("GcmId", gcm_id);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);


            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject finalizeRequest (String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", verify_finalize);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getCabbieCoordinates(String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", get_cabbie_coordinates);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getRequest(String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", get_request);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject acceptRequest (String Request_Id, String Client_Id, String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", accept_request);
            params.put("Request_Id", Request_Id);
            params.put("Client_Id", Client_Id);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject refuseRequests (String Request_Id, String Client_Id, String Cabbie_Id){

        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", refuse_request);
            params.put("Request_Id", Request_Id);
            params.put("Client_Id", Client_Id);
            params.put("Cabbie_Id", Cabbie_Id);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public JSONObject set_coordinates_cabbie(String Latitude, String Longitude, String Cabbie_Id)
    {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("Tag", set_coordinates_cabbie);
            params.put("Cabbie_Id", Cabbie_Id);
            params.put("Latitude", Latitude);
            params.put("Longitude", Longitude);

            Log.d("request", "starting");
            JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

            if (json != null)
            {
                Log.d("JSON result", json.toString());
                return json;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getumail()
    {
        return umail;
    }

    public boolean isUserLoggedIn(Context context)
    {
        /*DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0)
        {
            return true;
        }*/
        return false;
    }

    public boolean logoutUser(Context context)
    {
        /*DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();*/
        return true;
    }

}
