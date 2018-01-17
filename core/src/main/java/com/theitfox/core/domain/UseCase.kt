package com.theitfox.core.domain

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * Created by btquanto on 1/17/18.
 *
 *
 * This class is the super class of use cases
 * Use cases represent business logic. Each use case represents one logic.
 *
 * @param <T> the type of event the use case promises to emit upon execution
</T> */
abstract class UseCase<T>(
        protected val executionThread: Scheduler,
        protected val postExecutionThread: Scheduler) {

    /**
     * Execute when the use case is completed
     */
    protected var onCompleteAction: Action? = EmptyAction<Any>()

    /**
     * Execute when an error occurs
     */
    protected var onErrorAction: Consumer<Throwable>? = EmptyAction()

    /**
     * Execute when an event is emitted
     */
    protected var onNextAction: Consumer<T>? = EmptyAction()

    /**
     * Setter for onCompletionAction
     *
     * @param action An OnCompleteAction object
     * @return this use case
     */
    fun onComplete(action: Action?): UseCase<T> {
        this.onCompleteAction = action ?: this.onCompleteAction
        return this
    }

    /**
     * Setter for onErrorAction
     *
     * @param action An OnErrorAction object
     * @return this use case
     */
    fun onError(action: Consumer<Throwable>?): UseCase<T> {
        this.onErrorAction = action ?: this.onErrorAction
        return this
    }

    /**
     * Setter for onNextAction
     *
     * @param action An OnNextAction object
     * @return this use case
     */
    fun onNext(action: Consumer<T>?): UseCase<T> {
        this.onNextAction = action ?: this.onNextAction
        return this
    }

    /**
     * Build the Observable that promises the output of the use case
     *
     * @return an Observable that promises the output of this use case
     */
    protected abstract fun buildUseCaseObservable(): Observable<T>

    /**
     * Build the final Observable to be executed
     * This by default just calls[.buildUseCaseObservable]
     * However, it can be overridden for extended implementation
     * For example, merging [.buildUseCaseObservable] with another Observable
     *
     * @return an Observable that promises the output of this use case
     */
    protected open fun buildExecutionObservable(): Observable<T> {
        return buildUseCaseObservable()
    }

    /**
     * Execute the use case.
     * The emitted output will be processed by the actions that were set:
     * - onNextAction
     * - onErrorAction
     * - onCompleteAction
     * Any actions that were not set will be ignored
     *
     * @return the subscription that manages the life cycle of the execution observable
     */
    fun execute(): Disposable {
        return buildExecutionObservable()
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .subscribe(onNextAction, onErrorAction, onCompleteAction)
    }

    class EmptyAction<T> : Action, Consumer<T> {
        override fun run() {
        }

        override fun accept(t: T) {
        }
    }
}


