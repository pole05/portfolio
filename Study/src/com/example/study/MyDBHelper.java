package com.example.study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
		public MyDBHelper(Context context) {
			super(context, "today", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE todaystudy (NUM INTEGER PRIMARY KEY AUTOINCREMENT, date1 CHAR(10), mtime INTEGER, ftime INTEGER);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS todaystudy");
			onCreate(db);
		}
}