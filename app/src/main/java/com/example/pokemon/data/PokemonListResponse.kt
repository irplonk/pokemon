package com.example.pokemon.data

data class PokemonListResponse(
    val count: String,
    val next: String,
    val previous: String,
    val results: List<PokemonListEntry>
)

data class PokemonListEntry(
    val name: String,
    val url: String
)
