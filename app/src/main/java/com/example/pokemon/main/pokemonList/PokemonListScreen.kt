package com.example.pokemon.main.pokemonList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemon.data.PokemonListEntry
import com.example.pokemon.main.pokemonDetails.INITIAL_INTENT
import com.example.pokemon.ui.ErrorMessage
import com.example.pokemon.ui.LoadingView

@Composable
fun PokemonListScreen(
    pokemonListViewModel: PokemonListViewModel,
    navigateToPokemonDetails: (pokemonName: String) -> Unit
) {
    val state: PokemonListState
            by pokemonListViewModel.state.subscribeAsState(initial = PokemonListState.LoadingState)
    LaunchedEffect(INITIAL_INTENT) {
        pokemonListViewModel.dispatchIntent(PokemonListIntent.InitialIntent)
    }
    when (val currentState = state) {
        PokemonListState.LoadingState -> LoadingView()
        is PokemonListState.ErrorState -> ErrorMessage(message = currentState.message)
        is PokemonListState.FetchedPokemonListState -> PokemonList(
            currentState.pokemonListEntries,
            navigateToPokemonDetails
        )
    }
}

@Composable
fun PokemonList(
    pokemonList: List<PokemonListEntry>,
    navigateToPokemonDetails: (pokemonName: String) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(
            items = pokemonList,
            itemContent = {
                PokemonListItem(pokemon = it) { pokemonName ->
                    navigateToPokemonDetails(pokemonName)
                }
            }
        )
    }
}

@Composable
fun PokemonListItem(pokemon: PokemonListEntry, onClick: (String) -> Unit) {
    Text(text = pokemon.name, modifier = Modifier.clickable {
        onClick(pokemon.name)
    })
}