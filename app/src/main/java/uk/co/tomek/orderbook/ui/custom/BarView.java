package uk.co.tomek.orderbook.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import uk.co.tomek.orderbook.R;

/**
 * Custom view representing one graph item.
 */
public class BarView extends View {

    private Paint basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);

    private Rect textRectangle = new Rect();

    private float sizeFriction;

    Random randomizer = new Random(); // TODO: remove it, temp solution for debug

    public BarView(Context context) {
        this(context, null, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int baseColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        basePaint.setColor(baseColor);
        float textSize = getResources().getDimension(R.dimen.gap_medium);
        textPaint.setTextSize(textSize);
        sizeFriction = randomizer.nextFloat();
    }

    public void setSizeFriction(float sizeFriction) {
        this.sizeFriction = sizeFriction;
        invalidate();
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
        final String sizeFrictionString = Float.toString(sizeFriction);
        textPaint.getTextBounds(sizeFrictionString, 0, sizeFrictionString.length(), textRectangle);
        float textXpos = getWidth() / 2f - textRectangle.width() / 2f;
        float textYpos = getHeight() / 2f + textRectangle.height() / 2f;
        canvas.drawText(sizeFrictionString, textXpos, textYpos, textPaint);
    }
}
