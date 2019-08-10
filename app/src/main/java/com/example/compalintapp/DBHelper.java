package com.example.compalintapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Storage.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Storage.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Storage.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public long addComplaint(Storage storage)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Storage.COLUMN_NAME,storage.getName());
        contentValues.put(Storage.COLUMN_COMP,storage.getComplaint());
        contentValues.put(Storage.COLUMN_NUMBER,storage.getNum());

        Long id=sqLiteDatabase.insert(Storage.TABLE_NAME,null,contentValues);
        return id;
    }

   public ArrayList<Storage> readComplaint()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+Storage.TABLE_NAME,null);

        ArrayList<Storage> storageList=new ArrayList<Storage>();

        if(cursor.moveToFirst())
        {
            Storage storage;
            do {
                storage=new Storage();
                storage.setId(cursor.getInt(0));
                storage.setName(cursor.getString(1));
                storage.setComplaint(cursor.getString(2));
                storage.setNum(cursor.getString(3));
                Log.d("DBHELPER",storage.getName()+" data : "+storage.getId());
                storageList.add(storage);
            }while (cursor.moveToNext());
        }
        return storageList;
    }
}
