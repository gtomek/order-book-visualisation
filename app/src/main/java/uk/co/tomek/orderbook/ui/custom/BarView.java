package uk.co.tomek.orderbook.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import uk.co.tomek.orderbook.R;
import uk.co.tomek.orderbook.ui.model.OrderRaw;

/**
 * Custom view representing one graph item.
 */
public class BarView extends View {

    private Paint basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);

    private Rect textRectangle = new Rect();

    private float sizeFriction;

    private String title = "";

    public BarView(Context context) {
        this(context, null, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BarView, defStyleAttr, 0);
        try {
            int baseColor = attributes.getColor(R.styleable.BarView_itemColor, Color.BLUE);
            basePaint.setColor(baseColor);
            final int defaultTextSize = getResources().getDimensionPixelSize(R.dimen.gap_medium);
            float textSize = attributes.getDimension(R.styleable.BarView_itemTextSize, defaultTextSize);
            textPaint.setTextSize(textSize);
        } finally {
            attributes.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        basePaint.setStrokeWidth(height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw line
        float xEndPosition = getWidth() * sizeFriction;
        canvas.drawLine(0, getHeight() / 2f, xEndPosition, getHeight() / 2f, basePaint);

        // draw text
        textPaint.getTextBounds(title, 0, title.length(), textRectangle);
        float textXpos = getWidth() / 2f - textRectangle.width() / 2f;
        float textYpos = getHeight() / 2f + textRectangle.height() / 2f;
        canvas.drawText(title, textXpos, textYpos, textPaint);
    }

    public void setValues(OrderRaw orderRaw) {
        sizeFriction = orderRaw.getPriceFriction();
        title = orderRaw.getTitle();
        invalidate();
    }

    public void setTitle(String midPointTitle) {
        sizeFriction = 0;
        title = midPointTitle;
        invalidate();
    }
}
