package com.example.customlistview;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	
	// 어댑터: 커스텀 리스트뷰를 뿌려주는 곳
	
	// 전역변수
	private Context context; // inflate하기 위해
	private LayoutInflater inflater; // 합쳐주는 작업
	private ArrayList<ItemVO> items; // 데이터
	private int layout; // 레이아웃

	public CustomAdapter(Context context, int layout, ArrayList<ItemVO> items) {
		this.context = context;
		this.layout = layout;
		this.items = items;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int pos, View view, ViewGroup parent) {
		
		if (view == null) {
			view = inflater.inflate(layout, parent, false);
		}
		
		ImageView img = (ImageView) view.findViewById(R.id.img);
		String imgUrl = items.get(pos).p_poster_s;
		UniversalImageLoaderManager.displayImage(context, imgUrl, img);

		// 이미지 누르면 sub.class로 넘어가서 유튜브 플레이어 뜨게
		// img.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// Intent intent = new Intent(context, Sub.class); // action, uri
		// intent.putExtra("id", items.get(pos).getId()); // name, value
		// context.startActivity(intent);
		// }
		// });

	
//		Typeface typeFace = Typeface.createFromAsset(getAssets(), "NANUMBARUNGOTHIC.TTF");
		
		TextView text1 = (TextView) view.findViewById(R.id.text1);		
		String text11 = "[" + items.get(pos).category_s + "] "
				+ items.get(pos).name;
		text1.setText(text11);
//		text1.setTypeface(typeFace);

		TextView text2 = (TextView) view.findViewById(R.id.text2);
		text2.setText(items.get(pos).place);

		TextView text3 = (TextView) view.findViewById(R.id.text3);
		String text33 = items.get(pos).starttime + " ~ "
				+ items.get(pos).endtime;
		text3.setText(text33);

		String call = items.get(pos).call;		
		
		 if (call != null || call != "") {
		TextView text4 = (TextView) view.findViewById(R.id.text4);
		text4.setText(call);
		TextView text5 = (TextView) view.findViewById(R.id.text5);
		text5.setTextColor(Color.RED);
		text5.setText("☎");
		 }
		 
	 
		 
		return view;
	}
}