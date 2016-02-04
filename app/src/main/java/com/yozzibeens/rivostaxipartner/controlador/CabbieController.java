package com.yozzibeens.rivostaxipartner.controlador;

import android.content.Context;
import android.util.Log;

import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.modelo.CabbieDao;
import com.yozzibeens.rivostaxipartner.modelo.DaoSession;
import com.yozzibeens.rivostaxipartner.modelo.RivosPartnerDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by savidsalazar
 */
public class CabbieController {

    private static final String TAG = "Cabbie";

    private Context context;

    public CabbieController(Context prContext){
        this.context = prContext;
    }

    public boolean guardarCabbie(Cabbie prCabbie){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            oCabbieDao.insert(prCabbie);
            return true;
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return false;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }
    
    public boolean guardarOActualizarCabbie(Cabbie prCabbie){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            oCabbieDao.insertOrReplace(prCabbie);
            return true;
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return false;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }

    public boolean guardarOActualizarCabbie(ArrayList<Cabbie> prCabbies){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            oCabbieDao.insertOrReplaceInTx(prCabbies);
            return true;
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return false;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }

    public boolean eliminarCabbie(Cabbie prCabbie){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            oCabbieDao.delete(prCabbie);
            return true;
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return false;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }

    public boolean eliminarTodo(){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            oCabbieDao.deleteAll();
            return true;
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return false;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }

    public List<Cabbie> obtenerCabbie(){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            return oCabbieDao.loadAll();
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return null;
        }
        finally {
           RivosPartnerDB.getInstance().closeDatabase();
        }
    }

    public Cabbie obtenerCabbie(Long prKey){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            return oCabbieDao.load(prKey);
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return null;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }

    public Cabbie obtenerCabbiePorCabbieId(String cabbieId){
        try {
            DaoSession oRivosPartnerDB = RivosPartnerDB.getInstance().openDatabase(context);
            CabbieDao oCabbieDao = oRivosPartnerDB.getCabbieDao();
            return oCabbieDao.queryBuilder().where(CabbieDao.Properties.Cabbie_Id.eq(cabbieId)).unique();
        }
        catch (Exception error){
            Log.e(TAG, error.getMessage());
            return null;
        }
        finally {
            RivosPartnerDB.getInstance().closeDatabase();
        }
    }



}