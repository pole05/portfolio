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
		
		// ���α׷��� ��
		dlg = ProgressDialog.show(this, "�ε���", "��ٷ��ּ���.");
		
		// �ȳ� �佺Ʈ
		Toast.makeText(this, "��� �����ø� �� �ڼ��� ������ ���� �� �ֽ��ϴ�.", 35000).show();
		
		// �ι� ������ ����
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		// �˻� �Է���
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
		
		// ���ؽ�Ʈ �޴��� �������� ���
		registerForContextMenu(listView);
		
		
		// �˻�
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

		
		// ������ ���� (��Ʈ��ũ ���� �κ�)
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = Util // util Ŭ������ request �޼ҵ�
						.request("http://168.131.22.123:8080/mobile/MenuList.do");	// ���⼭ �����͸� �޾ƿ´�

//				final ArrayList<ItemVO> list = new ArrayList<ItemVO>();
				
				try {
					JSONObject obj = new JSONObject(result);
					JSONArray items = obj.getJSONArray("list");
										
					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);	//[]���� �迭 �ϳ�
						
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
				// ������ ���� ������: ������ ���� ������� �ʰ�, �� ������ �ڵ尡 ����� �� ����ȴ�
					@Override
					public void run() {
						// ����Ϳ� ����Ʈ�� ������ ��
						 adapter = new ListViewAdapter(MainActivity.this, R.layout.listview, list);						 
						 listView.setAdapter(adapter);

						// ���α׷��� �� 
						dlg.dismiss();						
					}
				});
				
			}
		}).start();	
		// ������ ��		
			
		
		// ����Ʈ�� ������ ��Ʃ�� �������� �̵��ϰ�
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
	// oncreate ��
		
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		getMenuInflater().inflate(R.menu.list_item_context_menu, menu);		
	}
	

	// Context �޴��� ����� View(���⼭�� ListView)�� Ŭ���Ǿ��� �� �ڵ����� ȣ��Ǵ� �޼ҵ�
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();

		final int position = info.position;
		// AdapterView�ȿ��� ContextMenu�� �����ִ� �׸��� ��ġ	
		
		 switch(item.getItemId()){
		  
		  case R.id.call:
//		   Toast.makeText(this, list.get(position).call, Toast.LENGTH_SHORT).show();
			  
		   AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
				alert.setPositiveButton("Ȯ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String tel_o = list.get(position).call.replace("-", "");
								String tel = "tel:" + tel_o;
								startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));								
							}
						});

				alert.setNegativeButton("���",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss(); // â �ݱ�
							}
						});
//				alert.setTitle("�츮 ��������");
				alert.setMessage(list.get(position).call + "�� ��ȭ�� �Žðڽ��ϱ�?");
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
