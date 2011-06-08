package com.diegomartin.telemaco.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Currency;

public class CurrencyDAO {
	private final static String TABLENAME = "Currency";
	private final static String WHERE_CONDITION = "id=?";

	public static long create(Currency c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("code", c.getCode());
			values.put("rate", c.getRate());
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Currency read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Currency currency = new Currency();
		
		if (db!=null){
			String columns[] = {"name", "code", "rate"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				currency.setId(id);
				currency.setName(cursor.getString(0));
				currency.setCode(cursor.getString(1));
				currency.setRate(cursor.getDouble(2));
			}
		}
		return currency;
	}
	
	public static int update(Currency c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("code", c.getCode());
			values.put("rate", c.getRate());
			
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(c.getId())});
			db.close();
		}
		return rows;
	}
	
	public static int delete(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			rows = db.delete(TABLENAME, WHERE_CONDITION, new String[] {String.valueOf(id)});
			db.close();
		}
		return rows;
	}
	
	public static int delete(Currency c){
		return delete(c.getId());
	}
}