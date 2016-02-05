package com.yozzibeens.rivostaxipartner.modelo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.yozzibeens.rivostaxipartner.modelo.Cabbie;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CABBIE.
*/
public class CabbieDao extends AbstractDao<Cabbie, Long> {

    public static final String TABLENAME = "CABBIE";

    /**
     * Properties of entity Cabbie.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Cabbie_Id = new Property(1, String.class, "Cabbie_Id", false, "CABBIE__ID");
        public final static Property Name = new Property(2, String.class, "Name", false, "NAME");
        public final static Property Email = new Property(3, String.class, "Email", false, "EMAIL");
        public final static Property Phone = new Property(4, String.class, "Phone", false, "PHONE");
    };


    public CabbieDao(DaoConfig config) {
        super(config);
    }
    
    public CabbieDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CABBIE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'CABBIE__ID' TEXT," + // 1: Cabbie_Id
                "'NAME' TEXT," + // 2: Name
                "'EMAIL' TEXT," + // 3: Email
                "'PHONE' TEXT);"); // 4: Phone
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CABBIE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Cabbie entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String Cabbie_Id = entity.getCabbie_Id();
        if (Cabbie_Id != null) {
            stmt.bindString(2, Cabbie_Id);
        }
 
        String Name = entity.getName();
        if (Name != null) {
            stmt.bindString(3, Name);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(4, Email);
        }
 
        String Phone = entity.getPhone();
        if (Phone != null) {
            stmt.bindString(5, Phone);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Cabbie readEntity(Cursor cursor, int offset) {
        Cabbie entity = new Cabbie( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Cabbie_Id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Email
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // Phone
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Cabbie entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCabbie_Id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEmail(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Cabbie entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Cabbie entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}