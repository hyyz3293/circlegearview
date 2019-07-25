package com.tool.jackntest.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tool.jackntest.R;
import com.tool.jackntest.utils.DensityUtil;


public class CircleGearView2 extends View {

    private Context mContext;
    private Paint mPaint; // 画笔对象的引用
    private float mRoundWidth = DensityUtil.dp2px(7); // 圆环的宽度

    private int centerX, centerY;
    private int radius, roundRadius;
    private int paddingOuterThumb;//外边距

    private int minValidateTouchArcRadius; // 最小有效点击半径
    private int maxValidateTouchArcRadius; // 最大有效点击半径

    private int mCenterTxtColor; //圆中心 字体 颜色、大小
    private float mCenterTxtSize, mCenterBottomSize;
    private String mCenterStatu = "检测中"; //中间状态文字

    private int mMainColor; //主题颜色

    private int mInnerRoundColor; //内圆 宽度 、颜色
    private float mInnerRoundWidth;


    private int mTxtProgress = 1; // 显示进度
    private int max = 72; // 最大进度 -- 总共72个刻度 所以这样定义
    private int progress = 1;


    private double mOuterRoundProgress = 36.5f;//外圈进度
    private boolean mOuterSences = true; //true 正向----false方向

    public CircleGearView2(Context context) {
        this(context, null);
    }

    public CircleGearView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleGearView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(attrs);
    }


    private void initView(AttributeSet attrs){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速
        this.setWillNotDraw(false);                    // 调用此方法后，才会执行 onDraw(Canvas) 方法
        mPaint = new Paint();

        //获取自定义属性和默认值
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CGViewStyleable);

        mCenterTxtColor =  typedArray.getColor(R.styleable.CGViewStyleable_center_txt_color, getResources().getColor(R.color.white));
        mCenterTxtSize = typedArray.getDimension(R.styleable.CGViewStyleable_center_txt_size, DensityUtil.dp2px(47));
        mCenterBottomSize = typedArray.getDimension(R.styleable.CGViewStyleable_center_txt_bottom_size, DensityUtil.dp2px(13));
        mCenterStatu = typedArray.getString(R.styleable.CGViewStyleable_center_txt_status);

        mRoundWidth = typedArray.getDimension(R.styleable.CGViewStyleable_round_width, DensityUtil.dp2px(7));
        mMainColor = typedArray.getColor(R.styleable.CGViewStyleable_round_color, getResources().getColor(R.color.maincolor));

        mInnerRoundWidth = typedArray.getDimension(R.styleable.CGViewStyleable_inner_round_width, DensityUtil.dp2px(2));
        mInnerRoundColor = typedArray.getColor(R.styleable.CGViewStyleable_inner_round_color, getResources().getColor(R.color.white33));



        paddingOuterThumb = DensityUtil.dp2px(20);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        centerX = width / 2;
        centerY = height / 2;
        int minCenter = Math.min(centerX, centerY);

        radius = (int) (minCenter - mRoundWidth / 2 - paddingOuterThumb); //圆环的半径
        roundRadius = radius - (int)(3 * mRoundWidth);
        minValidateTouchArcRadius = (int) (radius - paddingOuterThumb * 1.5f);
        maxValidateTouchArcRadius = (int) (radius + paddingOuterThumb * 1.5f);
        super.onSizeChanged(width, height, oldw, oldh);
    }

    @Override
    public void onDraw(Canvas canvas) {
      //  setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速
        initOnDraw(canvas);
    }

    /** start circle -*/
    private void initOnDraw(Canvas canvas) {
        /** 画文字 start */
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mCenterTxtColor);
        mPaint.setAntiAlias(true);  //消除锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShadowLayer(0, 0, 0, getResources().getColor(R.color.transparent));
        mPaint.setTextSize(mCenterTxtSize);

        String textPrgress = mTxtProgress + "%" ;
        float txtWidth = mPaint.measureText(textPrgress);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        float txtHeight = ChartUtils.getTxtHeight(textPrgress, mPaint);
        canvas.drawText(textPrgress, centerX - txtWidth / 2, centerY + txtHeight / 4, mPaint);

        /******************* 进度下方文字 start */
        mPaint.setTextSize(mCenterBottomSize);
        float textWidth2 = mPaint.measureText(mCenterStatu);
        float detetionHeight = ChartUtils.getTxtHeight(mCenterStatu, mPaint);
        canvas.drawText(mCenterStatu, centerX - textWidth2 / 2, centerY + txtHeight * 3 / 4  + detetionHeight, mPaint);
        /******************* 进度下方文字 end */
        /** 画文字 end */


        /** 画圆 start */
        mPaint.setShadowLayer(33, 0, 0, mMainColor);
        mPaint.setColor(mMainColor);

        //第一步：画背景(即内层圆)
        mPaint.setColor(mInnerRoundColor); //设置圆的颜色
        mPaint.setStyle(Paint.Style.STROKE); //设置空心
        mPaint.setStrokeWidth(mInnerRoundWidth); //设置圆的宽度
        mPaint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centerX, centerY, roundRadius, mPaint); //画出圆

        //第二步：圆弧
        mPaint.setStrokeWidth(DensityUtil.dp2px(6)); //设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(getResources().getColor(R.color.maincolor));  //设置进度的颜色
        RectF oval = new RectF(centerX - roundRadius  , centerY - roundRadius , centerX + roundRadius , centerY +
                roundRadius);  //用于定义的圆弧的形状和大小的界限
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 270, 360 * progress / max, false, mPaint);  //根据进度画圆弧
        /** 画圆 end  */


        /** 画刻度-72份- 还分正反切换---start */
        mPaint.setStrokeWidth(DensityUtil.dp2px(2));
        for (int i = 0; i < 72; i++){
            //radius:模糊半径，radius越大越模糊，越小越清晰，但是如果radius设置为0，则阴影消失不见
            //dx:阴影的横向偏移距离，正值向右偏移，负值向左偏移
            //dy:阴影的纵向偏移距离，正值向下偏移，负值向上偏移
            //color: 绘制阴影的画笔颜色，即阴影的颜色（对图片阴影无效）

            if (i < mOuterRoundProgress) {
                if (mOuterSences) {
                    mPaint.setShadowLayer(30, 0, 0, mMainColor);
                    mPaint.setColor(getResources().getColor(R.color.maincolor));
                } else
                    mPaint.setColor(getResources().getColor(R.color.white33));
            } else {
                if (mOuterSences)
                    mPaint.setColor(getResources().getColor(R.color.white33));
                else {
                    mPaint.setShadowLayer(30, 0, 0, mMainColor);
                    mPaint.setColor(getResources().getColor(R.color.maincolor));
                }
            }
            float mProgress = (i)*1.0f/72*max;
            PointF mProgressPoint = ChartUtils.calcArcEndPointXY(centerX, centerY, radius, 360 * mProgress / max, 270);
            //圆上到圆心
            //canvas.drawLine(mProgressPoint.x,mProgressPoint.y,centerX,centerY,paint);
            float scale1 = radius * 1.0F / mRoundWidth;
            float scale2 = radius * 1.0F / (radius - mRoundWidth);
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
                canvas.drawLine(disX2 ,disY2,disX,disY, mPaint);
            }else{
                //直线1/2高度
                float disX3 = (disX*1 + disX2)/2;
                float disY3 =  (disY*1 + disY2)/2;
                canvas.drawLine(disX3 ,disY3,disX,disY, mPaint);
            }
        }
        /** 画刻度-72份- 还分正反切换---end */

    }








    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
