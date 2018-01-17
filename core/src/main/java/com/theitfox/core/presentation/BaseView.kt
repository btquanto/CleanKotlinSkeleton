package com.theitfox.core.presentation

import android.content.Context

/**
 * Created by btquanto on 1/17/2018.
 *
 *
 * The BaseView interface that all MVP Views would extends from
 */
interface BaseView {
    /**
     * All views are expected to have access to a context
     * If views are fragments, it should have already been implemented
     *
     * @return the context
     */
    val context: Context
}
