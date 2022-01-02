package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.main.base.BaseViewModel
import com.example.pokemon.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val resourcesProvider: ResourcesProvider
) :
    BaseViewModel<PokemonDetailsIntent, PokemonDetailsAction, PokemonDetailsState>() {

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
            state.onNext(PokemonDetailsState.ErrorState("Null or empty pokemon name passed"))
            return
        }
        state.onNext(PokemonDetailsState.LoadingState)
        disposable.add(
            pokemonRepository.getPokemonDetails(name)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    { pokemonDetailsResponse ->
                        if (pokemonDetailsResponse == null) {
                            state.onNext(
                                PokemonDetailsState.ErrorState(
                                    resourcesProvider.genericErrorMessage
                                )
                            )
                        } else {
                            state.onNext(
                                PokemonDetailsState.FetchedPokemonDetailsState(
                                    pokemonDetailsResponse
                                )
                            )
                        }
                    },
                    {
                        state.onNext(
                            PokemonDetailsState.ErrorState(
                                it.message ?: resourcesProvider.genericErrorMessage
                            )
                        )
                    }
                )
        )
    }
}