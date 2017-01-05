package com.example.study;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBUtil {
	
	MyDBHelper myHelper;
	SQLiteDatabase sqlDB;
	
	public boolean isExistTable(Context context, String name) {
		myHelper = new MyDBHelper(context);
		sqlDB = myHelper.getReadableDatabase();
		boolean isExist = false;
		Cursor cursor = null;
		try {
			cursor = sqlDB.rawQuery("SELECT num FROM todaystudy;",null);
			isExist = true;
		} catch(Exception e) {
			Log.e("dbutil", "테이블 없음");
		} finally {
			if(cursor != null) cursor.close();
			if(sqlDB != null) sqlDB.close();
		}

		return isExist;
	}
}
