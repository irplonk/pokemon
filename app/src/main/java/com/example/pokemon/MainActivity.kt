package com.example.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.main.pokemonList.PokemonListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, PokemonListFragment.newInstance())
                .commitNow()
        }
    }
}