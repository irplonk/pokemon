package com.example.pokemon.di

import com.example.pokemon.data.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Singleton
    @Provides
    fun providesPokemonApi(client: OkHttpClient): PokemonApi {
        return Retrofit.Builder().baseUrl(POKEMON_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    companion object {
        private const val POKEMON_API_BASE_URL = "https://pokeapi.co/api/v2/"
    }
}