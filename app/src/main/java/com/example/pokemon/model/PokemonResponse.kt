package com.example.pokemon.model

data class PokemonResponse(
    val count: String,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)
