package com.theitfox.core.data

/**
 * Created by btquanto on 1/17/18.
 */

import android.content.Context
import com.theitfox.core.cache.MemCache

abstract class Repository (protected var context: Context, protected var memCache: MemCache)
