package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.main.base.BaseAction

sealed class PokemonDetailsAction : BaseAction {
    data class FetchPokemonDetailsAction(val name: String?) : PokemonDetailsAction()
}