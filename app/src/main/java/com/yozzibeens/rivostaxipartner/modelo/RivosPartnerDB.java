package com.yozzibeens.rivostaxipartner.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by SavidSalazar
 */

public class RivosPartnerDB {
    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static RivosPartnerDB instance;
    private static DaoMaster.DevOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private DaoSession daoSession;

    public static synchronized void initializeInstance() {
        if (instance == null) {
            instance = new RivosPartnerDB();
        }
    }

    public static synchronized RivosPartnerDB getInstance() {
        if (instance == null) {
            throw new IllegalStateException(RivosPartnerDB.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    public synchronized DaoSession getSession()
    {
        DaoMaster daoMaster = new DaoMaster(mDatabase);
        daoSession = daoMaster.newSession();
        return daoSession;
    }

    public synchronized DaoSession openDatabase(Context context) {
        if(mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabaseHelper = new DaoMaster.DevOpenHelper(context, "RivosPartner-db", null);
            mDatabase = mDatabaseHelper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(mDatabase);
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
            daoSession.clear();

        }
    }
}