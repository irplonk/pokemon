package com.example.pokemon.model

import com.example.pokemon.network.PokemonApi
import com.example.pokemon.network.PokemonApiErrorSingleTransformer
import io.reactivex.Single

interface PokemonRepository {

    fun getPokemonDetails(name: String): Single<PokemonDetails>

    fun getPokemon(): Single<PokemonResponse>
}

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    private fun <T> singleErrorTransformer() = PokemonApiErrorSingleTransformer<T>()

    override fun getPokemonDetails(name: String): Single<PokemonDetails> {
        return api.pokemon(name).compose(singleErrorTransformer())
    }

    override fun getPokemon(): Single<PokemonResponse> {
        return api.pokemonList().compose(singleErrorTransformer())
    }
}