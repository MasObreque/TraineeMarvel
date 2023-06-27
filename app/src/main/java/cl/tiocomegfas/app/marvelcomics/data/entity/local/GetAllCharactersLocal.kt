package cl.tiocomegfas.app.marvelcomics.data.entity.local

data class GetAllCharactersLocal(
    var characters: List<Character?>? = null
) {
    data class Character(
        var id: Int? = null,
        var name: String? = null,
        var thumbnail: String? = null,
        var comics: Int,
        var series: Int
    )
}
