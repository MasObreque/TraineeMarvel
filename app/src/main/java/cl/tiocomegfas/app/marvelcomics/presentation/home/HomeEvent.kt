package cl.tiocomegfas.app.marvelcomics.presentation.home

sealed class HomeEvent {
    object GetAllCharacters: HomeEvent()
    data class GetThumbnail(val id: Int): HomeEvent()
}
