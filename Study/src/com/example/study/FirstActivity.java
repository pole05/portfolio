package com.example.study;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FirstActivity extends Activity {

	private TextView D_day, D_date, Sub, preSub;
	SharedPreferences setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		//위에 상태표시줄 소거
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 화면 2초 후 넘어가게
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i = new Intent(FirstActivity.this, SecondActivity.class);
				startActivity(i);
				finish();
			}
		}, 2000);

		D_day = (TextView) findViewById(R.id.D_day);
		D_date = (TextView) findViewById(R.id.D_date);
		Sub = (TextView) findViewById(R.id.Sub);
		preSub = (TextView) findViewById(R.id.preSub);
		
		
		Typeface face2 = Typeface.createFromAsset(getAssets(), "NanumPen.ttf");
		preSub.setTypeface(face2);
		Sub.setTypeface(face2);

		Typeface face1 = Typeface.createFromAsset(getAssets(),
				"NANUMBARUNGOTHIC.TTF");
		D_day.setTypeface(face1);
		D_date.setTypeface(face1);

		setting = getSharedPreferences("setting", 0);
		String date = setting.getString("D", null);
		
		if(date != null){
			int Tyear = Integer.parseInt(date.substring(0,4));
			int Tmonth = Integer.parseInt(date.substring(5,7));
			int Tday = Integer.parseInt(date.substring(8));
			int D_date_i;
			
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DAY_OF_YEAR);
			int year = cal.get(Calendar.YEAR);
			cal.set(Tyear,Tmonth-1,Tday);
			if(year == cal.get(Calendar.YEAR)){
			D_date_i = cal.get(Calendar.DAY_OF_YEAR)-day;
			} else {
			D_date_i = (cal.get(Calendar.DAY_OF_YEAR)-day)
			+ (cal.get(Calendar.YEAR)-year)*365;
			}
			if(D_date_i < 0){
			D_day.setVisibility(View.INVISIBLE);
			D_date.setVisibility(View.INVISIBLE);
			}
			D_date.setText(D_date_i+"");
			}	
		
	}
}