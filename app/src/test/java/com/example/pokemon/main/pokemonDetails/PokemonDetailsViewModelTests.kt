package com.example.pokemon.main.pokemonDetails

import com.example.pokemon.data.PokemonDetailsResponse
import com.example.pokemon.data.PokemonRepository
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
class PokemonDetailsViewModelTests {

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    @Mock
    lateinit var resourcesProvider: ResourcesProvider

    lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel

    private val pokemonName = "pikachu"

    @Before
    fun setUp() {
        pokemonDetailsViewModel = PokemonDetailsViewModel(
            pokemonRepository,
            resourcesProvider,
            schedulerProvider = TestScheduleProvider()
        )
    }

    @Test
    fun `Given InitialIntent with null name, When dispatchIntent is called, Then emit ErrorState`() {
        // Given
        val intent = PokemonDetailsIntent.InitialIntent(name = null)

        // When
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val pokemonDetailsState = pokemonDetailsViewModel.state.value
        Truth.assertThat(pokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.ErrorState::class.java)
        val errorState = pokemonDetailsState as PokemonDetailsState.ErrorState
        Truth.assertThat(errorState.message).isEqualTo("Null or empty pokemon name passed")
    }

    @Test
    fun `Given InitialIntent with empty name, When dispatchIntent is called, Then emit ErrorState`() {
        // Given
        val intent = PokemonDetailsIntent.InitialIntent(name = "")

        // When
        val test = pokemonDetailsViewModel.state.test()
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(1)

        val pokemonDetailsState = results[0]
        Truth.assertThat(pokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.ErrorState::class.java)

        val errorState = pokemonDetailsState as PokemonDetailsState.ErrorState
        Truth.assertThat(errorState.message).isEqualTo("Null or empty pokemon name passed")
    }

    @Test
    fun `Given InitialIntent with valid name, When dispatchIntent is called and pokemonRepository_getPokemonDetails returns error with message, Then emit LoadingState and then ErrorState with error message`() {
        // Given
        val exception = Exception("Test error message")
        val intent = PokemonDetailsIntent.InitialIntent(pokemonName)

        // When
        `when`(pokemonRepository.getPokemonDetails(pokemonName)).thenReturn(Single.error(exception))
        val test = pokemonDetailsViewModel.state.test()
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(2)

        val firstPokemonDetailsState = results[0]
        Truth.assertThat(firstPokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.LoadingState::class.java)

        val secondPokemonDetailsState = results[1]
        Truth.assertThat(secondPokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.ErrorState::class.java)
        val errorState = secondPokemonDetailsState as PokemonDetailsState.ErrorState
        Truth.assertThat(errorState.message).isEqualTo(exception.message)
    }

    @Test
    fun `Given InitialIntent with valid name, When dispatchIntent is called and pokemonRepository_getPokemonDetails returns error with no message, Then emit LoadingState and then ErrorState with error message`() {
        // Given
        val genericErrorMessage = "This is a generic error message"
        val exception = Exception()
        val intent = PokemonDetailsIntent.InitialIntent(pokemonName)

        // When
        `when`(resourcesProvider.genericErrorMessage).thenReturn(genericErrorMessage)
        `when`(pokemonRepository.getPokemonDetails(pokemonName)).thenReturn(Single.error(exception))
        val test = pokemonDetailsViewModel.state.test()
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(2)

        val firstPokemonDetailsState = results[0]
        Truth.assertThat(firstPokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.LoadingState::class.java)

        val secondPokemonDetailsState = results[1]
        Truth.assertThat(secondPokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.ErrorState::class.java)
        val errorState = secondPokemonDetailsState as PokemonDetailsState.ErrorState
        Truth.assertThat(errorState.message).isEqualTo(genericErrorMessage)
    }

    @Test
    fun `Given InitialIntent with valid name, When dispatchIntent is called and pokemonRepository_getPokemonDetails returns a successful response, Then emit LoadingState and then FetchedPokemonDetailsState`() {
        // Given
        val response = mock(PokemonDetailsResponse::class.java)
        val intent = PokemonDetailsIntent.InitialIntent(pokemonName)

        // When
        `when`(pokemonRepository.getPokemonDetails(pokemonName)).thenReturn(Single.just(response))
        val test = pokemonDetailsViewModel.state.test()
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val results = test.values()
        Truth.assertThat(results).hasSize(2)

        val firstPokemonDetailsState = results[0]
        Truth.assertThat(firstPokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.LoadingState::class.java)

        val secondPokemonDetailsState = results[1]
        Truth.assertThat(secondPokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.FetchedPokemonDetailsState::class.java)
        val fetchedPokemonDetailsState = secondPokemonDetailsState as PokemonDetailsState.FetchedPokemonDetailsState
        Truth.assertThat(fetchedPokemonDetailsState.pokemonDetails).isEqualTo(response)
    }
}