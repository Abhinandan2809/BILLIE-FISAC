package com.hasthik.billi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class productdb extends SQLiteOpenHelper {
    private static final String dbname="product.db";
    public productdb(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query="create table price_list (id integer primary key autoincrement, productName text NOT NULL, price text NOT NULL)";
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS price_list");
        onCreate(db);

    }
    public void addProduct(String productName, String price)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("productName", productName);
        cv.put("price", price);
        long res=db.insert("price_list",null,cv);
    }
    public int getPrice(String productName)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c= db.rawQuery("SELECT price from price_list where productName=?",new String[]{productName});
        c.moveToFirst();
        String price=c.getString(0);
        return Integer.parseInt(price.substring(1));
    }
    public void updatePrice(String productName, String price)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("productName", productName);
        cv.put("price", price);
        db.update("price_list", cv, "productName=?",new String[]{productName});
    }
    public void deletePrice(String productName)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.delete("price_list", "productName=?", new String[]{productName});
    }
    public boolean checkexist(String productName)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM price_list where productName=?",new String[]{productName});
        int querycount=c.getCount();
        c.close();
        if(querycount>0)
            return true;
        else
            return false;
    }
    public Cursor getRecords()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM price_list ORDER BY id desc",null);
        return c;
    }


}

