package com.example.pokemon.main.base

interface BaseView<I: BaseIntent, S : BaseState> {

    val initialIntent: I

    fun render(state: S)
}