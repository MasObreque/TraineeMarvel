package cl.tiocomegfas.app.marvelcomics.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.tiocomegfas.app.marvelcomics.domain.usecase.GetAllCharactersUseCase
import cl.tiocomegfas.app.marvelcomics.util.base.ResourceState
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
): ViewModel() {
    var uiState: HomeUiState = HomeUiState()
        private set

    fun executeEvent(event: HomeEvent) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            getAllCharactersUseCase.invoke(null).collect {
                uiState = uiState.copy(isLoading = false)
                when(it) {
                    is ResourceState.Error -> {

                    }
                    is ResourceState.Success -> {

                    }
                }
            }
        }
    }


    private fun getAllCharacters() {

    }
}