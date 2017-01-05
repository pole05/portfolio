package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class sub extends Activity {	
	
	private WebView WebView01;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
				
		WebView01 = (WebView) findViewById(R.id.webView);
		
		WebView01.setFocusable(false);
		WebView01.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		WebView01.getSettings().setLoadWithOverviewMode(true);
		WebView01.getSettings().setUseWideViewPort(true);
		WebView01.getSettings().setBuiltInZoomControls(true);
		
		WebView01.setVerticalScrollBarEnabled(false);
		WebView01.setVerticalScrollbarOverlay(false);
		WebView01.setHorizontalScrollBarEnabled(false);
		WebView01.setHorizontalScrollbarOverlay(false);
		WebView01.setInitialScale(100);
		
		
		WebSettings webSettings = WebView01.getSettings();
		webSettings.setJavaScriptEnabled(true);

		Intent intent = getIntent();
		String no = intent.getStringExtra("no");
		
		WebView01.loadUrl("http://168.131.22.123:8080/mobile/MenuDetail.do?no=" + no);
	};
		
}