package com.example.drivermodule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperowner extends SQLiteOpenHelper {
    public static final String DBNAME="ownerdb.db";
    String tablename="ownertable";
    String ID="id";
    public DatabaseHelperowner(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+tablename+" ("+ID+" text primary key)");
//        insertownerdata("sdasd");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+tablename);

        onCreate(sqLiteDatabase);
    }

public boolean insertownerdata(String abc){
    SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put(ID,abc);
    long reg=sqLiteDatabase.insert(tablename,null,contentValues);

    return reg!=-1;


}


    public Cursor getownerid()
    {
        SQLiteDatabase db=getReadableDatabase();

        Cursor res=db.rawQuery("select * from "+tablename,null);
        return res;
    }


    public boolean delete(){
        SQLiteDatabase db=this.getWritableDatabase();
        int res=db.delete(tablename,null,null);
        return res==1;

    }

    public boolean update(String Did)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID,Did);

        long reg=db.update(tablename,contentValues,ID+"=?",new String[]{Did});

        return reg==1;
    }

}
