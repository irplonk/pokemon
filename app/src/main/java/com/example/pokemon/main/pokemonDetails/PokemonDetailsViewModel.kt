package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.main.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseViewModel<PokemonDetailsIntent, PokemonDetailsAction, PokemonDetailsState>() {

    init {
        state.onNext(PokemonDetailsState.LoadingState)
    }

    override fun convertIntentToAction(intent: PokemonDetailsIntent): PokemonDetailsAction {
        return when (intent) {
            is PokemonDetailsIntent.InitialIntent ->
                PokemonDetailsAction.FetchPokemonDetailsAction(intent.name)
        }
    }

    override fun handleAction(action: PokemonDetailsAction) {
        when (action) {
            is PokemonDetailsAction.FetchPokemonDetailsAction -> fetchPokemonDetails(action.name)
        }
    }

    private fun fetchPokemonDetails(name: String?) {
        if (name.isNullOrEmpty()) {
            state.onNext(PokemonDetailsState.ErrorState)
            return
        }
        disposable.add(
            pokemonRepository.getPokemonDetails(name)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        state.onNext(PokemonDetailsState.FetchedPokemonDetailsState(it))
                    },
                    {
                        state.onNext(PokemonDetailsState.ErrorState)
                    }
                )
        )
    }
}