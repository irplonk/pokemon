package com.example.pokemon.network

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import retrofit2.adapter.rxjava2.Result

class PokemonApiErrorSingleTransformer<T> : SingleTransformer<Result<T>, T> {
    override fun apply(upstream: Single<Result<T>>): SingleSource<T> {
        return upstream.flatMap { result: Result<T> ->
            if (!result.isError && result.response() != null) {
                val response = result.response()!!
                if (response.isSuccessful) {
                    Single.just(response.body())
                } else {
                    Single.error(Exception())
                }
            } else {
                Single.error(result.error())
            }
        }
    }

}