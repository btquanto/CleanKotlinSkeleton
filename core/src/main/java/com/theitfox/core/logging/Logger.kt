package com.theitfox.core.logging

import com.theitfox.core.BuildConfig

/**
 * Created by btquanto on 1/17/18.
 *
 * A logging utility allowing more conveniently logging Android messages
 */
object Logger {

    private val DEFAULT_TAG = "AppLogs"

    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.v(tag, message)
        }
    }

    fun v(message: String) {
        v(DEFAULT_TAG, message)
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.i(tag, message)
        }
    }

    fun i(message: String) {
        i(DEFAULT_TAG, message)
    }

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.d(tag, message)
        }
    }

    fun d(message: String) {
        d(DEFAULT_TAG, message)
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.w(tag, message)
        }
    }

    fun w(message: String) {
        w(DEFAULT_TAG, message)
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(tag, message)
        }
    }

    fun e(message: String) {
        e(DEFAULT_TAG, message)
    }
}
