package com.example.pokemon.main.pokemonList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.data.PokemonListEntry

class PokemonListAdapter(private val onClickPokemonListEntry: (PokemonListEntry) -> Unit) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    private var pokemonListEntries = listOf<PokemonListEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_pokemon_list, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bind(pokemonListEntries[position])
    }

    fun setData(pokemonListEntries: List<PokemonListEntry>) {
        this.pokemonListEntries = pokemonListEntries
        notifyDataSetChanged()
    }

    override fun getItemCount() = pokemonListEntries.size

    inner class PokemonListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTextView = view.findViewById<TextView>(R.id.textView_pokemonName)

        fun bind(pokemonListEntry: PokemonListEntry) {
            nameTextView.text = pokemonListEntry.name
            itemView.setOnClickListener {
                onClickPokemonListEntry(pokemonListEntry)
            }
        }
    }
}