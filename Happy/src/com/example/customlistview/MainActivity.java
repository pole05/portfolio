package com.example.customlistview;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ArrayList<ItemVO> list = new ArrayList<ItemVO>();	
	private ListViewAdapter adapter;
//	private CustomAdapter adapter;
	
	private BackPressCloseHandler backPressCloseHandler;
	
	ProgressDialog dlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 프로그레스 바
		dlg = ProgressDialog.show(this, "로딩중", "기다려주세요.");
		
		// 안내 토스트
		Toast.makeText(this, "길게 누르시면 더 자세한 정보를 보실 수 있습니다.", 35000).show();
		
		// 두번 눌러서 종료
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		// 검색 입력폼
		final EditText search = (EditText) findViewById(R.id.search);
		
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//				.permitAll().build();
//		StrictMode.setThreadPolicy(policy);

		final ListView listView = (ListView) findViewById(R.id.list);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				//TODO
			}
		});
		
		// 컨텍스트 메뉴가 나오도록 등록
		registerForContextMenu(listView);
		
		
		// 검색
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = search.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
		});

		
		// 스레드 시작 (네트워크 연결 부분)
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = Util // util 클래스의 request 메소드
						.request("http://168.131.22.123:8080/mobile/MenuList.do");	// 여기서 데이터를 받아온다

//				final ArrayList<ItemVO> list = new ArrayList<ItemVO>();
				
				try {
					JSONObject obj = new JSONObject(result);
					JSONArray items = obj.getJSONArray("list");
										
					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);	//[]안의 배열 하나
						
						String category_s = item.getString("category_s");
						String name = item.getString("name");
						String place = item.getString("place");
						String starttime = item.getString("starttime");
						String endtime = item.getString("endtime");
						String call = item.getString("call");
						int no = item.getInt("no");
						String p_poster_s = item.getString("p_poster_s");
						
						
						ItemVO vo = new ItemVO();
						vo.setCategory_s(category_s);
						vo.setName(name);
						vo.setPlace(place);
						vo.setStarttime(starttime);
						vo.setEndtime(endtime);
						vo.setCall(call);
						vo.setNo(no);
						vo.setP_poster_s(p_poster_s);
						
						list.add(vo);
					}
					System.out.println(list);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				runOnUiThread(new Runnable() {
				// 스레드 안의 스레드: 스레드 각각 실행되지 않고, 위 스레드 코드가 실행된 후 실행된다
					@Override
					public void run() {
						// 어댑터와 리스트뷰 연결해 줌
						 adapter = new ListViewAdapter(MainActivity.this, R.layout.listview, list);						 
						 listView.setAdapter(adapter);

						// 프로그레스 바 
						dlg.dismiss();						
					}
				});
				
			}
		}).start();	
		// 스레드 끝		
			
		
		// 리스트를 누르면 유튜브 영상으로 이동하게
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//				
//				Intent intent = new Intent(MainActivity.this, Sub.class);	// action, uri			
//				intent.putExtra("id", list.get(pos).getId());	// name, value
//				startActivity(intent);
//				
//			}
//		});
				
	}
	// oncreate 끝
		
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		getMenuInflater().inflate(R.menu.list_item_context_menu, menu);		
	}
	

	// Context 메뉴로 등록한 View(여기서는 ListView)가 클릭되었을 때 자동으로 호출되는 메소드
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();

		final int position = info.position;
		// AdapterView안에서 ContextMenu를 보여주는 항목의 위치	
		
		 switch(item.getItemId()){
		  
		  case R.id.call:
//		   Toast.makeText(this, list.get(position).call, Toast.LENGTH_SHORT).show();
			  
		   AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
				alert.setPositiveButton("확인",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String tel_o = list.get(position).call.replace("-", "");
								String tel = "tel:" + tel_o;
								startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));								
							}
						});

				alert.setNegativeButton("취소",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss(); // 창 닫기
							}
						});
//				alert.setTitle("우리 공부하자");
				alert.setMessage(list.get(position).call + "로 전화를 거시겠습니까?");
				alert.show();		   
		   break;
		   
		  case R.id.more:		   
//		   Toast.makeText(this, "http://168.131.22.123:8080/menuDetail.do?no=" + list.get(position).no, Toast.LENGTH_SHORT).show();
		  
		   Intent intent = new Intent(MainActivity.this, sub.class);	// action, uri
		   intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		   intent.putExtra("no", list.get(position).no + "");	// name, value
		   startActivity(intent);
//		   break;
		   
//		  case R.id.info:
//		   
//		   Toast.makeText(this, mDatas.get(index)+" Info", Toast.LENGTH_SHORT).show();
//		   break;		  
		  }		 
		return false;
	};
	
	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}
		
}
