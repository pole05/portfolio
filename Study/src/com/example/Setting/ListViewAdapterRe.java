package com.example.Setting;

import com.example.study.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterRe extends BaseAdapter {
	Content[] listArray;
	Context context;
	SharedPreferences setting;

	public ListViewAdapterRe(Context context, Content[] listArray) {
		this.context = context;
		this.listArray = listArray;
	}

	@Override
	public int getCount() {
		return listArray.length;
	}

	@Override
	public Object getItem(int pos) {
		return listArray[pos];
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(final int pos, View convertView, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_view, null);
		}
		ImageView image = (ImageView) convertView.findViewById(R.id.img);
		image.setImageResource(listArray[pos].getImage());
		TextView text = (TextView) convertView.findViewById(R.id.text);
		TextView textre = (TextView) convertView.findViewById(R.id.textre);
		text.setText(listArray[pos].getText());
		Activity act0 = (Activity)context;
		Typeface face = Typeface.createFromAsset(act0.getAssets(),
				"NANUMBARUNPENB.TTF");
		text.setTypeface(face);
		textre.setTypeface(face);
		if(pos == 0){
			Activity act = (Activity)context;
			setting = act.getSharedPreferences("setting", 0);
			String d = setting.getString("D","");
			textre.setText(d);
			textre.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

}
