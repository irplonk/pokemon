package com.example.pokemon.main.pokemonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pokemon.data.PokemonListEntry
import com.example.pokemon.databinding.FragmentPokemonListBinding
import com.example.pokemon.ext.viewLifecycle
import com.example.pokemon.main.base.BaseFragment
import com.example.pokemon.main.pokemonDetails.PokemonDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment :
    BaseFragment<PokemonListIntent, PokemonListAction, PokemonListState, PokemonListViewModel>() {

    override val viewModel: PokemonListViewModel by viewModels()

    private var binding by viewLifecycle<FragmentPokemonListBinding>()

    private var pokemonListAdapter: PokemonListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pokemonListAdapter = PokemonListAdapter(::onClickPokemonListEntry)
        binding = FragmentPokemonListBinding.inflate(inflater, container, false).apply {
            recyclerViewPokemonList.adapter = pokemonListAdapter
        }
        return binding.root
    }

    override fun onDestroyView() {
        pokemonListAdapter = null
        super.onDestroyView()
    }

    override fun render(state: PokemonListState) {
        when (state) {
            is PokemonListState.FetchedPokemonListState -> {
                binding.progressBarLoading.visibility = View.GONE
                pokemonListAdapter?.setData(state.pokemonListEntries)
            }
            is PokemonListState.LoadingState -> {
                binding.progressBarLoading.visibility = View.VISIBLE
            }
            is PokemonListState.ErrorState -> {
                binding.progressBarLoading.visibility = View.GONE
                binding.textViewErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun onClickPokemonListEntry(pokemonListEntry: PokemonListEntry) {
        val name = pokemonListEntry.name
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack(TAG)
        fragmentTransaction?.replace(android.R.id.content, PokemonDetailsFragment.newInstance(name))
            ?.commit()
    }

    companion object {
        private const val TAG = "PokemonListFragment"
        fun newInstance() = PokemonListFragment()
    }
}