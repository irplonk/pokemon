package com.example.pokemon.main.pokemonList

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokemon.main.pokemonDetails.INITIAL_INTENT
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.ErrorMessage
import com.example.pokemon.ui.LoadingView

@ExperimentalFoundationApi
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
    Crossfade(targetState = state) {
        when (it) {
            PokemonListState.LoadingState -> LoadingView()
            is PokemonListState.ErrorState -> ErrorMessage(message = it.message)
            is PokemonListState.FetchedPokemonListState -> PokemonList(
                it.pokemon,
                navigateToPokemonDetails
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    navigateToPokemonDetails: (pokemonName: String) -> Unit
) {
    LazyVerticalGrid(cells = GridCells.Fixed(count = 2)) {
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
fun PokemonListItem(pokemon: Pokemon, onClick: (String) -> Unit) {
    Column(modifier = Modifier.clickable {
        onClick(pokemon.name)
    }) {
        Image(
            painter = rememberImagePainter(pokemon.imageUrl),
            contentDescription = pokemon.name,
            modifier = Modifier.size(128.dp)
        )
        Text(text = pokemon.name)
    }
}