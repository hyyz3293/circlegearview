package com.tool.jackntest.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private int paddingOuterThumb;

    private int minValidateTouchArcRadius; // 最小有效点击半径
    private int maxValidateTouchArcRadius; // 最大有效点击半径

    private int mCenterTxtColor; //圆中心 字体 颜色、大小
    private float mCenterTxtSize, mCenterBottomSize;
    private String mCenterStatu = "检测中"; //中间状态文字

    private int mMainColor; //主题颜色

    private int mInnerRoundColor; //内圆 宽度 、颜色
    private float mInnerRoundWidth;


    private int mTxtProgress = 1; // 显示进度

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

        //第一步:画背景(即内层圆)
        mPaint.setColor(mInnerRoundColor); //设置圆的颜色
        mPaint.setStyle(Paint.Style.STROKE); //设置空心
        mPaint.setStrokeWidth(mInnerRoundWidth); //设置圆的宽度
        mPaint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centerX, centerY, roundRadius, mPaint); //画出圆


        /** 画圆 start */




    }








    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
