package com.example.customlistview;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	// 검색했을 때 리스트

	Context context;
	private LayoutInflater inflater;
	private ArrayList<ItemVO> items = null;
	private ArrayList<ItemVO> search = null;
	// private List<IconTextList> items = null;
	// private List<IconTextList> search = null;
	private int layout;

	public ListViewAdapter(Context context, int layout, ArrayList<ItemVO> items) {
		this.context = context;
		this.items = items;
		this.search = new ArrayList<ItemVO>(items);
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = layout;
	}

	public class ViewHolder {
		// ImageView flightIcon;
		// TextView text;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		return null;
		// return items.get(position).getName();
	}

	@Override
	public long getItemId(int position) {
		return 0;
		// return position;
	}

	@Override
	public View getView(final int pos, View view, ViewGroup parent) {

		final ItemVO list = items.get(pos);

		if (view == null) {
			// TODO
			view = inflater.inflate(R.layout.listview, null);
		}
		
		ImageView img = (ImageView) view.findViewById(R.id.img);

		UniversalImageLoaderManager.displayImage(context, items.get(pos)
				.getP_poster_s(), img);

		TextView text1 = (TextView) view.findViewById(R.id.text1);
		String text11 = "[" + items.get(pos).category_s + "] "
				+ items.get(pos).name;
		text1.setText(text11);
		// text1.setTypeface(typeFace);

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
	
	// 검색
	public void filter(String charText) {
		items.clear();
		if (charText.length() == 0) {	// 검색어가 없을 때는 다 보여 준다
			items.addAll(search);
		} else {						// 검색어가 있으면
			for (ItemVO IconTextList : search) {
				String name = IconTextList.getName();
				// 입력한 글자(charText)가 제목(name)에 포함돼 있으면 검색 리스트에 추가한다				
				if (name.toLowerCase(Locale.getDefault()).contains(charText)) {
					items.add(IconTextList);
				}
				
				String place = IconTextList.getPlace();
				// 입력한 글자(charText)가 제목(name)에 포함돼 있으면 검색 리스트에 추가한다				
				if (place.toLowerCase(Locale.getDefault()).contains(charText)) {
					items.add(IconTextList);
				}
				
				String category_s = IconTextList.getCategory_s();
				if (category_s.toLowerCase(Locale.getDefault()).contains(charText)) {
					items.add(IconTextList);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	
}