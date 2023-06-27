package cl.tiocomegfas.app.marvelcomics.data.source.local

import kotlinx.serialization.Serializable

@Serializable
data class MarvelDatabase(
    var characters: List<Character> = emptyList()
)  {
    @Serializable
    data class Character(
        var id: Int,
        var name: String,
        var thumbnailURL: String,
        var thumbnailURI: String? = null,
        var comics: List<Comic>,
        var series: List<Series>
    )
    @Serializable
    data class Comic(
        var id: Int,
        var name: String,
        var tomo: String? = null
    )
    @Serializable
    data class Series(
        var id: Int,
        var name: String,
    )
}