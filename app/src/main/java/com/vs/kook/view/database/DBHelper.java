package com.vs.kook.view.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vs.kook.utils.AppConstants;
import com.vs.kook.view.models.SMSHistoryModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, AppConstants.DATABASE_NAME, null, AppConstants.DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists call_history(number varchar, date varchar, time varchar, duration varchar, type varchar)");
		db.execSQL("create table if not exists sms_history(number varchar, date varchar, time varchar,body varchar,type varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS call_history");
	    onCreate(db);
	}
	
	public boolean insertCallHistoryData(String number, String date, String time, String duration, String type)
	{
		SQLiteDatabase sdb=this.getWritableDatabase();
		sdb.execSQL("insert into call_history values('"+number+"','"+date+"','"+time+"','"+duration+"','"+type+"')");
		return true;
	}
	
	public Cursor getCallHistoryData()
	{
		SQLiteDatabase sdb = this.getReadableDatabase();
		Cursor c = sdb.rawQuery("select * from call_history",null);
		return c;
	}
	public void deleteCallHistoryTable()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS call_history");
		onCreate(db);
	}


	public boolean insertSMSHistoryData(String number, String date, String time, String body, int type)
	{
		SQLiteDatabase sdb=this.getWritableDatabase();
		body = body.replaceAll("'", "''");
		sdb.execSQL("insert into sms_history values('"+number+"','"+date+"','"+time+"','"+body+"','"+type+"')");
		return true;
	}

    public ArrayList<SMSHistoryModel> getSMSHistoryData() {
        SQLiteDatabase sdb = this.getReadableDatabase();
        Cursor c = sdb.rawQuery("select * from sms_history",null);
        ArrayList<SMSHistoryModel> list = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                SMSHistoryModel model = new SMSHistoryModel();
                model.setNumber(c.getString(0));
                model.setDate(c.getString(1));
                model.setTime(c.getString(2));
                model.setBody(c.getString(3));
                model.setType(c.getString(4));
                Log.e("Test",model.getType());
                list.add(model);
            } while (c.moveToNext());
        } else {
            Log.e("Call Details:", "No SMS history exists!!!");
        }
        return list;
    }

}
