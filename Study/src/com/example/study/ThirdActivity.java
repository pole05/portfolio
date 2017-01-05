package com.example.study;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.process.AndroidProcesses;
import com.example.process.model.AndroidAppProcess;
import com.example.process.model.Stat;
import com.example.process.model.Statm;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends Activity {

	TextView text1, text2, text3, text4, text5, text6, text7, text8;
	MyDBHelper myHelper;
	SQLiteDatabase sqlDB;
	int mtime = 0;
	int maintime = 0;
	boolean isContinue = true;
	boolean isContinue2 = true;
	int mode;
	TimerBR br;
	
	Intent service;

	boolean isFirst = true;

	private void studyTimeEnd() {
		AlertDialog.Builder alert = new AlertDialog.Builder(ThirdActivity.this);
		alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 4번째 화면 연결
				Intent intent = new Intent(ThirdActivity.this,
						FourthActivity.class);
				startActivity(intent);
				dialog.dismiss();

				// 서비스 종료
				stopService(service);

				// 브로드캐스트 종료
				unregisterReceiver(br);

				finish();
			}
		});
		alert.setMessage("목표시간을 성공하셨습니다!");
		alert.show();

		// DB: 공부시간 저장
		saveDB(mtime, mtime);
	}

	private void writeRemainTime() {
		int min = maintime / 60;
		int sec = maintime % 60;

		String strtime = String.format("%02d : %02d", min, sec);
		text6.setText(strtime);

		maintime--;
	}
	
	public void recentTime() {
		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.HOUR_OF_DAY);
		int sec = cal.get(Calendar.MINUTE);

		String Rtime = String.format("%02d : %02d", min, sec);
		text2.setText(Rtime);
	}
	
	public int readDB() {
		sqlDB = myHelper.getReadableDatabase();
		Cursor cursor;
		cursor = sqlDB.rawQuery("SELECT * FROM TODAYSTUDY WHERE DATE1 = '"
				+ getToday() + "';", null);
		int abc = 0;
		while (cursor.moveToNext()) {
			Log.e("순서", cursor.getString(0) + "");
			Log.e("날짜", cursor.getInt(1) + "");
			Log.e("목표시간", cursor.getInt(2) + "");
			Log.e("누적공부시간", cursor.getInt(3) + "");
			abc = abc + cursor.getInt(3);
		}

		cursor.close();
		sqlDB.close();

		return abc;
	}

	private void saveDB(int mtime, int studyTime) {
		sqlDB = myHelper.getWritableDatabase();
		// INSERT INTO TODAYSTUDY VALUES ('2016-10-17', 60);
		sqlDB.execSQL("INSERT INTO TODAYSTUDY VALUES (NULL, '" + getToday()
				+ "', " + mtime + ", " + studyTime + ");");

		sqlDB.close();
	}

	// DB: 날짜 메소드
	private String getToday() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		String date = year + "-" + (month + 1 < 10 ? "0" + month : month) + "-"
				+ (day < 10 ? "0" + day : day);
		return date;
	}

	// 모드1 토스트 메소드
	public void showToast() {
		Toast toast = Toast.makeText(ThirdActivity.this, "폰끄고 공부해라",
				Toast.LENGTH_SHORT);
		toast.show();
		toast.setGravity(Gravity.BOTTOM, 0, 60);
		toast.show();
	}

	List<String> before;
	
	// 모드2 소리 메소드
	public void playSound() {
		List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();

		PackageManager pm = this.getPackageManager();

		if(isFirst) {
			before = new ArrayList<String>();
			
			for(int i = 0; i < processes.size(); i++) {			
				AndroidAppProcess process = processes.get(i);
				String processName = process.name;
		
				try {
					PackageInfo packageInfo = process.getPackageInfo(this, 0);
					String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
					Log.e("processName", processName);
					Log.e("appname", appName);
					before.add(appName);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {}
				
			}
			isFirst = false;
		}
		
		List<String> checkList = new ArrayList<String>(before);		
		List<String> after = new ArrayList<String>();
		
		for(int i = 0; i < processes.size(); i++) {
			AndroidAppProcess process = processes.get(i);
			String processName = process.name;
			
			try {
				PackageInfo packageInfo = process.getPackageInfo(this, 0);
				String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
				Log.e("processName", processName);
				Log.e("appname", appName);
				after.add(appName);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {}
			
		}

		checkList.retainAll(after);		
	
		if (after.size()-checkList.size() >= 4) {
			MediaPlayer mp = MediaPlayer.create(ThirdActivity.this, R.raw.barf);
			AudioManager amg = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			amg.setStreamVolume(AudioManager.STREAM_MUSIC, 10, AudioManager.FLAG_PLAY_SOUND);
			
			if (!mp.isPlaying()) {
				mp.start();
			}
		}
		
		Log.e("before", "" + before.size());
		Log.e("after", "" + after.size());
		Log.e("checklist", "" + checkList.size());
		Log.e("result", "" + (after.size()-checkList.size()));
							
//		ActivityManager actMng = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//
//		if (isFirst) {
//			List<ActivityManager.RunningAppProcessInfo> list = actMng.getRunningAppProcesses();
//			before = new ArrayList<String>();
//			for (ActivityManager.RunningAppProcessInfo process : list) {
//				String Package = process.processName;
//				Log.d("test", Package);
//				before.add(Package);
//			}
//			isFirst = false;
//		}
//
//		List<String> checkList = new ArrayList<String>(before);
//		
//		List<ActivityManager.RunningAppProcessInfo> list2 = actMng.getRunningAppProcesses();
//		List<String> after = new ArrayList<String>();
//		for (ActivityManager.RunningAppProcessInfo process : list2) {
//			String Package2 = process.processName;
//			Log.d("test", Package2);
//			after.add(Package2);
//		} 
//
//		checkList.retainAll(after);
//	
//
//		if (after.size()-checkList.size() >= 3) {
//			MediaPlayer mp = MediaPlayer.create(ThirdActivity.this, R.raw.barf);
//			AudioManager amg = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//			amg.setStreamVolume(AudioManager.STREAM_MUSIC, 15,
//					AudioManager.FLAG_PLAY_SOUND);
//			
//	
//			if (!mp.isPlaying()) {
//				mp.start();
//			}
//		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirdactivity_main);
		
		//위에 상태표시줄 소거
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);

		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		text5 = (TextView) findViewById(R.id.text5);
		text6 = (TextView) findViewById(R.id.text6);
		text7 = (TextView) findViewById(R.id.text7);
		text8 = (TextView) findViewById(R.id.text8);

		Button btn1 = (Button) findViewById(R.id.btn1);
		Button btn2 = (Button) findViewById(R.id.btn2);

		// 글꼴 설정
		Typeface face = Typeface.createFromAsset(getAssets(),
				"NANUMBARUNPENB.TTF");
		text1.setTypeface(face);
		text2.setTypeface(face);
		text3.setTypeface(face);
		text4.setTypeface(face);
		text5.setTypeface(face);
		text6.setTypeface(face);
		text7.setTypeface(face);
		text8.setTypeface(face);
		btn1.setTypeface(face);
		btn2.setTypeface(face);

		// 배추사진 -> 포기창 실행
		ImageView img = (ImageView) findViewById(R.id.img);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						ThirdActivity.this);
				alert.setPositiveButton("확인",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 4번째 화면 연결
								Intent intent = new Intent(ThirdActivity.this,
										FourthActivity.class);
								startActivity(intent);

								// DB: 목표시간 - 남은시간 = 공부시간 저장
								saveDB(mtime, mtime - maintime);

								// 서비스 종료
								stopService(service);
								sendBroadcast(new Intent(
										"com.example.study.stop"));
								// 브로드캐스트 종료
								unregisterReceiver(br);

								finish();
							}
						});

				alert.setNegativeButton("취소",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss(); // 창 닫기
							}
						});
				alert.setTitle("우리 공부하자");
				alert.setMessage("이대로 포기하시겠습니까?");
				alert.show();
			}
		});

		// Second 화면과 연결
		Intent intent = getIntent();
		mtime = intent.getIntExtra("mtime", 0);
		int min2 = mtime / 60;
		int sec2 = mtime % 60;
		String totaltime = String.format("%02d : %02d", min2, sec2);
		text4.setText(totaltime);
		maintime = mtime;

		// 일시정지
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 서비스 일시정지
				sendBroadcast(new Intent("com.example.study.pause"));
			}
		});

		// 다시시작
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 서비스 다시 시작
				sendBroadcast(new Intent("com.example.study.reStudy"));
			}
		});

		// 모드 구현
		mode = intent.getIntExtra("mode", 0);

		// 브로드캐스트 실행
		br = new TimerBR();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.study.showToast");
		filter.addAction("com.example.study.playSound");
		filter.addAction("com.example.study.writeRemainTime");
		filter.addAction("com.example.study.studyTimeEnd");
		filter.addAction("com.example.study.recentTime");
		registerReceiver(br, filter);


		// DB 구현
		myHelper = new MyDBHelper(this);

		// 오늘 총 공부시간 조회
		int abc = readDB();

		int min3 = abc / 60;
		int sec3 = abc % 60;
		String time = String.format("%02d : %02d", min3, sec3);
		text8.setText(time);

		// 서비스 실행
		service = new Intent(this, MainService.class);
		service.putExtra("mode", mode);
		service.putExtra("mtime", mtime);
		service.putExtra("abc", abc);
		startService(service);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		// back key 차단
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(service);
	}

	public class TimerBR extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String filter = intent.getAction();

			if (filter.equals("com.example.study.showToast")) {
				showToast();
			} else if (filter.equals("com.example.study.playSound")) {
				playSound();
			} else if (filter.equals("com.example.study.writeRemainTime")) {
				writeRemainTime();
			} else if (filter.equals("com.example.study.studyTimeEnd")) {
				studyTimeEnd();
			} else if (filter.equals("com.example.study.recentTime")) {
				recentTime();
			}
		}
	}

}