package cl.tiocomegfas.app.marvelcomics.data.entity.mapper

import android.graphics.Bitmap

data class GetAllCharactersMapper(
    val characters: List<Character>
) {
    data class Character(
        val id: Int,
        var bitmap: Bitmap? = null,
        val name: String,
        val comics: Int,
        val series: Int
    )
}
