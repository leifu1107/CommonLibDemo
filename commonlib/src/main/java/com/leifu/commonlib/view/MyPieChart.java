package com.leifu.commonlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.leifu.commonlib.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人:雷富
 * 创建时间:2019/8/29 12:53
 * 描述:
 */
public class MyPieChart extends View {

    private List<PieEntry> pieEntries;

    private Paint paint;  //画笔

    private float centerX; //中心点坐标 x
    private float centerY; //中心点坐标 y
    private float radius;  //未选中状态的半径
    private float sRadius; //选中状态的半径

    private OnItemClickListener listener; //点击事件的回调

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MyPieChart(Context context) {
        super(context);
        init();
    }

    public MyPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pieEntries = new ArrayList<>();
        paint = new Paint();
        paint.setTextSize(DensityUtil.sp2px(15));
        paint.setAntiAlias(true);
    }


    public void setRadius(float radius) {
        this.sRadius = radius;
    }

    /**
     * 设置数据并刷新
     *
     * @param pieEntries
     */
    public void setPieEntries(List<PieEntry> pieEntries) {
        this.pieEntries = pieEntries;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算总值
        float total = 0;
        for (int i = 0; i < pieEntries.size(); i++) {
            total += pieEntries.get(i).getNumber();
        }
        //刷新中心点 和半径
        centerX = getPivotX();
        centerY = getPivotY();
        if (sRadius == 0) {   //这里做个判断，如果没有通过setRadius方法设置半径，则半径为真个view最小边的一半
            sRadius = (getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2);
        }
        //计算出两个状态的半径，这里二者相差5dp.
        radius = sRadius - DensityUtil.dp2px(5);

        //其实角度设置为0,即x轴正方形
        float startC = 0;
        //遍历List<PieEntry> 开始画扇形
        for (int i = 0; i < pieEntries.size(); i++) {
            //计算当前扇形扫过的角度
            float sweep;
            if (total <= 0) {
                sweep = 360 / pieEntries.size();
            } else {
                sweep = 360 * (pieEntries.get(i).getNumber() / total);
            }
            //设置当前扇形的颜色
            paint.setColor(getResources().getColor(pieEntries.get(i).colorRes));
            //判断当前扇形是否被选中，确定用哪个半径
            float radiusT;
            if (pieEntries.get(i).isSelected()) {
                radiusT = sRadius;
            } else {
                radiusT = radius;
            }
            //画扇形的方法
            RectF rectF = new RectF(centerX - radiusT, centerY - radiusT, centerX + radiusT, centerY + radiusT);
            canvas.drawArc(rectF, startC, sweep, true, paint);

            //将每个扇形的起始角度 和 结束角度 放入对应的对象
            pieEntries.get(i).setStartC(startC);
            pieEntries.get(i).setEndC(startC + sweep);
            //将当前扇形的结束角度作为下一个扇形的起始角度
            startC += sweep;
        }
    }


    /**
     * 获取  touch点/圆心连线  与  x轴正方向 的夹角
     *
     * @param touchX
     * @param touchY
     */
    private float getSweep(float touchX, float touchY) {
        float xZ = touchX - centerX;
        float yZ = touchY - centerY;
        float a = Math.abs(xZ);
        float b = Math.abs(yZ);
        double c = Math.toDegrees(Math.atan(b / a));
        if (xZ >= 0 && yZ >= 0) {//第一象限
            return (float) c;
        } else if (xZ <= 0 && yZ >= 0) {//第二象限
            return 180 - (float) c;
        } else if (xZ <= 0 && yZ <= 0) {//第三象限
            return (float) c + 180;
        } else {//第四象限
            return 360 - (float) c;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }


    /**
     * 每个扇形的对象
     */
    public static class PieEntry {
        private float number;  //数值
        private int colorRes;  //颜色资源
        private boolean selected; //是否选中
        private float startC;     //对应扇形起始角度
        private float endC;       //对应扇形结束角度

        public PieEntry(float number, int colorRes, boolean selected) {
            this.number = number; //防止分母为零
            this.colorRes = colorRes;
            this.selected = selected;
        }

        public float getStartC() {
            return startC;
        }

        public void setStartC(float startC) {
            this.startC = startC;
        }

        public float getEndC() {
            return endC;
        }

        public void setEndC(float endC) {
            this.endC = endC;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        public int getColorRes() {
            return colorRes;
        }

        public void setColorRes(int colorRes) {
            this.colorRes = colorRes;
        }
    }


}
