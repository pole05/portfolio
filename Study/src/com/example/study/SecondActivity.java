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

	// 00 : 00�� ǥ���ϱ� ���� �޼ҵ�
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// ������ �ð��� �ؽ�Ʈ�� ǥ���ϱ�
			TextView thour = (TextView) findViewById(R.id.time);
			thour.setText(new StringBuilder().append(pad(hourOfDay)).append(" : ").append(pad(minute)));

			// ������ �ð��� ������ �ٲٱ�
			mtime = (hourOfDay * 60) + minute;

		}
	};

	// OnCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondactivity_main);
		
		//���� ����ǥ���� �Ұ�
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Typeface face = Typeface.createFromAsset(getAssets(), "NANUMBARUNPENB.TTF");
		
		// �ð� �۾��� ������ ��
		TextView set = (TextView) findViewById(R.id.time);
		set.setTypeface(face);
		set.setOnClickListener(new View.OnClickListener() {
			// �ð� ���� ��ȭâ ���
			@Override
			public void onClick(View v) {
				new TimePickerDialog(SecondActivity.this, timeSetListener, ahour,
						aminute, true).show();
			}
		});

		// �̹��� ������ �������� �̵�
		ImageView setting = (ImageView) findViewById(R.id.setting);
		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 Intent intent2 = new Intent(SecondActivity.this, MainActivity.class);
				 startActivity(intent2);

			}
		});

		// ��� ���� & �佺Ʈ �޽���
		
		rg = (RadioGroup) findViewById(R.id.rd);
		
		// ���1
		RadioButton mode1 = (RadioButton) findViewById(R.id.mode1);
		mode1.setTypeface(face);
		mode1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mode = 1;
				String msg = "��� �޼��� �߻�";
				Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
		
		// ���2
		RadioButton mode2 = (RadioButton) findViewById(R.id.mode2);
		mode2.setTypeface(face);
		mode2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mode = 2;
				String msg = "Ÿ�� ����� ���� ��ź ";
				Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		// ���3
		RadioButton mode3 = (RadioButton) findViewById(R.id.mode3);
		mode3.setTypeface(face);
		mode3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mode = 3;
				String msg = "��üȭ�� ���븸���";
				Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		

		// ��ư ������ ���� �������� �̵�
		Button next = (Button) findViewById(R.id.next);
		next.setTypeface(face);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// �ð��̳� ��带 �������� �ʰ� ��ư�� ������ �������� �Ѿ�� �ʴ´�
				RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
				
				if(rd == null && mtime != 0 ){
					String msg = "��带 �������ּ��� ^^";
					Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(rd != null && mtime == 0 ){
					String msg = "�ð��� �������ּ��� ^^";
					Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(rd == null && mtime == 0 ){
					String msg = "�ð��� ��带 �������ּ��� ^^";
					Toast.makeText(SecondActivity.this, msg, Toast.LENGTH_SHORT).show();
					return;
				}
                
				// totaltime�� ThirdActivity�� �Ѱ� �ش�
				Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
				intent.putExtra("mtime", mtime);
				intent.putExtra("mode", mode);
				startActivity(intent);
				finish();
			}
		});

	}

}