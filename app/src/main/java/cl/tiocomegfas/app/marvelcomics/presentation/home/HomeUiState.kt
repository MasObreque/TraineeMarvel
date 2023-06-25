package cl.tiocomegfas.app.marvelcomics.presentation.home

import cl.tiocomegfas.app.marvelcomics.data.entity.mapper.GetAllCharactersMapper

data class HomeUiState(
    var isLoading: Boolean = false,
    var characters: GetAllCharactersMapper? = null,
)
