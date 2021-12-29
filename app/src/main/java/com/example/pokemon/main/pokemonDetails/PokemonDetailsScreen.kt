package com.example.pokemon.main.pokemonDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PokemonDetailsScreen(
    pokemonDetailsViewModel: PokemonDetailsViewModel = viewModel()
) {
    val state by pokemonDetailsViewModel.state.subscribeAsState(initial = PokemonDetailsState.LoadingState)
    // TODO: Continue here
}