package com.diegomartin.telemaco.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Plug;

public class PlugDAO {
	private final static String TABLENAME = "Plug";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "name", "description", "image"};
	
	public static long create(Plug p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", p.getName());
			values.put("description", p.getDescription());
			values.put("image", p.getImage());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Plug read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Plug plug = new Plug();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if (cursor.moveToNext()){
				plug.setId(cursor.getLong(0));
				plug.setName(cursor.getString(1));
				plug.setDescription(cursor.getString(2));
				plug.setImage(cursor.getString(3));
			}
			cursor.close();
		}
		return plug;
	}
	
	public static int update(Plug p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", p.getName());
			values.put("description", p.getDescription());
			values.put("image", p.getImage());
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(p.getId())});
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
	
	public static int delete(Plug t){
		return delete(t.getId());
	}
	
	public static void createOrUpdate(Plug plug) {
		Plug p = read(plug.getId());
		if (p.getName()!=null) update(plug);
		else create(plug);
	}
}
