package com.yozzibeens.rivostaxipartner.utilerias;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by danixsanc on 27/09/2015.
 */
public class Preferencias {

    private final String SHARED_PREFS_FILE = "HMPrefs";
    private final String TUTORIAL = "tutorial";
    private final String SESION = "sesion";
    private final String CABBIEID = "cabbieid";

    public int s;
    private Context mContext;

    public Preferencias(Context context){
        mContext = context;
    }

    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public void setTutorial(boolean prSave){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(TUTORIAL, prSave);
        editor.commit();
    }

    public boolean getTutorial() {
        return getSettings().getBoolean(TUTORIAL, true);
    }

    public void setSesion(boolean prSave2){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(SESION, prSave2);
        editor.commit();
    }

    public boolean getSesion(){
        return getSettings().getBoolean(SESION, true);
    }




    public String getCabbie_Id(){
        return getSettings().getString(CABBIEID, CABBIEID);
    }

    public void setCabbie_Id(String prSave){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(CABBIEID, prSave);
        editor.commit();
    }

}
