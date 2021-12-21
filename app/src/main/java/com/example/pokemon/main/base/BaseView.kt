package com.example.pokemon.main.base

interface BaseView<S : BaseState> {

    fun render(state: S)
}