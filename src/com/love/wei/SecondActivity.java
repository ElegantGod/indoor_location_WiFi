package com.love.wei;

import java.text.Normalizer;
import java.util.List;
import java.io.IOException; 
import java.net.NetPermission;



import com.love.yan.*;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondActivity extends Activity{
	private SQLiteDatabase db;
	private Cursor cursor;
	private StringBuffer sb;
	private TextView ssidTV;
	private Button test;
	private Button train;
	private Button location;
	private EditText number;
	private DBHelper mDBHelper;
	private WifiAdmin mWifiAdmin;    
    private List<ScanResult> list;    
    private ScanResult mScanResult;
    private Integer[] testData;
    private Integer[][] trainData;
    private float[] normTestData;
    private float[][] normTrainData;
    private Integer[] normMin;
    private Integer[] normMax;
    private float diff;
    private int minKey;
    private int listLength;
    private int maxNum;
    private int rssNum;
    private float minValue;
    private float diff2;
    private float[] result;
    private StringBuffer text;
    private String[] bssidString ={"bc:d1:77:fb:eb:1a","40:16:9f:9b:4a:5e","82:c7:c0:00:60:a5",
    		"22:17:7b:2a:d9:13","d0:c7:c0:00:61:75","00:17:7b:2a:d9:13","02:17:7b:2a:d9:13",
    		"00:23:cd:83:20:ea","28:e3:47:ff:07:78","56:fd:52:74:96:93"};
    //407-network,MERCURY_9B4A5E,Guest-2.4G,XTU,CIE,CMCC,CMCC-EDU,000,chengwei,weiyanh
    //private String[] ssidString ={"407-Network","MERCURY_9B4A5E","Guest_2.4GHz","XTU","CIE",
    	//	"CMCC","CMCC-EDU","000","chengwei","weiyanhua"};
    private String[] ssidString ={"407-Network","MERCURY_9B4A5E","NETCORE",
    		"CMCC-EDU","000","chengwei","weiyanhua"};
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_main);
		mWifiAdmin = new WifiAdmin(SecondActivity.this);
		mDBHelper = new DBHelper(SecondActivity.this);
		sb = new StringBuffer();
		ssidTV = (TextView) findViewById(R.id.ssid);
		test = (Button) findViewById(R.id.test);
		location = (Button) findViewById(R.id.location);
		train = (Button) findViewById(R.id.train);
		number = (EditText) findViewById(R.id.number);
		
		
		test.setOnClickListener(new MyListener());
		train.setOnClickListener(new MyListener());
		location.setOnClickListener(new MyListener());
		
	}
		private class MyListener implements OnClickListener{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(SecondActivity.this, "获取wifi信息：", Toast.LENGTH_SHORT).show();
				//Toast.makeText(SecondActivity.this, "一维："+trainData.length, Toast.LENGTH_LONG).show();
				switch (v.getId()) {
				case R.id.test:
					getTestWifi();
					break;
				case R.id.train:
					String num = number.getText().toString();
					maxNum = Integer.valueOf(num).intValue();
					Toast.makeText(SecondActivity.this, "网格数目："+maxNum, Toast.LENGTH_SHORT).show();
					getTrainWifi();
					break;
				case R.id.location:
					showTestWifi();
					//切换到ThirdActivity界面并将result信息通过Bundle类传递过去
					Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
					Bundle bundle = new Bundle();
					bundle.putFloatArray("result", result);
					bundle.putInt("minKey", minKey);
					intent.putExtras(bundle);
					startActivity(intent);
					
					break;
				default:
					break;
				}
			}
		}
	private void getTrainWifi() {
		// TODO Auto-generated method stub
		trainData = new Integer[maxNum][70];
		normMax = new Integer[7];
		normMin = new Integer[7];
		for(int i=0; i<trainData.length; i++){
			Cursor cursor = mDBHelper.selectNumber(Integer.toString(i+1));
			cursor.moveToFirst();
			for(int j=0; j<trainData[i].length; j++){
				trainData[i][j]=cursor.getInt(2);
				cursor.moveToNext();
			}
		}
		//
		
		for(int k=0; k<trainData.length; k++){
			for(int n=0; n<7; n++){
				if(k==0){
					normMax[n]=trainData[k][n];
					normMin[n]=trainData[k][n];
				}
				
				for(int m=0; m<70; m=m+7){
					if(normMax[n]<trainData[k][m+n])
						normMax[n]=trainData[k][m+n];
					
					if(normMin[n]>trainData[k][m+n])
						normMin[n]=trainData[k][m+n];
				}
			}
		}
		
	}
	
	
	
	
	
	protected void getTestWifi(){
	      if(sb!=null){  
	          sb=new StringBuffer();  //清除测试信息
	      }  
	      rssNum = 0;
	      //开始扫描网络  
	      mWifiAdmin.startScan();  
	      list=mWifiAdmin.getWifiList();  
	      
	      if(list!=null){  
	    	  testData = new Integer[70];
	    	  for(int iter=0;iter<ssidString.length;iter++){
	                  for(int i=0;i<list.size();i++){  
	                      mScanResult=list.get(i);  
	                      if(mScanResult.SSID.equals(ssidString[iter])){
	                    	  for(int j=0;j<10;j++){
	                    		  testData[7*j+iter]=mScanResult.level;
	                    	  }
	                    	  rssNum++;
	                    	  break;
	                      }
	                  }
	    	  }
	      }
	      Toast.makeText(SecondActivity.this, "RSS数目："+rssNum, Toast.LENGTH_SHORT).show();
		}
	
	//在线测试
	private void showTestWifi() {
		// TODO Auto-generated method stub
		//与测试数据比较，得出最大值和最小值

		for (int i=0;i<normMax.length;i++){
			if(testData[i]>normMax[i])
				normMax[i]=testData[i];
			if(testData[i]<normMin[i])
				normMin[i]=testData[i];
		}
		//归一化处理
		normTestData = new float[70];
		normTrainData = new float[maxNum][70];
		
		for(int k=0; k<normTrainData.length; k++){
			for(int n=0; n<7; n++){
				int normDiff=normMax[n]-normMin[n];
				for(int m=0; m<70; m=m+7){
					normTrainData[k][m+n]=(float)(trainData[k][m+n]-normMin[n])/(float)normDiff;//归一化
					normTestData[m+n]=(float)(testData[m+n]-normMin[n])/(float)normDiff;					
				}
			}
		}

		diff =0;
		result = new float[maxNum];
		for(int m=0; m<normTrainData.length; m++){
			diff2 =0;
			for(int n=0; n<normTrainData[m].length; n++){
				diff = normTrainData[m][n]-normTestData[n];
				diff2 = diff2+diff*diff;
			}
			result[m]=diff2;
		}
		//Toast.makeText(SecondActivity.this, "normMax长度"+normMax.length+".."+normMin.length, Toast.LENGTH_SHORT).show();

		minValue = result[0];
		minKey = 0;
		for(int i=0;i<result.length;i++){
			if(result[i]<minValue){
				minValue = result[i];
				minKey = i;
			}
		}
		minKey = minKey+1;
		Toast.makeText(SecondActivity.this, "当前位置："+minKey, Toast.LENGTH_LONG).show();
		text = new StringBuffer();
		for(int i=0;i<maxNum;i++){
			text = text.append(i+1+" : "+result[i]+"\n");
		}
		ssidTV.setText(minKey+"\n"+"所有距离："+"\n"+text);

	}
	
}
