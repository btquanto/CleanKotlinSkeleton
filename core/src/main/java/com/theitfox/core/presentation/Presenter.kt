package com.theitfox.core.presentation

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by btquanto on 1/17/18.
 *
 *
 * The base Presenter that all Presenters extends from
 *
 * @param <V>   The type of mvp view that should be attached to this presenter
 * @param <UCP> the type parameter for UseCaseProvider
 * */
abstract class Presenter<V : BaseView, UCP>(protected var useCaseProvider: UCP) {
    /**
     * The mvp view that should be attached to this presenter.
     */
    protected var view: V? = null

    /**
     * The composite disposables that manages the life cycles of all use cases
     */
    protected var disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Check if there is a view is attached to this presenter
     *
     * @return the boolean
     */
    protected val isViewAttached: Boolean
        get() = this.view != null

    /**
     * Attach a view to this presenter
     *
     * @param view the view that will be attached to this presenter
     */
    fun attachView(view: V) {
        this.view = view
    }

    /**
     * Detach the view from this presenter
     */
    fun detachView() {
        this.view = null
        this.disposables.clear()
    }
}
