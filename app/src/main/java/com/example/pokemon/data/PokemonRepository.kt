package com.example.pokemon.data

import com.example.pokemon.data.utils.PokemonApiErrorSingleTransformer
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(private val api: PokemonApi) {

    private fun <T> singleErrorTransformer() = PokemonApiErrorSingleTransformer<T>()

    fun getPokemonDetails(name: String): Single<PokemonDetailsResponse> {
        return api.pokemon(name).compose(singleErrorTransformer())
    }

    fun getPokemon(): Single<PokemonListResponse> {
        return api.pokemonList().compose(singleErrorTransformer())
    }
}