package com.example.pokemon.main.pokemonList

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.main.base.BaseViewModel
import com.example.pokemon.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pokemonRepository: PokemonRepository,
    resourcesProvider: ResourcesProvider
) :
    BaseViewModel<PokemonListIntent, PokemonListAction, PokemonListState>() {

    init {
        disposable.add(
            pokemonRepository.getPokemon()
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        state.onNext(PokemonListState.FetchedPokemonListState(it.results))
                    },
                    {
                        state.onNext(
                            PokemonListState.ErrorState(
                                it.message ?: resourcesProvider.genericErrorMessage
                            )
                        )
                    }
                )
        )
    }

    override fun convertIntentToAction(intent: PokemonListIntent): PokemonListAction {
        TODO("Not yet implemented")
    }

    override fun handleAction(action: PokemonListAction) {
        TODO("Not yet implemented")
    }
}