package com.example.Setting;

import java.util.Calendar;

import com.example.study.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

public class DatePicker {
	public static String date_selected;
	SharedPreferences setting;
	SharedPreferences.Editor editor;
	
	public void DialogDatePicker(final Context context) {
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog.OnDateSetListener mDateSetListener =

		new DatePickerDialog.OnDateSetListener() {

			// onDateSet method
			@Override
			public void onDateSet(android.widget.DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				date_selected = String.valueOf(year) + "/"
						+ String.valueOf(monthOfYear + 1) + "/"
						+ String.valueOf(dayOfMonth);
				
				//콘텍스트를 액티비티로 형변환을 한 후에 이용해야한다.
				Activity act = (Activity)context;
				
				setting = act.getSharedPreferences("setting", 0);
				editor= setting.edit();
				editor.putString("D", date_selected);
				
				editor.commit();
				
				TextView d_day =(TextView) act.findViewById(R.id.textre);
				d_day.setText(date_selected);
				d_day.setVisibility(View.VISIBLE);
			}
		};

		DatePickerDialog alert = new DatePickerDialog(context,
				mDateSetListener, cyear, cmonth, cday);
		alert.show();
	}
}
