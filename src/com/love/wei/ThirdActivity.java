package com.love.wei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class ThirdActivity extends Activity{
	private DisplayMetrics dm;
	private TestSurfaceView mTestSurfaceView;
	private int mWidth;
	private int mHeight;
	private int minKey;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_main);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		minKey = bundle.getInt("minKey");
		startView();
	}

private void startView() {
		// TODO Auto-generated method stub
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mWidth = dm.widthPixels;  
	    mHeight = dm.heightPixels;  
	    mTestSurfaceView = new TestSurfaceView(this);
	    mTestSurfaceView.setDisplayWH(mWidth, mHeight);
	    mTestSurfaceView.setMinKey(minKey);
	    setContentView(mTestSurfaceView);
	}

}
