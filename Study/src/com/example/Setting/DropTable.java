package com.example.Setting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.study.MyDBHelper;

public class DropTable {
	MyDBHelper myHelper;
	SQLiteDatabase sqlDB;
	
	public void dropTable(Context context){
		myHelper = new MyDBHelper(context);
		sqlDB = myHelper.getWritableDatabase();
		sqlDB.execSQL("DROP TABLE TODAYSTUDY");
	}
}
