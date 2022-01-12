package com.example.pokemon.main.pokemonList

import com.example.pokemon.main.base.BaseIntent

sealed class PokemonListIntent : BaseIntent {
    object InitialIntent : PokemonListIntent()
}