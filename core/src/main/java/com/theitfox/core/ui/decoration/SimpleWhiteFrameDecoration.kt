package com.theitfox.core.ui.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.support.v7.widget.RecyclerView

/**
 * SimpleWhiteFrameDecoration draw a simple white frame over the recycler view's items, mimicking grid dividers
 */
class SimpleWhiteFrameDecoration
/**
 * Instantiates a new Grid divider decoration.
 */
@JvmOverloads constructor(private val mDividerSize: Int = DIVIDER_SIZE, color: Int = DIVIDER_COLOR) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable

    init {
        mDivider = PaintDrawable(color)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (parent.childCount == 0) {
            return
        }

        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            var left: Int
            var top: Int
            var right: Int
            var bottom: Int
            // Left divider
            top = child.top - params.topMargin
            bottom = child.bottom + params.bottomMargin
            left = child.left - params.leftMargin
            right = left + mDividerSize
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
            // Right divider
            right = child.right + params.leftMargin
            left = right - mDividerSize
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
            // Top
            left = child.left - params.leftMargin
            right = child.right + params.leftMargin
            top = child.top - params.topMargin
            bottom = top + mDividerSize
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
            // Bottom
            bottom = child.bottom + params.bottomMargin
            top = bottom - mDividerSize
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
        }
    }

    companion object {

        private val DIVIDER_SIZE = 2
        private val DIVIDER_COLOR = Color.WHITE
    }
}