package com.kapp.info.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kapp.info.Entity.ActiveCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Naser on 6/17/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AppLouck.db";
    public static final String CONTACTS_TABLE_NAME = "activecode";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_MOBILE = "mobile";
    public static final String CONTACTS_COLUMN_KEYCODE = "keycode";
    public static final String CONTACTS_COLUMN_ACTIVE_CODE = "activecode";
    public static final String CONTACTS_COLUMN_CREATE_DATE = "createdate";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table activecode " +
                        "(id integer primary key, name text,mobile text,keycode text, activecode text,createdate text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS activecode");
        onCreate(db);
    }

    public boolean insertActiveCode (String name, String mobile, String keycode, String activecode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("keycode", keycode);
        contentValues.put("activecode", activecode);
        contentValues.put("createdate",currentDateandTime );
        db.insert("activecode", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from activecode where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateActiveCode (int id ,String name, String mobile, String keycode, String activecode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("keycode", keycode);
        contentValues.put("activecode", activecode);
        db.update("activecode", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteActiveCode (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("activecode",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<ActiveCode> getAllActiveCode() {
        ArrayList<ActiveCode> array_list = new ArrayList<ActiveCode>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from activecode", null );
        res.moveToFirst();

        ActiveCode activeCode;
        while(res.isAfterLast() == false){
            activeCode = new ActiveCode(
                    res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_KEYCODE)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_ACTIVE_CODE)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_MOBILE)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_CREATE_DATE))
            );
            array_list.add(activeCode);
            res.moveToNext();
        }
        return array_list;
    }
}