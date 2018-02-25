package de.philippengel.artisticstyletransferdemo

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * A FrameLayout subclass that always has the same width and height and forms a square.
 * The larger dimension will be set to the smaller to meet this invariant.
 */
class SquareFrameLayout: FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (measuredWidth == 0 && measuredHeight == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            val min = Math.min(getMeasuredHeight(), getMeasuredWidth())
            setMeasuredDimension(min, min)
            return
        }

        val size = if (measuredHeight == 0 || measuredWidth == 0) {
            Math.max(measuredHeight, measuredWidth)
        } else {
            Math.min(measuredHeight, measuredWidth)
        }

        val measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        super.onMeasure(measureSpec, measureSpec)
    }

}
