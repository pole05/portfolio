package com.example.study;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FourthActivity extends Activity implements
		OnChartValueSelectedListener {
	
	private BarChart mChart;
	private ArrayList<BarEntry> yVals1;
	MyDBHelper myHelper;
	SQLiteDatabase sqlDB;
	
	private Button Again, Close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fourth);
		
		//���� ����ǥ���� �Ұ�
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Again = (Button) findViewById(R.id.Again);
		Close = (Button) findViewById(R.id.Close);
		
		Typeface face = Typeface.createFromAsset(getAssets(),
				"NANUMBARUNPENB.TTF");
		Again.setTypeface(face);
		Close.setTypeface(face);
		
		Again.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FourthActivity.this, SecondActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		Close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				moveTaskToBack(true); 
				finish(); 
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});

		mChart = (BarChart) findViewById(R.id.chart1);
		mChart.setOnChartValueSelectedListener(this); // mChart�� ��ҵ��� ���õ� �� �ְ� ����

		mChart.setPinchZoom(false); // ��ü ��Ʈ�� Ȯ����Ұ����ϰ� �ϴ� �ڵ�--�̰�� x��, y���� ������!

		mChart.setDragEnabled(true); // �巡��(��ġ �� ���� ���� ����)�� �����ϰ� �ϴ� �ڵ�
		mChart.setScaleEnabled(true); // ��Ʈ�� ũ�⸦ ������ �� �ְ� �ϴ� �ڵ�

		mChart.setDrawGridBackground(false); // ��Ʈ ������ �׸���(����)�� �׷����� �ʰ� �ϴ� �ڵ�
		mChart.setDrawBarShadow(false); // ���� �׷����� �׸��ڰ� ������ �ʰ� �ϴ� �ڵ�

		mChart.setDrawValueAboveBar(true); //���� �׷��� ���� DrawValue(�ప!)
		
		YAxis leftAxis = mChart.getAxisLeft();	//�׷��� y���� ���ʿ� ����!
		leftAxis.setDrawGridLines(false); //y���� ���ڼ��� �Ȼ�����ϴ� �ڵ�
		leftAxis.setAxisMinValue(0);  //y�� �ּҰ��� 0���� ����!
		leftAxis.setValueFormatter(new YAxisValueFormatter() {
			
			private DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
		    
			@Override
			public String getFormattedValue(float value, YAxis yAxis) {
				
				return mFormat.format(value);
			}
			
		});
		mChart.getAxisRight().setEnabled(false);  //��Ʈ�� �����ʿ� ������ y���� �׸��� ����

		XAxis xLabels = mChart.getXAxis(); // x�ప���� ������ ���� ����
		xLabels.setPosition(XAxisPosition.BOTTOM); // x���� ��ġ�� ��Ʈ�� ��������!
		
		//x�ప���� ������ ���� �̸����� �迭�� �־���!
		myHelper = new MyDBHelper(this);
		sqlDB = myHelper.getReadableDatabase();
		Cursor cursor;
		cursor = sqlDB.rawQuery("SELECT DATE1 FROM TODAYSTUDY GROUP BY DATE1;", null);
		
		ArrayList<String> xVal = new ArrayList<String>();
		
		while(cursor.moveToNext()){
			xVal.add(cursor.getString(0));
		}
		
		// y�� �� ������
		yVals1 = new ArrayList<BarEntry>();
		ArrayList<Integer> val1= new ArrayList<Integer>();
		ArrayList<Integer> val2= new ArrayList<Integer>();
		ArrayList<Integer> val3= new ArrayList<Integer>();
		
		cursor = sqlDB.rawQuery("SELECT FTIME FROM TODAYSTUDY WHERE NUM IN (SELECT MAX(NUM) FROM TODAYSTUDY GROUP BY DATE1);", null);
		while(cursor.moveToNext()){
			val1.add(cursor.getInt(0));
		}
		
		cursor = sqlDB.rawQuery("SELECT MTIME FROM TODAYSTUDY WHERE NUM IN (SELECT MAX(NUM) FROM TODAYSTUDY GROUP BY DATE1);", null);
		while(cursor.moveToNext()){
			val2.add(cursor.getInt(0));
		}
		cursor = sqlDB.rawQuery("SELECT SUM(FTIME) FROM TODAYSTUDY GROUP BY DATE1;", null);
		while(cursor.moveToNext()){
			val3.add(cursor.getInt(0));
		}
		
		for (int i = 0; i < xVal.size(); i++) {
			
			yVals1.add(new BarEntry(new float[] {
					val1.get(i), val2.get(i)-val1.get(i),
					val3.get(i)}, i));
		}
		cursor.close();
		sqlDB.close();
		
		//��Ʈ�� ������(������ ����׷����� �ǹ��ϴ� �ٸ� ��Ÿ���� ����)�� ��ġ�� ���� ����
		Legend l = mChart.getLegend();
		
		l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
		l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
		l.setFormSize(8f);
		l.setFormToTextSpace(4f);
		l.setXEntrySpace(6f);
		
		
		ArrayList<IBarDataSet> xset = new ArrayList<IBarDataSet>();

		BarDataSet set_i = new BarDataSet(yVals1, "");
		set_i.setColors(getColors());
		set_i.setStackLabels(new String[] { "��� �� ���η�", "�̴� ���η�", "1�� �� ���η�" });

		xset.add(set_i);
		BarData data = new BarData(xVal, xset);
		data.setValueTextColor(Color.BLACK);
		data.setValueTextSize(15);
		face = Typeface.createFromAsset(getAssets(), "NANUMBARUNGOTHIC.TTF");
		data.setValueTypeface(face);
		
		mChart.setBackgroundColor(Color.WHITE);
		mChart.animateY(500);
		mChart.setData(data);
		mChart.setAutoScaleMinMaxEnabled(true);
		mChart.setDrawHighlightArrow(false);
		mChart.setHorizontalScrollBarEnabled(true);
		mChart.invalidate();
		mChart.setDescription("������ �ð�/��");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fourth, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		 int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	}


	private int[] getColors() {

		int stacksize = 3;

		// have as many colors as stack-values per entry
		int[] colors = new int[stacksize];

		for (int i = 0; i < colors.length; i++) {
			colors[i] = ColorTemplate.JOYFUL_COLORS[i];
		}

		return colors;
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		BarEntry bar = yVals1.get(dataSetIndex);

		if (bar.getVals() != null)
			Toast.makeText(FourthActivity.this,
					"������ �ð� : " + bar.getVals()[h.getStackIndex()], 0)
					.show();
		else
			Toast.makeText(FourthActivity.this, "�ڷ� ����", 0).show();
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}
}
