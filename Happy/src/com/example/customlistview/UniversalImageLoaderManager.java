package com.example.customlistview;

import java.io.File;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class UniversalImageLoaderManager {

	private final static Boolean pauseOnScroll = false; 
	private final static Boolean pauseOnFling = true; 
	
	public static void displayImage(Context context, String imgURI, ImageView view){
		
		if(!ImageLoader.getInstance().isInited()){
			UniversalImageLoaderManager.init(context);
		}

		ImageLoader.getInstance()
		.displayImage(imgURI
				, view
				, ImageLoaderHelper.getBaseOptions());
		
	}
	
//	public static void displayImageForWide(Context context, String imgURI, ImageView view){
//		
//		if(!ImageLoader.getInstance().isInited()){
//			UniversalImageLoaderManager.init(context);
//		}
//		
//		ImageLoader.getInstance()
//		.displayImage(imgURI
//				, view
//				, ImageLoaderHelper.displayImageForWide());
//		
//	}
	
	public static void displayImageForPromotion(Context context, String imgURI, ImageView view){
		
		if(!ImageLoader.getInstance().isInited()){
			UniversalImageLoaderManager.init(context);
		}
		
		ImageLoader.getInstance()
		.displayImage(imgURI
				, view
				, ImageLoaderHelper.getBaseOptionsForPromotion());
		
	}
	
	public static void init(Context context){
		File cacheDir;
		
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	        cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"neongall");
	    else
	        cacheDir=context.getCacheDir();
	    if(!cacheDir.exists())
	        cacheDir.mkdirs();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(
				Thread.NORM_PRIORITY-2)
				.memoryCache(new LruMemoryCache(50 * 1024 * 1024))
//		        .memoryCacheSize(500 * 1024 * 1024)
		        .memoryCacheSizePercentage(15) // default
		        .threadPoolSize(3) // default
		        .discCache(new UnlimitedDiscCache(cacheDir)) // default
		        .discCacheSize(500 * 1024 * 1024)
		        .discCacheFileCount(1000)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(
				new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.writeDebugLogs() // Remove for release app
			.build();
			// Initialize ImageLoader with configuration.
			ImageLoader.getInstance().init(config);
	}
	
	public static void applyScrollListener(ListView view) {
        view.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
    }
}
