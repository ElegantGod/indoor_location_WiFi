package com.love.wei;

import java.util.Random;

import javax.security.auth.PrivateCredentialPermission;

import android.R.color;
import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;  
import android.view.SurfaceView;  
  
/** 
 * 使用SurfaceView提供给需要直接画像素而不是使用画窗体部件的应用使用。 
 * 而每个Surface创建一个Canvas对象，用来管理View在Surface上的绘画操作。 
 */  
public class TestSurfaceView extends SurfaceView implements  
        SurfaceHolder.Callback {  
    // 控制循环  
    private boolean mbLoop = false;  
  
    // 定义SurfaceHolder对象  
    private SurfaceHolder mSurfaceHolder;
    private int miCount = 0;  
    public int x = 50, y = 50;  
    private int mWidth = 1280,mHeight = 720; 
    private int minKey = 0;
    float[] pos;
    private Bitmap mBitmap;
      
    public TestSurfaceView(Context context) {  
        super(context);  
        mSurfaceHolder = this.getHolder();  
        mSurfaceHolder.addCallback(this);  
        this.setFocusable(true);  
    }  
  
    public void setDisplayWH(int w, int h) {  
        mWidth = w;  
        mHeight = h;  
    }  
    
    public void setMinKey(int minKey){
    	this.minKey = minKey;
    }
  
    public void setBitmap(Bitmap bitmap) {  
        this.mBitmap = bitmap;  
    }  
  
    @Override  
    public void surfaceChanged(SurfaceHolder holder, int format, int width,  
            int height) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void surfaceCreated(SurfaceHolder holder) {  
        mbLoop = true;  
  
        Thread th = new Thread(new Runnable() {  
  
            @Override  
            public void run() {  
                while (mbLoop){  
                    try {  
                        Thread.sleep(200);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
  
                    synchronized( mSurfaceHolder ){  
                        drawBitmap();  
                        DrawData();  
                    }  
                }  
            }  
        });  
        th.start();  
    }  
  
    @Override  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        mbLoop = false;  
    }  
  
    private void drawBitmap() {  
        // 锁定画布，得到canvas  
        if (mSurfaceHolder == null || this.mBitmap == null)  
            return;  
  
        Canvas canvas = mSurfaceHolder.lockCanvas();  
        if (canvas == null) {  
            return;  
        }  
  
        // 绘图  
        Paint paint = new Paint();  
        paint.setAntiAlias(true);  
        paint.setColor(Color.BLUE);  
  
        canvas.drawBitmap(this.mBitmap, 0, 0, paint);  
  
        // 绘制后解锁，绘制后必须解锁才能显示  
        mSurfaceHolder.unlockCanvasAndPost(canvas);  
    }  
  
    // 绘图方法  
    private void DrawData() {  
        if (mSurfaceHolder == null)  
            return;  
  
        // 锁定画布，得到canvas  
        Canvas canvas = mSurfaceHolder.lockCanvas();  
        if (canvas == null) {  
            return;  
        }  
  
        if (miCount < 100) {  
            miCount++;  
        } else {  
            miCount = 0;  
        }  
  
        // 绘图  
        Paint mPaint1 = new Paint();  
        Paint mPaint2 = new Paint();
        mPaint1.setAntiAlias(true);  
        mPaint1.setColor(Color.WHITE);  
  
        // 绘制矩形--清屏作用  
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint1);  
                  
        Color color = null;
        /*for (int i=1 ;i < 11; i++)
        	for(int j = 1; j <11; j++){
        		//update();
        		mPaint.setColor(color.RED);
        		float r = (float) (mWidth*0.05);
        		float cx = (float) (mWidth*0.1*i-r);
        		float cy = (float) (mWidth*0.1*j-r);
        		canvas.drawCircle(cx, cy, r,  mPaint);
        	}*/
        
        
        for (int j=0; j<15; j++)
        	for(int i=0; i<8; i++){
        		mPaint1.setColor(color.RED);
        		mPaint1.setStyle(Style.STROKE);
        		float X0=(float) (i*mWidth*0.125);
        		float Y0=(float) (j*mHeight*0.044);
        		float X1=(float) ((i+1)*mWidth*0.125);
        		float Y1=(float) ((j+1)*mHeight*0.044);
        		float pointx = (X0 + X1)/2;
        		float pointy = (Y0 + Y1)/2;
        		if(minKey==j*8+i+1){
        			mPaint1.setColor(Color.GREEN);
        			mPaint1.setStyle(Style.FILL);
        		}
        		canvas.drawRect(X0, Y0, X1, Y1,  mPaint1);
        		mPaint2.setColor(color.RED);
        		mPaint2.setTextSize(30);
        		if(j*8+i<9)
        			pos = new float[]{pointx-10,pointy+10};
        		else if(j*8+i<99)
        			pos = new float[]{pointx-30,pointy+10, pointx,pointy+10};
        		else
        			pos = new float[]{pointx-45,pointy+10, pointx-15,pointy+10, pointx+15, pointy+10};
        		canvas.drawPosText(Integer.toString(j*8+i+1), pos, mPaint2);
        	}
 
        // 绘制矩形--   
          
        // 绘制后解锁，绘制后必须解锁才能显示  
        mSurfaceHolder.unlockCanvasAndPost(canvas);  
    }
    
    /*private void update() {
		// TODO Auto-generated method stub
		Random random = new Random();
		Int r = random.nextInt(256);
		Int g = random.nextInt(256);
		Int b = random.nextInt(256);
		mColor = Color.rgb(r, g, b);
		
	}*/
} 
