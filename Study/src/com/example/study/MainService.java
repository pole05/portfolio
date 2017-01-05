package com.example.study;

import java.util.Calendar;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

public class MainService extends Service {
	
	TextView text1, text2, text3, text4, text5, text6, text7, text8;
	
	// 목표시간
	private int maintime = 0;
	
	// 남은시간
	private int remaintime = 0;
	
	// 현재모드
	private int mode = 0;
	
	// 총 시간
	int abc = 0;
		
	private TimerBR br;
	private View mView;
	private WindowManager mManager;	
	private WindowManager.LayoutParams mParams;
	
	// 현재 시간 메소드
	public void recentTime() {
		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.HOUR_OF_DAY);
		int sec = cal.get(Calendar.MINUTE);

		String Rtime = String.format("%02d : %02d", min, sec);
		text2.setText(Rtime);
	}
	
	// 타이머 구동
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.e("service", "남은시간 : " + remaintime);
			Log.e("service", "mode : " + mode);
				
			// 60초(1분) 경과시 남은 시간 1분 깍음
			if(remaintime % 60 == 0) {
				// 남은 시간 변경
				sendBroadcast(new Intent("com.example.study.writeRemainTime"));
				if (mode ==3) {
					int min = remaintime / 60;
					int hour = remaintime / 60 / 60;
					String strtime = String.format("%02d : %02d", hour, min);
					text6.setText(strtime);			
				}			
			}
			
			// 10초 마다 각 모드에 따라 출력
			if(remaintime % 10 == 0) {
				if(mode == 1) {
					sendBroadcast(new Intent("com.example.study.showToast"));
					Log.e("service", "showToast");
				}
			} 
				
			if(remaintime % 4 == 0) {	
				if(mode == 2) {
					sendBroadcast(new Intent("com.example.study.playSound"));
					Log.e("service", "playSound");
				}
			}

			// 타이머 끝나면 확인창 실행
			if (remaintime <= 0) {
				
				sendBroadcast(new Intent("com.example.study.studyTimeEnd"));
				if(mode == 3) {
					mManager.removeView(mView);
				}
//위로 옮겨봄		sendBroadcast(new Intent("com.example.study.studyTimeEnd"));
				
				
			} else {
				remaintime--;
				
//				sendBroadcast(new Intent("com.example.study.recentTime"));
//				if (mode == 3) {
//					recentTime();	
//				}
				
				this.sendEmptyMessageDelayed(0, 1000);
			}
			
			
		}
	};
	
	
	// 현재시간 핸들러
	
	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
				
				sendBroadcast(new Intent("com.example.study.recentTime"));
				if (mode == 3) {
					recentTime();	
				}
				
				this.sendEmptyMessageDelayed(0, 1000);
			}
			
			
		};
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {					
		maintime = intent.getIntExtra("mtime", 0);
		
		maintime = maintime * 60; // 초로 변환해서 계산
		
		remaintime = maintime;
		
		mode = intent.getIntExtra("mode", 0);
		
		handler.sendEmptyMessage(0);
		handler2.sendEmptyMessage(0);

		if(mode == 3) {
			LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    mView = mInflater.inflate(R.layout.lockscreen, null);
		 
		    mManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		    mParams = new WindowManager.LayoutParams(
		            WindowManager.LayoutParams.MATCH_PARENT,
		            WindowManager.LayoutParams.MATCH_PARENT,
		            WindowManager.LayoutParams.TYPE_PHONE,
		            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
		            PixelFormat.TRANSLUCENT);
	    
		    mManager.addView(mView, mParams);
		    mView.setFitsSystemWindows(true);
		    
		    text1 = (TextView) mView.findViewById(R.id.text1);
			text2 = (TextView) mView.findViewById(R.id.text2);
			text3 = (TextView) mView.findViewById(R.id.text3);
			text4 = (TextView) mView.findViewById(R.id.text4);
			text5 = (TextView) mView.findViewById(R.id.text5);
			text6 = (TextView) mView.findViewById(R.id.text6);
			text7 = (TextView) mView.findViewById(R.id.text7);
			text8 = (TextView) mView.findViewById(R.id.text8);
			
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
			
			// 현재시간 표시
//			recentTime();			
			
			// 목표 시간 표시
			int min2 = maintime / 60 / 60;	// 초를 시간으로
			int sec2 = maintime / 60; // 초를 분으로
			String totaltime = String.format("%02d : %02d", min2, sec2);
			text4.setText(totaltime + "");
			
			// 총시간 text8
			abc = intent.getIntExtra("abc", 0);
			System.out.println(abc);
			int min3 = abc / 60;
			int sec3 = abc % 60;
			String time = String.format("%02d : %02d", min3, sec3);
			text8.setText(time);
			
			
		    
		} else {
			br = new TimerBR();
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.example.study.pause");
			filter.addAction("com.example.study.reStudy");
			filter.addAction("com.example.study.stop");
			registerReceiver(br, filter);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public class TimerBR extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String filter = intent.getAction();

			if(filter.equals("com.example.study.pause")) {
				handler.removeMessages(0);
			} else if(filter.equals("com.example.study.reStudy")) {
				handler.sendEmptyMessage(0);
			} else if(filter.equals("com.example.study.stop")) {
				handler.removeMessages(0);
				handler2.removeMessages(0);
			} 

		}
	}
}