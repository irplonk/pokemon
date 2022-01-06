package com.example.pokemon.main.pokemonDetails

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
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val pokemonDetailsState = pokemonDetailsViewModel.state.value
        Truth.assertThat(pokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.ErrorState::class.java)
        val errorState = pokemonDetailsState as PokemonDetailsState.ErrorState
        Truth.assertThat(errorState.message).isEqualTo("Null or empty pokemon name passed")
    }

    @Test
    fun `Given InitialIntent with valid name, When dispatchIntent is called and pokemonRepository_getPokemonDetails returns error with message, Then emit ErrorState with error message`() {
        // Given
        val exception = Exception("Test error message")
        val intent = PokemonDetailsIntent.InitialIntent(pokemonName)

        // When
        `when`(pokemonRepository.getPokemonDetails(pokemonName)).thenReturn(Single.error(exception))
        pokemonDetailsViewModel.dispatchIntent(intent)

        // Then
        val pokemonDetailsState = pokemonDetailsViewModel.state.value
        Truth.assertThat(pokemonDetailsState)
            .isInstanceOf(PokemonDetailsState.ErrorState::class.java)
        val errorState = pokemonDetailsState as PokemonDetailsState.ErrorState
        Truth.assertThat(errorState.message).isEqualTo(exception.message)
    }
}