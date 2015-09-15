package com.love.wei;

import java.util.List;  
import com.love.yan.*;

import android.app.Activity;  
import android.content.Intent;
import android.net.wifi.ScanResult;  
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.EditText;
import android.widget.TextView;  
import android.widget.Toast;  
  
public class FirstActivity extends Activity {  
    /** Called when the activity is first created. */  
      	private TextView allNetWork;    
        private Button scan ;    
        private Button start;    
        private Button sure;    
        private Button check;  
        private Button read;
        private Button button1;
        private EditText number;
        private String num;
        private String delSsid;
        private WifiAdmin mWifiAdmin;    
        private List<ScanResult> list;    
        private ScanResult mScanResult;    
        private StringBuffer sb=new StringBuffer();  
        private DBHelper helper;
        private DataWifi mWifiData;
        private int a ,b,c,d,e,f,g,h,I,j;
        private String[] bssidString ={"bc:d1:77:fb:eb:1a","40:16:9f:9b:4a:5e","82:c7:c0:00:60:a5",
        		"22:17:7b:2a:d9:13","d0:c7:c0:00:61:75","00:17:7b:2a:d9:13","02:17:7b:2a:d9:13",
        		"00:23:cd:83:20:ea","28:e3:47:ff:07:78","56:fd:52:74:96:93"};
        //407-network,MERCURY_9B4A5E,Guest-2.4G,XTU,CIE,CMCC,CMCC-EDU,000,chengwei,weiyanh
        private String[] ssidString ={"407-Network","MERCURY_9B4A5E","NETCORE",
        		"CMCC-EDU","000","chengwei","weiyanhua"};
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.first_main);  
        mWifiAdmin = new WifiAdmin(FirstActivity.this);   
        helper = new DBHelper(FirstActivity.this); 
        init();    
    }  
    public void init(){  
        allNetWork = (TextView) findViewById(R.id.allNetWork);    
        scan = (Button) findViewById(R.id.scan);    
        start = (Button) findViewById(R.id.start);    
        sure = (Button) findViewById(R.id.sure);    
        check = (Button) findViewById(R.id.check);   
        read = (Button) findViewById(R.id.read);
        number = (EditText) findViewById(R.id.number);
        button1 = (Button) findViewById(R.id.button1);
        
        scan.setOnClickListener(new MyListener());    
        start.setOnClickListener(new MyListener());    
        sure.setOnClickListener(new MyListener());    
        check.setOnClickListener(new MyListener());    
        read.setOnClickListener(new MyListener());
        button1.setOnClickListener(new MyListener());
    }  
    private class MyListener implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            switch (v.getId()) {  
            case R.id.button1:
            	a=1;b=1;//a是RSS存储次数，b是各个AP
            	num = number.getText().toString();
            	Toast.makeText(FirstActivity.this, "网格: "+num, Toast.LENGTH_SHORT).show();//浮在界面显示	
            	break;
            case R.id.scan:
                getAllNetWorkList();    
                break;  
           case R.id.start: 
                mWifiAdmin.openWifi();  
                //删除一部分SSID
                String[] delSsids = {"Guest_2.4GHz","CIE","CMCC"};
                for(int i=0;i<delSsids.length;i++){
                	delSsid = delSsids[i];
                	helper.deleteSsid(delSsid);
                }
                break;  
           case R.id.sure:
        	    saveDataWifi();  
                break;  
           case R.id.check:
               Toast.makeText(FirstActivity.this, "当前wifi状态为："+mWifiAdmin.checkState(), 1).show();  
               helper.deleteNumber(num);
               break;  
           case R.id.read:
        	   readWifiData();
        	   break;
            default:  
                break;  
            }  
        }  
          
    }  
    
    public void readWifiData(){
    	Intent intent = new Intent();//切换界面
    	intent.setClass(FirstActivity.this, SecondActivity.class);
    	startActivity(intent);
    }
    
    public void getAllNetWorkList() {
		// TODO Auto-generated method stub
    	if(sb!=null){  
            sb=new StringBuffer();  
        }  
    	mWifiAdmin.startScan();
    	list=mWifiAdmin.getWifiList();
    	c=1;//c和b的作用是一样的
        for(int iter=0;iter<ssidString.length;iter++){
        	if(list!=null){  
                for(int i=0;i<list.size();i++){  
                    mScanResult=list.get(i);  
                    if(mScanResult.SSID.equals(ssidString[iter])){
                    	mWifiData = new DataWifi(mScanResult.SSID, mScanResult.level, mScanResult.BSSID, num);
                    	sb=sb.append(mWifiData.toString()+" 特征："+c+"\n\n");//连接SSID，level····
                    	c++;
                    	break;
                    }
                }
        	}
        }
       
        allNetWork.setText("位置信息："+"\n"+sb.toString());//一直显示在界面上
	}
	public void saveDataWifi(){  
          // 每次点击扫描之前清空上一次的扫描结果    
        if(sb!=null){  
            sb=new StringBuffer();  
        }  
        b=1;
        for(int iter=0;iter<ssidString.length;iter++){
        	if(list!=null){  
                for(int i=0;i<list.size();i++){  
                    mScanResult=list.get(i);  
                    if(mScanResult.SSID.equals(ssidString[iter])){
                    	mWifiData = new DataWifi(mScanResult.SSID, mScanResult.level, mScanResult.BSSID, num);
                    	helper.add(mWifiData);
                    	sb=sb.append(mWifiData.toString()+"次数："+a+"特征："+b+"\n\n");
                    	b++;
                    	break;
                    }
                }
        	}
        }
        a++;
        allNetWork.setText("网格信息："+"\n"+sb.toString());
    }

}

