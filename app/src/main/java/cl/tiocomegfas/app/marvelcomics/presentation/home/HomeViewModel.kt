package cl.tiocomegfas.app.marvelcomics.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.tiocomegfas.app.marvelcomics.data.entity.request.GetThumbnailCharacterRequest
import cl.tiocomegfas.app.marvelcomics.domain.exception.ResponseEmptyException
import cl.tiocomegfas.app.marvelcomics.domain.usecase.GetAllCharactersUseCase
import cl.tiocomegfas.app.marvelcomics.domain.usecase.GetThumbnailUseCase
import cl.tiocomegfas.app.marvelcomics.util.base.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val getThumbnailUseCase: GetThumbnailUseCase
): ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    private val thumbnailsCalled: MutableList<Int> = mutableListOf()

    init {
        executeEvent(HomeEvent.GetAllCharacters)
    }

    fun executeEvent(event: HomeEvent) {
        viewModelScope.launch {
            when(event) {
                HomeEvent.GetAllCharacters -> getAllCharacters()
                is HomeEvent.GetThumbnail -> getAllThumbnail(event.id)
            }
        }
    }

    private suspend fun getAllCharacters() {
        uiState = uiState.copy(isLoading = true)
        getAllCharactersUseCase.invoke(null).collect { result ->
            uiState = when(result) {
                is ResourceState.Error -> {
                    when(result.error) {
                        is ResponseEmptyException -> uiState.copy(isLoading = false, characters = HomeUiState.GetAllCharactersAction.Empty)
                        else -> uiState.copy(isLoading = false, characters = HomeUiState.GetAllCharactersAction.Error)
                    }
                }
                is ResourceState.Success -> {
                    uiState.copy(isLoading = false, characters = HomeUiState.GetAllCharactersAction.Success(result.data))
                }
            }
        }
    }

    private suspend fun getAllThumbnail(id: Int) {
        if(thumbnailsCalled.find { it == id } == null) {
            thumbnailsCalled.add(id)
            getThumbnailUseCase.invoke(
                GetThumbnailCharacterRequest(id)
            ).collect { result ->
                when(result) {
                    is ResourceState.Error -> {
                        result.error.printStackTrace()
                        Log.e("TAG", "Ocurrio un error al obtener el thumbnail")
                    }
                    is ResourceState.Success -> {
                        val mapper = (uiState.characters as HomeUiState.GetAllCharactersAction.Success).mapper
                        mapper.characters.find {
                            it.id == id
                        }?.bitmap = result.data.thumbnail
                        uiState = uiState.copy(characters = HomeUiState.GetAllCharactersAction.Success(mapper))
                    }
                }
            }
        }
    }
}