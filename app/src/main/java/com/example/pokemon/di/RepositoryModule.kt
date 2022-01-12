package com.example.pokemon.di

import com.example.pokemon.data.PokemonApi
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.data.PokemonRepositoryImpl
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