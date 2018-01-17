package com.theitfox.core.cache

import java.util.*

/**
 * Created by btquanto on 15/09/2016.
 *
 *
 * Memory cache utility using singleton design pattern
 */
class MemCache {

    private val entries: HashMap<String, Entry<*>> = HashMap()

    /**
     * Put a value into mem cache
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param value the value
    </T> */
    fun <T> put(key: String, value: T) {
        entries.put(key, Entry(value))
    }

    /**
     * Get a cached value
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the cached value, or null
    </T> */
    operator fun <T> get(key: String): T? {
        var entry: Entry<*>? = entries[key]
        if (entry != null) {
            if (entry.isExpired) {
                entries.remove(key)
                entry = null
            }
        }
        return if (entry != null) entry.data as T else null
    }

    /**
     * Check if mem cache contains any unexpired entry for a given key
     *
     * @param key the key
     * @return whether mem cache contains an unexpired entry for `key`
     */
    operator fun contains(key: String): Boolean {
        return get<Any>(key) != null
    }

    /**
     * Entry class for holding cached data and checking expiration
     */
    private inner class Entry<out T>(val data: T) {
        private val expiration: Date

        /**
         * Check if the cached entry has expired
         *
         * @return the boolean
         */
        val isExpired: Boolean
            get() = Date().after(expiration)

        init {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.MINUTE, EXPIRATION)
            this.expiration = calendar.time
        }
    }

    companion object {
        /**
         * MemCache entries expired in 15 minutes.
         * The entries are checked for expiration and removed on retrieval
         *
         * TODO: Have a MemCacheService to periodically check for expired entries
         */
        private val EXPIRATION = 30 // 30 minutes
    }
}
