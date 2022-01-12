package com.example.pokemon.main.pokemonDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pokemon.databinding.FragmentPokemonDetailsBinding
import com.example.pokemon.ext.viewLifecycle
import com.example.pokemon.main.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment :
    BaseFragment<PokemonDetailsIntent, PokemonDetailsAction, PokemonDetailsState, PokemonDetailsViewModel>() {

    override val viewModel: PokemonDetailsViewModel by viewModels()
    private var binding by viewLifecycle<FragmentPokemonDetailsBinding>()

    private val pokemonName by lazy { arguments?.getString(ARG_NAME) }

    override val initialIntent
        get() = PokemonDetailsIntent.InitialIntent(pokemonName)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun render(state: PokemonDetailsState) {
        when (state) {
            is PokemonDetailsState.FetchedPokemonDetailsState -> {
                binding.progressBarLoading.visibility = View.GONE
                binding.textViewPokemonName.text = pokemonName
                binding.textViewPokemonWeight.text = state.pokemonDetails?.weight
            }
            is PokemonDetailsState.LoadingState -> {
                binding.progressBarLoading.visibility = View.VISIBLE
            }
            is PokemonDetailsState.ErrorState -> {
                binding.progressBarLoading.visibility = View.GONE
                binding.textViewErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val ARG_NAME = "arg_name"

        fun newInstance(name: String): PokemonDetailsFragment {
            return PokemonDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
        }
    }
}