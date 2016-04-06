package com.example.administrator.rounddemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.iamgedemo.R;

/**
 * Created by Administrator on 2016/4/2.
 */
public class RoundView extends View {

    private Bitmap mBitmap;//需要被圆角转换的图片
    private Bitmap mOutBitmap;//输出出去的Bitmap
    private Paint mPaint;//画笔

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public RoundView(Context context) {
        super(context);
        initViews();
    }

    private void initViews()
    {
        setLayerType(LAYER_TYPE_SOFTWARE,null);//关闭掉硬件加速  防止绘制出现问题
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);//需要被圆角化的图片
        mBitmap = Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getWidth());
        /**
         * 现在开始根据这个图片重新创建一模一样大小的Bitmap  进行绘图
         */
        mOutBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mOutBitmap);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 在Android 中首先绘制上去的是Dst  之后才是Src
         */
        //在画布上绘制Dst  绘制圆角矩形
        canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getWidth(),mBitmap.getHeight(),mPaint);
        Log.e("Connor", mBitmap.getWidth() + "----" + mBitmap.getHeight());
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //在画画布上绘制Src 绘制图片
        canvas.drawBitmap(mBitmap,0,0,mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        matrix.setScale(0.2f,0.2f);
        canvas.drawBitmap(mOutBitmap,matrix,null);
    }
}
