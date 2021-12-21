package com.example.pokemon.main.base

import androidx.lifecycle.ViewModel
import com.example.pokemon.utils.BaseSchedulerProvider
import com.example.pokemon.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<I : BaseIntent, A : BaseAction, S : BaseState>
    (protected val schedulerProvider: BaseSchedulerProvider = SchedulerProvider()) : ViewModel() {

    protected val disposable: CompositeDisposable = CompositeDisposable()
    val state: BehaviorSubject<S> = BehaviorSubject.create()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun dispatchIntent(intent: I) {
        handleAction(convertIntentToAction(intent))
    }

    abstract fun convertIntentToAction(intent: I): A

    abstract fun handleAction(action: A)
}