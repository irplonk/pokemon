package com.example.pokemon.main.pokemonDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import com.example.pokemon.data.PokemonDetailsResponse
import com.example.pokemon.ui.ErrorMessage
import com.example.pokemon.ui.LoadingView

const val INITIAL_INTENT = "initial_intent"

@Composable
fun PokemonDetailsScreen(
    pokemonDetailsViewModel: PokemonDetailsViewModel,
    pokemonName: String?,
) {
    val state by pokemonDetailsViewModel.state.subscribeAsState(initial = PokemonDetailsState.LoadingState)
    LaunchedEffect(INITIAL_INTENT) {
        pokemonDetailsViewModel.dispatchIntent(PokemonDetailsIntent.InitialIntent(pokemonName))
    }
    when (state) {
        PokemonDetailsState.LoadingState -> LoadingView()
        is PokemonDetailsState.ErrorState -> ErrorMessage(message = (state as PokemonDetailsState.ErrorState).message)
        is PokemonDetailsState.FetchedPokemonDetailsState -> PokemonDetails((state as PokemonDetailsState.FetchedPokemonDetailsState).pokemonDetails)
    }
}

@Composable
fun PokemonDetails(pokemonDetails: PokemonDetailsResponse) {
    Column {
        Text(text = pokemonDetails.name)
        Text(text = pokemonDetails.weight)
    }
}