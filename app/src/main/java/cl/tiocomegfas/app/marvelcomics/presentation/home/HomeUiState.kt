package cl.tiocomegfas.app.marvelcomics.presentation.home

import cl.tiocomegfas.app.marvelcomics.data.entity.mapper.GetAllCharactersMapper

data class HomeUiState(
    var isLoading: Boolean = false,
    var characters: GetAllCharactersAction = GetAllCharactersAction.InitialState,
) {

    sealed class GetAllCharactersAction {
        object InitialState: GetAllCharactersAction()
        data class Success(val mapper: GetAllCharactersMapper): GetAllCharactersAction()
        object Empty: GetAllCharactersAction()
        object Error: GetAllCharactersAction()
    }
}
