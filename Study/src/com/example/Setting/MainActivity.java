package com.example.Setting;

import com.example.study.DBUtil;
import com.example.study.FourthActivity;
import com.example.study.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView list;
	ListViewAdapterRe lAdapt;
	DBUtil DButil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		//위에 상태표시줄 소거
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Content[] listArray = { new Content("D-day 설정", R.drawable.test_icon),
				new Content("초기화", R.drawable.reset),
				new Content("통계 보기", R.drawable.chart),
				new Content("뒤로", R.drawable.back) };

		lAdapt = new ListViewAdapterRe(this, listArray);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(lAdapt);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if (pos == 0) {
					new DatePicker().DialogDatePicker(MainActivity.this);

				} else if (pos == 1) {
					SharedPreferences setting;
					SharedPreferences.Editor editor;
					setting = getSharedPreferences("setting", 0);
					editor= setting.edit();
					editor.clear();
					editor.commit();
					TextView textre = (TextView) list.findViewById(R.id.textre);
					textre.setText("");
					DButil = new DBUtil();
					if(DButil.isExistTable(MainActivity.this, "TODAYSTUDY") == true){
					new DropTable().dropTable(MainActivity.this);}
					
				} else if (pos == 2) {
					DButil = new DBUtil();
					if(DButil.isExistTable(MainActivity.this, "TODAYSTUDY") == true){
					 Intent i = new Intent(MainActivity.this, FourthActivity.class);
					 startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "자료없음", 0).show();
					}
				} else if (pos == 3) {
					finish();
				}


			}
		});
		
	}

}
