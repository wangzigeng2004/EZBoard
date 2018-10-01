package cn.ezandroid.lib.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.ezandroid.lib.board.theme.GoTheme;

import static cn.ezandroid.lib.board.theme.GoTheme.INVALID_VALUE;

/**
 * 棋子显示控件
 *
 * @author like
 * @date 2017-12-21
 */
public class StoneView extends TextView {

    private Paint mStonePaint;

    private Stone mStone;

    private int mStoneSpace = 6; // 棋子间距

    private boolean mIsHighlight; // 棋子是否高亮

    private boolean mIsDrawNumber = true; // 是否绘制棋子手数

    private GoTheme.StoneTheme mStoneTheme; // 棋子主题样式
    private GoTheme.MarkTheme mMarkTheme; // 标记主题样式

    public StoneView(Context context) {
        super(context);
        initStone();
    }

    public StoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStone();
    }

    private void initStone() {
        mStonePaint = new Paint();
        mStonePaint.setAntiAlias(true);
        mStonePaint.setFilterBitmap(true);
    }

    /**
     * 设置棋子主题
     *
     * @param stoneTheme
     */
    public void setStoneTheme(GoTheme.StoneTheme stoneTheme) {
        if (mStoneTheme != stoneTheme) {
            mStoneTheme = stoneTheme;
            postInvalidate();
        }
    }

    /**
     * 设置标记主题
     *
     * @param markTheme
     */
    public void setMarkTheme(GoTheme.MarkTheme markTheme) {
        if (mMarkTheme != markTheme) {
            mMarkTheme = markTheme;
            postInvalidate();
        }
    }

    /**
     * 设置是否绘制棋子手数
     *
     * @param drawNumber
     */
    public void setDrawNumber(boolean drawNumber) {
        if (mIsDrawNumber != drawNumber) {
            mIsDrawNumber = drawNumber;
            postInvalidate();
        }
    }

    /**
     * 获取是否绘制棋子手数
     *
     * @return
     */
    public boolean isDrawNumber() {
        return mIsDrawNumber;
    }

    /**
     * 设置棋子是否高亮
     *
     * @param highlight
     */
    public void setHighlight(boolean highlight) {
        if (mIsHighlight != highlight) {
            mIsHighlight = highlight;
            postInvalidate();
        }
    }

    /**
     * 获取棋子是否高亮
     *
     * @return
     */
    public boolean isHighlight() {
        return mIsHighlight;
    }

    /**
     * 设置棋子间距
     *
     * @param stoneSpace
     */
    public void setStoneSpace(int stoneSpace) {
        if (mStoneSpace != stoneSpace) {
            mStoneSpace = stoneSpace;
            postInvalidate();
        }
    }

    /**
     * 获取棋子间距
     *
     * @return
     */
    public int getStoneSpace() {
        return mStoneSpace;
    }

    /**
     * 设置棋子
     *
     * @param stone
     */
    public void setStone(Stone stone) {
        mStone = stone;
    }

    /**
     * 获取棋子
     *
     * @return
     */
    public Stone getStone() {
        return mStone;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制棋子
        drawStone(canvas);

        // 绘制手数
        drawNumber(canvas);

        // 绘制高亮状态
        drawHighlight(canvas);
    }

    /**
     * 绘制棋子
     *
     * @param canvas
     */
    private void drawStone(Canvas canvas) {
        // 绘制棋子
        Drawable drawable = mStoneTheme.getRandomTexture();
        if (drawable instanceof ColorDrawable) {
            // 纯色棋子
            mStonePaint.setColor(((ColorDrawable) drawable).getColor());
            mStonePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mStoneSpace / 2, mStonePaint);
        } else {
            // 图片棋子等
            drawable.setBounds(0, 0, getWidth(), getHeight());
            drawable.draw(canvas);
        }

        // 绘制棋子边框
        if (mStoneTheme.getBorderColor() != INVALID_VALUE) {
            mStonePaint.setColor(mStoneTheme.getBorderColor());
            mStonePaint.setStyle(Paint.Style.STROKE);
            mStonePaint.setStrokeWidth(mStoneTheme.mBorderWidth);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mStoneSpace / 2, mStonePaint);
        }
    }

    /**
     * 绘制高亮状态
     *
     * @param canvas
     */
    private void drawHighlight(Canvas canvas) {
        if (mIsHighlight) {
            mStonePaint.setColor(mMarkTheme.getHighLightColor());
            mStonePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 6, mStonePaint);
        }
    }

    /**
     * 绘制手数
     *
     * @param canvas
     */
    private void drawNumber(Canvas canvas) {
        if (mIsHighlight || !mIsDrawNumber || mStone.number <= 0) {
            return;
        }
        float textSize;
        if (mStone.number < 10) {
            textSize = getWidth() / 1.5f;
        } else if (mStone.number < 100) {
            textSize = getWidth() / 2f;
        } else {
            textSize = getWidth() / 2.5f;
        }
        mStonePaint.setStyle(Paint.Style.FILL);
        mStonePaint.setTextSize(textSize);
        mStonePaint.setStrokeWidth(0);
        String number = String.valueOf(mStone.number);
        float width = mStonePaint.measureText(number);
        Rect bounds = new Rect();
        mStonePaint.getTextBounds(number, 0, number.length(), bounds);
        if (mStone.color == StoneColor.BLACK) {
            mStonePaint.setColor(Color.WHITE);
            canvas.drawText(number, (getWidth() - width) / 2f, (getHeight() + bounds.height()) / 2f, mStonePaint);
        } else {
            mStonePaint.setColor(Color.BLACK);
            canvas.drawText(number, (getWidth() - width) / 2f, (getHeight() + bounds.height()) / 2f, mStonePaint);
        }
    }
}