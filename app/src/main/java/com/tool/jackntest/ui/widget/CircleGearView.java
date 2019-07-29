package com.tool.jackntest.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tool.jackntest.R;
import com.tool.jackntest.utils.DensityUtil;


public class CircleGearView extends View {
    private Context mContext;
    /** 画笔对象的引用 */
    private Paint paint;

    /** 圆环的颜色 */
    private int roundColor;
    /** 圆环进度的颜色 */
    private int roundProgressColor;
    /** 圆环的宽度 */
    private float roundWidth;
    /** 最大进度 */
    private int max;
    /** 当前进度 */
    private float progress = 1;
    /**  100/显示进度 */
    private int mProgressShow = 1;
    /** 中间进度百分比的字符串的颜色*/
    private int textColor;
    /** 中间进度百分比的字符串的字体*/
    private float textSize;
    /** 点的半径*/
    //private float pointRadius;
    /** 空心点的宽度*/
    //private float pointWidth;
    /**有记录的时间段*/
    private int[] hours;


    private long mOuterRoundTime = 2000;//毫秒
    private double mOuterRoundProgress = 0f;//外圈进度
    private boolean mOuterSences = true; //true 正向----false方向


    public CircleGearView(Context context) {
        this(context, null);
    }

    public CircleGearView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleGearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速
        this.setWillNotDraw(false);                    // 调用此方法后，才会执行 onDraw(Canvas) 方法
        paint = new Paint();

        //获取自定义属性和默认值
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CGViewStyleable);
        roundColor = Color.parseColor("#f5f5f5");
        roundProgressColor = Color.parseColor("#f5f5f5");
        roundWidth = DensityUtil.dp2px(7);
        textColor = Color.parseColor("#ff7b1a");
        textSize =  57;
        //最大刻度
        //max = 100;
        max = 72;
        //pointRadius = 3;
        //pointWidth = 2;

        // 加载拖动图标
//        mDragDrawable = getResources().getDrawable(R.drawable.ring_dot);// 圆点图片
//        int thumbHalfheight = mDragDrawable.getIntrinsicHeight();
//        int thumbHalfWidth = mDragDrawable.getIntrinsicWidth() ;
//        mDragDrawable.setBounds(-thumbHalfWidth, -thumbHalfheight, thumbHalfWidth, thumbHalfheight);
//
//        mDragPressDrawable = getResources().getDrawable(R.drawable.ring_dot);// 圆点图片
//        thumbHalfheight = mDragPressDrawable.getIntrinsicHeight() ;
//        thumbHalfWidth = mDragPressDrawable.getIntrinsicWidth() ;
//        mDragPressDrawable.setBounds(-thumbHalfWidth, -thumbHalfheight, thumbHalfWidth, thumbHalfheight);

        //外边距
        paddingOuterThumb = DensityUtil.dp2px(20);
    }


    @Override
    public void onDraw(Canvas canvas) {
        //setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速
        initOnDraw(canvas);
    }

    private void initOnDraw(Canvas canvas) {
        /**
         * 画最外层的大圆环
         */
//        paint.setColor(roundColor); //设置圆环的颜色
//        paint.setStyle(Paint.Style.STROKE); //设置空心
//        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
//        paint.setAntiAlias(true);  //消除锯齿
//        canvas.drawCircle(centerX, centerY, radius, paint); //画出圆环

        /**
         * 画文字
         */
        paint.setStrokeWidth(0);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(0, 0, 0, getResources().getColor(R.color.transparent));
        textSize = DensityUtil.dp2px( 47);
        paint.setTextSize(textSize);
        //paint.setMaskFilter(null);
        if (mProgressShow >= 97)
            mProgressShow = 100;

        String textPrgress = mProgressShow + "%" ;
        float textWidth = paint.measureText(textPrgress);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        float textHeight = getTextHeight(textPrgress, paint);

        // canvas.drawText(textTime, centerX - textWidth / 2, centerY + textSize / 2, paint);

        canvas.drawText(textPrgress, centerX - textWidth / 2, centerY + textHeight / 4, paint);

        textSize = DensityUtil.dp2px( 13);
        paint.setTextSize(textSize);


        String detetionStr = "检测中";
        float textWidth2 = paint.measureText(detetionStr);
        float detetionHeight = getTextHeight(detetionStr, paint);
        canvas.drawText(detetionStr, centerX - textWidth2 / 2, centerY + textHeight * 3 / 4  + detetionHeight, paint);


        paint.setShadowLayer(33, 0, 0, getResources().getColor(R.color.maincolor));


        //第一步:画背景(即内层圆)
        paint.setColor(getResources().getColor(R.color.white33)); //设置圆的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(DensityUtil.dp2px(2)); //设置圆的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centerX, centerY, roundRadius, paint); //画出圆

        /**
         * 画圆弧 ，画圆环的进度
         */
        paint.setStrokeWidth(DensityUtil.dp2px(6)); //设置圆环的宽度
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(getResources().getColor(R.color.maincolor));  //设置进度的颜色
        //paint.setShadowLayer(roundRadius, centerX, centerY, R.color.white);
        RectF oval = new RectF(centerX - roundRadius  , centerY - roundRadius , centerX + roundRadius , centerY +
                roundRadius);  //用于定义的圆弧的形状和大小的界限
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 270, 360 * progress / max, false, paint);  //根据进度画圆弧





        /**
         * 圆环颜色
         */
//        paint.setStrokeWidth(roundWidth + roundWidth); //设置圆环的宽度
//        paint.setColor(roundProgressColor);  //设置进度的颜色
//        RectF oval2 = new RectF(centerX - radius  , centerY - radius , centerX + radius , centerY +
//                radius);  //用于定义的圆弧的形状和大小的界限
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawArc(oval2, 270, 360, false, paint);  //根据进度画圆弧



        // 画圆上的两个点
//        paint.setStrokeWidth(pointWidth);
//        PointF startPoint = ChartUtils.calcArcEndPointXY(centerX, centerY, radius, 0, 270);
//        canvas.drawCircle(startPoint.x, startPoint.y, pointRadius, paint);

//        PointF progressPoint = ChartUtils.calcArcEndPointXY(centerX, centerY, radius, 360 *
//                progress / max, 270);
        // 画Thumb
        //canvas.save();

        //画用图片画
//        canvas.translate(progressPoint.x, progressPoint.y);
//        if (downOnArc) {
//            mDragPressDrawable.draw(canvas);
//        } else {
//            mDragDrawable.draw(canvas);
//        }



        //画刻度 72小时
        drawLines(canvas);


        //画内圆外圆颜色
        //drawCircleColor(canvas);
        //画可拖动的圆点
        //drawTouchBar(canvas);
        //画圆外文字
        //drawOutSideText(canvas);
        //画出某些时间段内的圆弧
        //drawEachTimeLines(canvas,hours);
    }


    /**
     * 画圆外文字
     * @param canvas
     */
    private void drawOutSideText(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setTextSize(40);
        paint.setColor(Color.parseColor("#565656"));
        int pading  = paddingOuterThumb/4;
        //测量字体宽度
        float textWidth1 = paint.measureText("0");
        float textWidth2 = paint.measureText("12");

        int left = centerX - radius - (int)roundWidth -(int) textWidth1 - pading;
        int top = centerY - radius - (int)roundWidth - pading;
        int right = centerX + radius + (int)roundWidth +pading;
        int bottom =  centerY + radius + (int)roundWidth + (int)textWidth2;
        canvas.drawText("0",(left + right)/2,top+ textWidth1/3,paint);
        canvas.drawText("18" ,left ,(top + bottom)/2,paint);
        canvas.drawText("6",right - textWidth2/3,(top + bottom)/2 ,paint);
        canvas.drawText("12",(left + right)/2 - textWidth2/3,bottom - textWidth1/3,paint);

    }

    /**
     * 画可拖动的圆点
     * @param canvas
     */
    private void drawTouchBar(Canvas canvas) {
        PointF progressPoint = ChartUtils.calcArcEndPointXY(centerX, centerY, radius, 360 *
                progress / max, 270);
        //直接用画笔画
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(Color.parseColor("#ff7b1a"));
        canvas.drawCircle(progressPoint.x, progressPoint.y,roundWidth/2,paint);
        canvas.restore();
    }

    /**
     * 画内圆外圆颜色
     * @param canvas
     */
    private void drawCircleColor(Canvas canvas) {
        //画外圆颜色
        //paint.setColor(Color.parseColor("#f7f7f7"));
        paint.setColor(Color.parseColor("#f7f7f7"));
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX,centerY,radius + roundWidth,paint);
        //画内圆颜色
        paint.setColor(Color.parseColor("#f6f6f6"));
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX,centerY,radius - roundWidth,paint);
    }

    /**
     * 画出刻度
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        paint.setColor(Color.parseColor("#000000"));
        //直线粗细
        paint.setStrokeWidth(6);
        //paint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i < 72; i++){
            if (i < mOuterRoundProgress) {
                if (mOuterSences) {
                    paint.setShadowLayer(30, 0, 0, getResources().getColor(R.color.maincolor));
                    //paint.setMaskFilter(new BlurMaskFilter(22, BlurMaskFilter.Blur.OUTER));
                    paint.setColor(getResources().getColor(R.color.maincolor));
                } else
                    paint.setColor(getResources().getColor(R.color.white33));
                //radius:模糊半径，radius越大越模糊，越小越清晰，但是如果radius设置为0，则阴影消失不见
                //dx:阴影的横向偏移距离，正值向右偏移，负值向左偏移
                //dy:阴影的纵向偏移距离，正值向下偏移，负值向上偏移
                //color: 绘制阴影的画笔颜色，即阴影的颜色（对图片阴影无效）
            } else {
                if (mOuterSences)
                    paint.setColor(getResources().getColor(R.color.white33));
                else {
                    paint.setShadowLayer(30, 0, 0, getResources().getColor(R.color.maincolor));
                    //paint.setMaskFilter(new BlurMaskFilter(22, BlurMaskFilter.Blur.OUTER));
                    paint.setColor(getResources().getColor(R.color.maincolor));
                }

            }


            float mProgress = (i)*1.0f/72*max;
            PointF mProgressPoint = ChartUtils.calcArcEndPointXY(centerX, centerY, radius, 360 *
                    mProgress / max, 270);
            //圆上到圆心
            //canvas.drawLine(mProgressPoint.x,mProgressPoint.y,centerX,centerY,paint);
            float scale1 = radius*1.0F / roundWidth;
            float scale2 = radius*1.0F / (radius - roundWidth);
            //计算内圆上的点
            float disX = (scale1*mProgressPoint.x + scale2*centerX)/(scale1+ scale2);
            float disY =  (scale1*mProgressPoint.y + scale2*centerY)/(scale1+ scale2);
            //计算外圆上的点
            float disX2 = mProgressPoint.x*2 - disX;
            float disY2 =  mProgressPoint.y*2 - disY;
            if (mProgress%6 == 0){
                //直线3/4高度
                float disX3 = (disX*3 + disX2)/4;
                float disY3 =  (disY*3 + disY2)/4;

                canvas.drawLine(disX2 ,disY2,disX,disY,paint);

                //maxLineHeight =  (int)(disX*1 + disX2*3)/4;
            }else{
                //直线1/2高度
                float disX3 = (disX*1 + disX2)/2;
                float disY3 =  (disY*1 + disY2)/2;
                canvas.drawLine(disX3 ,disY3,disX,disY,paint);
            }

        }
    }




    /**
     * 画出某些时间段内的圆弧
     * @param hours
     */
    public void drawEachTimeLines(int[] hours){
        this.hours = hours;
        invalidate();
    }



    /**
     * 根据进度画出圆弧
     * @param canvas
     * @param mProgress 这里的进度 = 时间
     */
    private void drawMyArc(Canvas canvas, int mProgress){
        paint.setStrokeWidth(roundWidth/4); //设置1/4
        paint.setColor(Color.parseColor("#ff7b1a"));  //设置进度的颜色
        RectF oval = new RectF(centerX - radius  + roundWidth/4*3, centerY - radius + roundWidth/4*3, centerX + radius - roundWidth/4*3, centerY +
                radius - roundWidth/4*3);  //用于定义的圆弧的形状和大小的界限
        paint.setStyle(Paint.Style.STROKE);

        float start = 270 + (mProgress*360/max);
        //这个end是说在起始度数上加的度数
        float end = 15;
        canvas.drawArc(oval, start,end, false, paint);  //根据进度画圆弧

    }


    private boolean downOnArc = false;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                if (isTouchArc(x, y)) {
//                    downOnArc = true;
//                    updateArc(x, y);
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (downOnArc) {
//                    updateArc(x, y);
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                downOnArc = false;
//                invalidate();
//                if (changeListener != null) {
//                    int progressInt = (int) progress;
//                    changeListener.onProgressChangeEnd(max, progressInt);
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    private int centerX, centerY;
    private int radius, roundRadius;
    private int paddingOuterThumb;

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        centerX = width / 2;
        centerY = height / 2;
        int minCenter = Math.min(centerX, centerY);

        radius = (int) (minCenter - roundWidth / 2 - paddingOuterThumb); //圆环的半径
        roundRadius = radius - (int)(3 * roundWidth);
        minValidateTouchArcRadius = (int) (radius - paddingOuterThumb * 1.5f);
        maxValidateTouchArcRadius = (int) (radius + paddingOuterThumb * 1.5f);
        super.onSizeChanged(width, height, oldw, oldh);
    }

    // 根据点的位置，更新进度
    private void updateArc(int x, int y) {
        int cx = x - getWidth() / 2;
        int cy = y - getHeight() / 2;
        // 计算角度，得出（-1->1）之间的数据，等同于（-180°->180°）
        double angle = Math.atan2(cy, cx) / Math.PI;
        // 将角度转换成（0->2）之间的值，然后加上90°的偏移量
        angle = ((2 + angle) % 2 + (90 / 180f)) % 2;
        // 用（0->2）之间的角度值乘以总进度，等于当前进度
        progress = (int) (angle * max / 2);
        if (changeListener != null) {
            changeListener.onProgressChange(max, (int)progress);
        }
        invalidate();
    }

    private int minValidateTouchArcRadius; // 最小有效点击半径
    private int maxValidateTouchArcRadius; // 最大有效点击半径

    // 判断是否按在圆边上
    private boolean isTouchArc(int x, int y) {
        double d = getTouchRadius(x, y);
        if (d >= minValidateTouchArcRadius && d <= maxValidateTouchArcRadius) {
            return true;
        }
        return false;
    }

    // 计算某点到圆点的距离
    private double getTouchRadius(int x, int y) {
        int cx = x - getWidth() / 2;
        int cy = y - getHeight() / 2;
        return Math.hypot(cx, cy);
    }

    public String getTimeText(int progress) {
        //总进度100 分为72小时
        long hour = Math.round(progress*1.0/max * 72);
        int result = (int)hour;
        if (result == 72){
            result = 0;
        }
        return result+":00" + "-"+(result+1)+":00";
    }

    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized float getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(float progress) {
        //LogUtils.e(progress);


        if (progress < 0) {
            mProgressShow = 1;
            progress = 0;
        }

        mProgressShow =  Math.round(progress);
        float ss = progress * 72 / 100;
        progress = (int) ss;
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }

    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    private OnProgressChangeListener changeListener;

    public void setChangeListener(OnProgressChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface OnProgressChangeListener {
        void onProgressChange(int duration, int progress);

        void onProgressChangeEnd(int duration, int progress);
    }


    /**
     * 画出某些时间段内的圆弧
     * @param hours
     */
    private void drawEachTimeLines(Canvas canvas, int[] hours){
        if (hours == null || hours.length==0){
            return;
        }
        for (int i = 0;i<hours.length;i++){
            drawMyArc(canvas,hours[i]);
        }

    }

    private int getTextWidth(String text, Paint paint) {
        Rect rect = new Rect(); // 文字所在区域的矩形
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    private int getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    /** 之前进度 */
    public float getmProgressShow(){
        return mProgressShow;
    }


    public void setProgressX(long time, boolean mSence){

        this.mOuterSences = mSence;
        this.mOuterRoundTime = time;
        startProgressX();
        ///handler.post(task);//立即调用
        //handler.postDelayed(task, 100);

        //handler.postDelayed(mTimeTask, 100);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mIntCount < mCount) {
                        //需要执行的代码
                        if (mOuterSences) {
                            mOuterRoundProgress = getOuterProger(mIntCount);
                        } else {
                            mOuterRoundProgress = getOuterProger2(mIntCount);
                        }

                        mIntCount++;
                        postInvalidate();
                        //LogUtils.e("xx" + System.currentTimeMillis());
//                handler.postDelayed(this,50);//设置循环时间，此处是5毫秒
                        ///handler.postDelayed(this, 10);
                        Thread.sleep(10);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }




//    private Handler handler = new Handler();
//    private Runnable task = new Runnable() {
//        public void run() {
//            // TODO Auto-generated method stub
//            if (mIntCount < mCount) {
//
//                //需要执行的代码
//                if (mOuterSences) {
//                    mOuterRoundProgress = getOuterProger(mIntCount);
//                } else {
//                    mOuterRoundProgress = getOuterProger2(mIntCount);
//                }
//
//                mIntCount++;
//                postInvalidate();
//                LogUtils.e("xx" + System.currentTimeMillis());
////                handler.postDelayed(this,50);//设置循环时间，此处是5毫秒
//                handler.postDelayed(this, 10);
//            }
//
//        }
//    };



    private float mCount = 0f;
    private double mCountG = 0f;
    private int mIntCount = 0;

    private void startProgressX() {
        //使用自由落體的公式---h=1/2*g*t2
        mCount = 0f;
        mCountG = 0f;
        mCount = (int) mOuterRoundTime / 10; //默认每50毫秒执行一次方法
        //mOuterRoundProgress；
        mOuterRoundProgress = 0f;
        mCountG = 200f / (mCount * mCount);
        //mCountG = 0.12f;
        mIntCount = 0;

    }

    private double getOuterProger(int counts){
        double progress = 0.5f * mCountG * counts * counts;

        return  progress;
    }

    private double getOuterProger2(int count){

        double progress = 100 - 0.5f * mCountG * (mCount - count) * (mCount - count);

        return progress * 72 / 100;
    }








//    protected class MyThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//
//            while (mIntCount < mCount) {
//                LogUtils.e("xxx");
//                try {
//                    mOuterRoundProgress = getOuterProger(mIntCount);
//                    mIntCount++;
//                    //LogUtils.e(mOuterRoundProgress);
//
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //invalidate();
//               if (mOuterSences) {
//                   mHandler.sendEmptyMessage(0);  //该方法会调用 onDraw(Canvas canvas)
//               } else {
//                    LogUtils.e(mOuterRoundProgress);
//                    postInvalidate();
//                    //mHandler.sendEmptyMessage(0);  //该方法会调用 onDraw(Canvas canvas)
//               }
//
//            }
//        }
//    }



    //    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            invalidate();
//        }
//    };
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//             if (mHandler != null) {
//                mHandler.removeCallbacksAndMessages(null);
//        }
//        if (mThread != null)
//            mThread.interrupt();

    }
}
