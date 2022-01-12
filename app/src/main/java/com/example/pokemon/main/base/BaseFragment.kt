package com.example.pokemon.main.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<I : BaseIntent, A : BaseAction, S : BaseState, VM : BaseViewModel<I, A, S>> :
    Fragment(), BaseView<I, S> {

    protected abstract val viewModel: VM

    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable.add(viewModel.state.subscribe(::render))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dispatchIntent(initialIntent)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}