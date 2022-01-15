package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.main.base.BaseState
import com.example.pokemon.model.PokemonDetails

sealed class PokemonDetailsState : BaseState {
    data class FetchedPokemonDetailsState(val pokemonDetails: PokemonDetails) :
        PokemonDetailsState()

    object LoadingState : PokemonDetailsState()
    data class ErrorState(val message: String) : PokemonDetailsState()
}