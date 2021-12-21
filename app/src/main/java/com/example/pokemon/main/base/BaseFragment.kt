package com.example.pokemon.main.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<I : BaseIntent, A : BaseAction, S : BaseState, VM : BaseViewModel<I, A, S>> :
    Fragment(), BaseView<S> {

    protected abstract val viewModel: VM

    private var disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(viewModel.state.subscribe(::render))
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}