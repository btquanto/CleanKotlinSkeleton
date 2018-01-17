package com.theitfox.core.domain

import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by btquanto on 1/17/18.
 *
 *
 * This class extends the capability of the UseCase class
 * It allows use cases that first try to retrieve results from a cache
 * before fetching the result from another source
 *
 * @param <T> the type parameter
</T> */
abstract class UseCaseWithCache<T>
(executionThread: Scheduler, postExecutionThread: Scheduler)
    : UseCase<T>(executionThread, postExecutionThread) {

    override fun buildExecutionObservable(): Observable<T> {
        val queryCacheObservable = this.buildCacheRetrievalObservable()
        val useCaseObservable = super.buildExecutionObservable()
        return if (queryCacheObservable != null) {
            queryCacheObservable!!
                    .mergeWith(useCaseObservable)
                    .filter({ response -> response != null })
        } else useCaseObservable
    }

    /**
     * Build an observable that promises the cached value
     *
     * @return the observable
     */
    protected open fun buildCacheRetrievalObservable(): Observable<T>? {
        return null
    }
}
