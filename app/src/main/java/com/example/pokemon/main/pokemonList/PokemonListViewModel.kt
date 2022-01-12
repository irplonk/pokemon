package com.example.pokemon.main.pokemonList

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.main.base.BaseViewModel
import com.example.pokemon.utils.BaseSchedulerProvider
import com.example.pokemon.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val resourcesProvider: ResourcesProvider,
    private val schedulerProvider: BaseSchedulerProvider
) :
    BaseViewModel<PokemonListIntent, PokemonListAction, PokemonListState>() {

    override fun convertIntentToAction(intent: PokemonListIntent) =
        when (intent) {
            PokemonListIntent.InitialIntent -> PokemonListAction.FetchPokemonListAction
        }

    override fun handleAction(action: PokemonListAction) {
        when (action) {
            PokemonListAction.FetchPokemonListAction -> fetchPokemonList()
        }
    }

    private fun fetchPokemonList() {
        state.onNext(PokemonListState.LoadingState)
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
}