package com.example.pokemon

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.main.pokemonDetails.PokemonDetailsScreen
import com.example.pokemon.main.pokemonList.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonApp()
        }
    }
}

@Composable
fun PokemonApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PokemonList.route) {
        composable(Screen.PokemonList.route) {
            PokemonListScreen({ pokemonName ->
                navController.navigate("${Screen.PokemonDetails.route}/$pokemonName")
            })
        }
        composable("${Screen.PokemonDetails.route}/${PokemonDetailsArgs.POKEMON_NAME.value}") { navBackStackEntry ->
            PokemonDetailsScreen(
                pokemonName = navBackStackEntry.arguments?.getString(
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