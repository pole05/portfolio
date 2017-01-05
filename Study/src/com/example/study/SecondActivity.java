package com.example.study;

import com.example.Setting.MainActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SecondActivity extends Activity {

	TextView thour, tminute;
	int ahour, aminute, mtime, mode;
	RadioGroup rg;

	// 00 : 00로 표시하기 위한 메소드
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// 설정한 시간을 텍스트로 표시하기
			TextView thour = (TextView) findViewById(R.id.time);
			thour.setText(new StringBuilder().append(pad(hourOfDay)).append(" : ").append(pad(minute)));

			// 설정한 시간을 분으로 바꾸기
			mtime = (hourOfDay * 60) + minute;

		}
	};

	// OnCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondactivity_main);
		
		//위에 상태표시줄 소거
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Typeface face = Typeface.createFromAsset(getAssets(), "NANUMBARUNPENB.TTF");
		
		// 시계 글씨를 눌렀을 때
		TextView set = (TextView) findViewById(R.id.time);
		set.setTypeface(face);
		set.setOnClickListener(new View.OnClickListener() {
			// 시간 설정 대화창 뜬다
			@Override
			public void onClick(View v) {
				new TimePickerDialog(SecondActivity.this, timeSetListener, ahour,
						aminute, true).show();
			}
		});

		// 이미지 누르면 설정으로 이동
		ImageView setting = (ImageView) findViewById(R.id.setting);
		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 Intent intent2 = new Intent(SecondActivity.this, MainActivity.class);
				 startActivity(intent2);

			}
		});

		// 모드 선택 & 토스트 메시지
		
		rg = (RadioGroup) findViewById(R.id.rd);
		
		// 모드1
		RadioButton mode1 = (RadioButton) findViewById(R.id.mode1);
		mode1.setTypeface(face);
		mode1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mode = 1;
				String msg = "경고 메세지 발사";
				Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
		
		// 모드2
		RadioButton mode2 = (RadioButton) findViewById(R.id.mode2);
		mode2.setTypeface(face);
		mode2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mode = 2;
				String msg = "타앱 실행시 랜덤 폭탄 ";
				Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		// 모드3
		RadioButton mode3 = (RadioButton) findViewById(R.id.mode3);
		mode3.setTypeface(face);
		mode3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mode = 3;
				String msg = "전체화면 먹통만들기";
				Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		

		// 버튼 누르면 다음 페이지로 이동
		Button next = (Button) findViewById(R.id.next);
		next.setTypeface(face);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 시간이나 모드를 선택하지 않고 버튼을 누르면 다음으로 넘어가지 않는다
				RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
				
				if(rd == null && mtime != 0 ){
					String msg = "모드를 선택해주세요 ^^";
					Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(rd != null && mtime == 0 ){
					String msg = "시간을 설정해주세요 ^^";
					Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(rd == null && mtime == 0 ){
					String msg = "시간과 모드를 설정해주세요 ^^";
					Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
					return;
				}
                
				// totaltime을 ThirdActivity로 넘겨 준다
				Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
				intent.putExtra("mtime", mtime);
				intent.putExtra("mode", mode);
				startActivity(intent);
				finish();
			}
		});

	}

}