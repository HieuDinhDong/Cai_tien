package com.example.shippinguserapplication.supports;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.shippinguserapplication.models.Commodities;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "shipping.db";
    private final static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertCommodities(Commodities commodities) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO commodities VALUES(null,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, commodities.getNameCom());
        statement.bindString(2, commodities.getDescribeCom());
        statement.bindDouble(3, commodities.getWeight());
        statement.bindDouble(4, commodities.getPrice());
        statement.executeInsert();
    }

    public void updateCommodities(Commodities commodities){
        SQLiteDatabase database=getWritableDatabase();
        String sql="UPDATE commodities SET name_com=?,describe_com=?,weight=?,price=?  WHERE id_com=?";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, commodities.getNameCom());
        statement.bindString(2, commodities.getDescribeCom());
        statement.bindDouble(3, commodities.getWeight());
        statement.bindDouble(4, commodities.getPrice());
        statement.bindLong(5, commodities.getIdCom());
        statement.executeUpdateDelete();
    }

    public Commodities getCommoditiesDetail(int idCom) {
        String sql = "SELECT * FROM commodities WHERE id_com="+idCom;
        Cursor data = GetData(sql);
        Commodities commodities=new Commodities();
        while (data.moveToNext()) {
            commodities.setIdCom(data.getInt(0));
            commodities.setNameCom(data.getString(1));
            commodities.setDescribeCom(data.getString(2));
            commodities.setWeight(data.getDouble(3));
            commodities.setPrice(data.getDouble(4));
        }
        return commodities;
    }

    public ArrayList<Commodities> getAllCommodities() {
        String sql = "SELECT * FROM commodities";
        Cursor data = GetData(sql);
        ArrayList<Commodities> list=new ArrayList<>();
        while (data.moveToNext()) {
            list.add(new Commodities(data.getInt(0),data.getString(1),data.getString(2),data.getDouble(3),data.getDouble(4)));
        }
        return list;
    }

    public void deleteCommodities(int idCom) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM commodities WHERE id_com= " + idCom;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeInsert();
    }


    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS commodities(id_com INTEGER PRIMARY KEY AUTOINCREMENT,name_com VARCHAR(250), describe_com VARCHAR(250), weight DOUBLE, price DOUBLE)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
