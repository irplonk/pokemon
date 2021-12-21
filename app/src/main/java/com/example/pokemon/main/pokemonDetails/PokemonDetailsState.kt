package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.data.PokemonDetailsResponse
import com.example.pokemon.main.base.BaseState

sealed class PokemonDetailsState : BaseState {
    data class FetchedPokemonDetailsState(val pokemonDetails: PokemonDetailsResponse?) :
        PokemonDetailsState()

    object LoadingState : PokemonDetailsState()
    object ErrorState : PokemonDetailsState()
}