package cl.tiocomegfas.app.marvelcomics.data.entity.mapper

data class GetAllCharactersMapper(
    val characters: List<Character>
) {
    data class Character(
        val id: Int,
        val name: String,
        val thumbnailURL: String,
    )
}
