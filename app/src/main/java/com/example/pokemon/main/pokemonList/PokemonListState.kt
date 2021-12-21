package com.example.pokemon.main.pokemonList

import com.example.pokemon.data.PokemonListEntry
import com.example.pokemon.main.base.BaseState

sealed class PokemonListState : BaseState {
    data class FetchedPokemonListState(val pokemonListEntries: List<PokemonListEntry>) :
        PokemonListState()

    object LoadingState : PokemonListState()
    object ErrorState : PokemonListState()
}
