package com.example.pokemon.data

import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon")
    fun pokemonList(): Single<Result<PokemonListResponse>>

    @GET("pokemon/{name}")
    fun pokemon(@Path("name") name: String): Single<Result<PokemonDetailsResponse>>
}