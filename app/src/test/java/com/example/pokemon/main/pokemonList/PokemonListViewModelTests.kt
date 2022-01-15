package com.example.pokemon.main.pokemonList

import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonResponse
import com.example.pokemon.model.PokemonRepository
import com.example.pokemon.utils.ResourcesProvider
import com.example.pokemon.utils.TestScheduleProvider
import com.google.common.truth.Truth
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonListViewModelTests {

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    @Mock
    lateinit var resourcesProvider: ResourcesProvider

    private lateinit var pokemonListViewModel: PokemonListViewModel

    @Before
    fun setUp() {
        pokemonListViewModel = PokemonListViewModel(
            pokemonRepository,
            resourcesProvider,
            schedulerProvider = TestScheduleProvider()
        )
    }

    @Test
    fun `Given InitialIntent, When pokemonRepository_getPokemon returns a successful response, Then emit LoadingState and then FetchedPokemonListState`() {
        // Given
        val response = mock(PokemonResponse::class.java)
        val pokemonListEntries = listOf(mock(Pokemon::class.java))

        // When
        `when`(response.results).thenReturn(pokemonListEntries)
        `when`(pokemonRepository.getPokemon()).thenReturn(Single.just(response))
        val test = pokemonListViewModel.state.test()
        pokemonListViewModel.dispatchIntent(PokemonListIntent.InitialIntent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(2)

        val loadingState = results[0]
        Truth.assertThat(loadingState).isInstanceOf(PokemonListState.LoadingState::class.java)

        val fetchedPokemonListState = results[1]
        Truth.assertThat(fetchedPokemonListState).isInstanceOf(PokemonListState.FetchedPokemonListState::class.java)

        val castedFetchedPokemonListState = fetchedPokemonListState as PokemonListState.FetchedPokemonListState
        Truth.assertThat(castedFetchedPokemonListState.pokemon).isEqualTo(pokemonListEntries)
    }

    @Test
    fun `Given InitialIntent, When pokemonRepository_getPokemon returns an error with a message, Then emit LoadingState and then ErrorState with provided message`() {
        // Given
        val error = Exception("Test error message")

        // When
        `when`(pokemonRepository.getPokemon()).thenReturn(Single.error(error))
        val test = pokemonListViewModel.state.test()
        pokemonListViewModel.dispatchIntent(PokemonListIntent.InitialIntent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(2)

        val loadingState = results[0]
        Truth.assertThat(loadingState).isInstanceOf(PokemonListState.LoadingState::class.java)

        val errorState = results[1]
        Truth.assertThat(errorState).isInstanceOf(PokemonListState.ErrorState::class.java)

        val castedErrorState = errorState as PokemonListState.ErrorState
        Truth.assertThat(castedErrorState.message).isEqualTo(error.message)
    }

    @Test
    fun `Given InitialIntent, When pokemonRepository_getPokemon returns an error with no message, Then emit LoadingState and then ErrorState with provided message`() {
        // Given
        val genericErrorMessage = "This is a generic error message"

        // When
        `when`(resourcesProvider.genericErrorMessage).thenReturn(genericErrorMessage)
        `when`(pokemonRepository.getPokemon()).thenReturn(Single.error(Exception()))
        val test = pokemonListViewModel.state.test()
        pokemonListViewModel.dispatchIntent(PokemonListIntent.InitialIntent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(2)

        val loadingState = results[0]
        Truth.assertThat(loadingState).isInstanceOf(PokemonListState.LoadingState::class.java)

        val errorState = results[1]
        Truth.assertThat(errorState).isInstanceOf(PokemonListState.ErrorState::class.java)

        val castedErrorState = errorState as PokemonListState.ErrorState
        Truth.assertThat(castedErrorState.message).isEqualTo(genericErrorMessage)
    }
}