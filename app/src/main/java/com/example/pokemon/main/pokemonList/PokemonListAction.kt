package com.example.pokemon.main.pokemonList

import com.example.pokemon.main.base.BaseAction

sealed class PokemonListAction : BaseAction {
    object FetchPokemonListAction : PokemonListAction()
}