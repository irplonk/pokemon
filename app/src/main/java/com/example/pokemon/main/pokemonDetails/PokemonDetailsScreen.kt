package com.example.pokemon.main.pokemonDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemon.data.PokemonDetailsResponse
import com.example.pokemon.ui.ErrorMessage
import com.example.pokemon.ui.LoadingView

@Composable
fun PokemonDetailsScreen(
    pokemonName: String?,
    pokemonDetailsViewModel: PokemonDetailsViewModel = viewModel(),
) {
    val state by pokemonDetailsViewModel.state.subscribeAsState(initial = PokemonDetailsState.LoadingState)
    when (state) {
        PokemonDetailsState.LoadingState -> LoadingView()
        is PokemonDetailsState.ErrorState -> ErrorMessage(message = (state as PokemonDetailsState.ErrorState).message)
        is PokemonDetailsState.FetchedPokemonDetailsState -> PokemonDetails((state as PokemonDetailsState.FetchedPokemonDetailsState).pokemonDetails)
    }
}

@Composable
fun PokemonDetails(pokemonDetails: PokemonDetailsResponse?) {
    if (pokemonDetails == null) {
        ErrorMessage(message = "No details for this pokemon")
    } else {
        Column {
            Text(text = pokemonDetails.name)
            Text(text = pokemonDetails.weight)
        }
    }
}