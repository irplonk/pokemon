package com.example.pokemon

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.main.pokemonDetails.PokemonDetailsScreen
import com.example.pokemon.main.pokemonDetails.PokemonDetailsViewModel
import com.example.pokemon.main.pokemonList.PokemonListFragment
import com.example.pokemon.main.pokemonList.PokemonListScreen
import com.example.pokemon.main.pokemonList.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use Composable functions
//        setContent {
//            PokemonApp()
//        }
        // Use Fragments
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, PokemonListFragment.newInstance())
                .commitNow()
        }
    }
}

@Composable
fun PokemonApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PokemonList.route) {
        composable(Screen.PokemonList.route) {
            val pokemonListViewModel = hiltViewModel<PokemonListViewModel>()
            PokemonListScreen(pokemonListViewModel) { pokemonName ->
                navController.navigate("${Screen.PokemonDetails.route}/$pokemonName")
            }
        }
        composable("${Screen.PokemonDetails.route}/{${PokemonDetailsArgs.POKEMON_NAME.value}}") { backStackEntry ->
            val pokemonDetailsViewModel = hiltViewModel<PokemonDetailsViewModel>()
            PokemonDetailsScreen(
                pokemonDetailsViewModel,
                pokemonName = backStackEntry.arguments?.getString(
                    PokemonDetailsArgs.POKEMON_NAME.value
                )
            )
        }
    }
}

enum class PokemonDetailsArgs(val value: String) {
    POKEMON_NAME("name")
}

sealed class Screen(val route: String) {
    object PokemonList : Screen("pokemonList")
    object PokemonDetails : Screen("pokemonDetails")
}