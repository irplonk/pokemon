package com.example.pokemon.main.pokemonList

import com.example.pokemon.main.base.BaseState
import com.example.pokemon.model.Pokemon

sealed class PokemonListState : BaseState {
    data class FetchedPokemonListState(val pokemon: List<Pokemon>) :
        PokemonListState()

    object LoadingState : PokemonListState()
    data class ErrorState(val message: String) : PokemonListState()
}
