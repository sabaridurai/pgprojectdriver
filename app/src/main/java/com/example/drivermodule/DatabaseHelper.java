package com.example.drivermodule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="driverDB.db";
    private final String ID="id";
    private final String Name="name";
    private final String Phno="phno";
    private final String Licno="lnumber";
    private final String DD="date";
    private final String Address="address";
    private final String District="district";
    private final String Stat="state";
    private final String Postcode="postcode";
    private final String Count="country";
    private static final String tablename="driverprofile";



    public DatabaseHelper(@Nullable Context context) {

        super(context,DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("create table if not exists "+tablename+" ("+ID+" text primary key,"+Name+" text,"+Phno+" text,"+Licno+" text,"+DD+" text,"
        +Address+" text,"+Postcode+" text,"+District+" text,"+Stat+" text,"+Count+" text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

db.execSQL("drop table if exists "+tablename);

onCreate(db);
    }




    public boolean insert_data(String Did,String Dname,String Dphno,String Dlicno,String Ddd,String Daddress,String Dpost,String Ddistrict,String Dstate,String Dcount)
    {
SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(ID,Did);
        contentValues.put(Name,Dname);
        contentValues.put(Phno,Dphno);
        contentValues.put(Licno,Dlicno);
        contentValues.put(DD,Ddd);
        contentValues.put(Address,Daddress);
        contentValues.put(Postcode,Dpost);
        contentValues.put(District,Ddistrict);
        contentValues.put(Stat,Dstate);
        contentValues.put(Count,Dcount);
        long reg=db.insert(tablename,null,contentValues);

        return reg!=-1;
    }
    public Cursor getAlldata()
    {
        SQLiteDatabase db=getReadableDatabase();

        Cursor res=db.rawQuery("select * from "+tablename,null);
        return res;
    }
    public boolean update(String Did,String Dname,String Dphno,String Dlicno,String Ddd,String Daddress,String Dpost,String Ddistrict,String Dstate,String Dcount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID,Did);
        contentValues.put(Name,Dname);
        contentValues.put(Phno,Dphno);
        contentValues.put(Licno,Dlicno);
        contentValues.put(DD,Ddd);
        contentValues.put(Address,Daddress);
        contentValues.put(Postcode,Dpost);
        contentValues.put(District,Ddistrict);
        contentValues.put(Stat,Dstate);
        contentValues.put(Count,Dcount);
        long reg=db.update(tablename,contentValues,ID+"=?",new String[]{Did});

        return reg==1;
    }
    public boolean delete(){
        SQLiteDatabase db=this.getWritableDatabase();
        int res=db.delete(tablename,null,null);
        return res==1;

    }
}
