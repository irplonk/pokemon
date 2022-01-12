package com.example.pokemon.data

import com.example.pokemon.data.utils.PokemonApiErrorSingleTransformer
import io.reactivex.Single

interface PokemonRepository {

    fun getPokemonDetails(name: String) : Single<PokemonDetailsResponse>

    fun getPokemon(): Single<PokemonListResponse>
}

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    private fun <T> singleErrorTransformer() = PokemonApiErrorSingleTransformer<T>()

    override fun getPokemonDetails(name: String): Single<PokemonDetailsResponse> {
        return api.pokemon(name).compose(singleErrorTransformer())
    }

    override fun getPokemon(): Single<PokemonListResponse> {
        return api.pokemonList().compose(singleErrorTransformer())
    }
}