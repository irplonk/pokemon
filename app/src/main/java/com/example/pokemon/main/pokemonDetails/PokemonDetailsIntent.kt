package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.main.base.BaseIntent

sealed class PokemonDetailsIntent : BaseIntent {
    data class InitialIntent(val name: String?) : PokemonDetailsIntent()
}