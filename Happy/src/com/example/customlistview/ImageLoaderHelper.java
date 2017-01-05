package com.example.customlistview;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageLoaderHelper {

	private static DisplayImageOptions baseOptions;

	public static DisplayImageOptions getBaseOptions() {
		if (baseOptions == null) {
			
//			Drawable beforeDraw = new ColorDrawable(Color.parseColor("#F6F6F6"));
			
//			.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			
			baseOptions = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.progress_for_imageview)
//			.showImageForEmptyUri(R.drawable.no_image)
//			.showImageOnFail(R.drawable.no_image)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.considerExifParams(true)
	        .imageScaleType(ImageScaleType.NONE)
			.displayer(new RoundedBitmapDisplayer(20))
			.displayer(new FadeInBitmapDisplayer(700))
			.build();
		}
		return baseOptions;
	}
	
//	public static DisplayImageOptions displayImageForWide() {
//		if (baseOptions == null) {
//			
//			baseOptions = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.progress_for_imageview_wide)
//			.showImageForEmptyUri(R.drawable.no_image)
//			.showImageOnFail(R.drawable.no_image)
//			.cacheInMemory(true)
//			.cacheOnDisc(true)
//			.considerExifParams(true)
//	        .imageScaleType(ImageScaleType.NONE)
//			.displayer(new RoundedBitmapDisplayer(20))
//			.displayer(new FadeInBitmapDisplayer(700))
//			.build();
//		}
//		return baseOptions;
//	}

	public static DisplayImageOptions getBaseOptionsForPromotion() {
		if (baseOptions == null) {
			
			Drawable beforeDraw = new ColorDrawable(Color.parseColor("#F6F6F6"));

			baseOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(beforeDraw)
//			.showImageForEmptyUri(R.drawable.no_image)
//			.showImageOnFail(R.drawable.no_image)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.considerExifParams(true)
			.imageScaleType(ImageScaleType.NONE)
			.displayer(new RoundedBitmapDisplayer(20))
			.displayer(new FadeInBitmapDisplayer(700))
			.build();
		}
		return baseOptions;
	}
	
	public static AnimateFirstDisplayListener getAnimateFirstDisplayListener() {
		return new AnimateFirstDisplayListener();
	}

	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 1000);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
