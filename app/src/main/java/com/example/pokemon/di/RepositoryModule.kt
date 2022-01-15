package com.example.pokemon.di

import com.example.pokemon.model.PokemonRepository
import com.example.pokemon.model.PokemonRepositoryImpl
import com.example.pokemon.network.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonApi: PokemonApi): PokemonRepository =
        PokemonRepositoryImpl(pokemonApi)
}