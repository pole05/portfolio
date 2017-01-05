package com.example.customlistview;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexActivity extends Activity {
	
	Handler h;// �ڵ鷯 ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ��Ʈ��ȭ���̹Ƿ� Ÿ��Ʋ�ٸ� ���ش�
		setContentView(R.layout.activity_index);
		h = new Handler(); // �����̸� �ֱ� ���� �ڵ鷯 ����
		h.postDelayed(mrun, 1800); // ������ ( ����� ��ü�� mrun, �ð� 1��)
		
		
		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);
		Typeface face1 = Typeface.createFromAsset(getAssets(), "NanumSquareB.ttf");
		Typeface face2 = Typeface.createFromAsset(getAssets(), "NanumPen.ttf");
		text1.setTypeface(face1);
		text2.setTypeface(face2);

//		ImageView intro_gif = (ImageView) findViewById(R.id.intro);
//		GlideDrawableImageViewTarget imageViewTarget
//			= new GlideDrawableImageViewTarget(index2.png);
//		Glide.with(this).load(R.drawable.index2.png).into(imageViewTarget);

	}

	Runnable mrun = new Runnable() {
		@Override
		public void run() {
			Intent i = new Intent(IndexActivity.this, MainActivity.class); // ����Ʈ ����(�� ��Ƽ��Ƽ,
																// ���� ������ ��Ƽ��Ƽ)
			startActivity(i);
			finish();
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			// overridePendingTransition �̶� �Լ��� �̿��Ͽ� fade in,out ȿ������. ������ �߿�
		}
	};

	// ��Ʈ�� �߿� �ڷΰ��⸦ ���� ��� �ڵ鷯�� ������� �ƹ��� ���� ����� �κ�
	// �� ������ ��Ʈ�� �� �ڷΰ��⸦ ������ ��Ʈ�� �Ŀ� Ȩȭ���� ����.
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		h.removeCallbacks(mrun);
	}
}