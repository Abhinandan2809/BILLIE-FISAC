package com.hasthik.billi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class logindb extends SQLiteOpenHelper {
    private static final String dbname="users.db";
    public logindb(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query="create table registered_user (id integer primary key autoincrement, username text NOT NULL, password text NOT NULL)";
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS registered_user");
        onCreate(db);

    }
    public void addUser(String username, String password)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long res=db.insert("registered_user",null,cv);
    }
    public boolean checkpresent(String username, String password)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM registered_user where username=? and password=?",new String[]{username, password});
        int querycount=c.getCount();
        c.close();
        if(querycount>0)
            return true;
        else
            return false;
    }
    public boolean checkexist(String username)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM registered_user where username=?",new String[]{username});
        int querycount=c.getCount();
        c.close();
        if(querycount>0)
            return true;
        else
            return false;
    }
}

