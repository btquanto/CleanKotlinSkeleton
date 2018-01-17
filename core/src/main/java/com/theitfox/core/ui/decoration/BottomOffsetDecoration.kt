package com.theitfox.core.ui.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by btquanto on 09/02/2017.
 */

class BottomOffsetDecoration : RecyclerView.ItemDecoration {
    private var mBottomOffset: Int = 0
    private val offsetProvider: OffsetProvider?

    constructor(bottomOffset: Int) {
        mBottomOffset = bottomOffset
        offsetProvider = null
    }

    constructor(offsetProvider: OffsetProvider) {
        this.offsetProvider = offsetProvider
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dataSize = state.itemCount
        val position = parent.getChildPosition(view)
        if (dataSize > 0 && position == dataSize - 1) {
            if (offsetProvider != null) {
                mBottomOffset = offsetProvider.offset
            }
            outRect.set(0, 0, 0, mBottomOffset)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    interface OffsetProvider {
        val offset: Int
    }
}