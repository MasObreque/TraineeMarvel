package cl.tiocomegfas.app.marvelcomics.data.source.local

import android.content.Context
import cl.tiocomegfas.app.marvelcomics.data.entity.local.GetAllCharactersLocal
import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.util.getIdByURI
import cl.tiocomegfas.app.marvelcomics.util.persistence.PersistenceConfiguration
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.Writer
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: PersistenceConfiguration<MarvelDatabase>
) {
    suspend fun saveAllCharacters(response: GetAllCharactersResponse) {
        setMarvelDatabase {
            val characterList: MutableList<MarvelDatabase.Character> = mutableListOf()
            response.content.results.forEach { character ->
                val comicsList = mutableListOf<MarvelDatabase.Comic>()
                character.comics.items.forEach { comics ->
                    comicsList.add(
                        MarvelDatabase.Comic(
                            id = comics.resourceURI.getIdByURI() ?: -1,
                            name = comics.name
                        )
                    )
                }
                val seriesList = mutableListOf<MarvelDatabase.Series>()
                character.series.items.forEach { series ->
                    seriesList.add(
                        MarvelDatabase.Series(
                            id = series.resourceURI.getIdByURI() ?: -1,
                            name = series.name
                        )
                    )
                }
                characterList.add(
                    MarvelDatabase.Character(
                        id = character.id,
                        name = character.name,
                        thumbnailURL = StringBuilder().append(character.thumbnail.path).append(".").append(character.thumbnail.extension).toString(),
                        comics = comicsList,
                        series = seriesList
                    )
                )
            }
            it.copy(
                characters = characterList
            )
        }
    }

    suspend fun getCharacter(id: Int): MarvelDatabase.Character {
        return getMarvelDatabase().characters.find { it.id == id } ?: throw Exception("Character not found")
    }

    suspend fun getAllCharacters(): GetAllCharactersLocal {
        val result = mutableListOf<GetAllCharactersLocal.Character>()
        getMarvelDatabase().characters.forEach {
            result.add(
                GetAllCharactersLocal.Character(
                    id = it.id,
                    name = it.name,
                    comics = it.comics.size,
                    series = it.series.size
                )
            )
        }
        return GetAllCharactersLocal(
            characters = result
        )
    }

    suspend fun isCharactersSaved(): Boolean {
        return getMarvelDatabase().characters.isNotEmpty()
    }

    suspend fun isThumbnailSaved(id: Int): Boolean {
        return getMarvelDatabase().characters.find { it.id == id }?.thumbnailURI?.isNotEmpty() ?: false
    }

    private suspend fun setMarvelDatabase(action: (MarvelDatabase) -> MarvelDatabase) {
        database.setPreferences(action.invoke(getMarvelDatabase()))
    }

    private suspend fun getMarvelDatabase(): MarvelDatabase {
        return database.getPreferences()
    }

    suspend fun saveCharacterThumbnail(id: Int, response: ByteArray) {
        val file = File(context.filesDir.absoluteFile.absolutePath, "character")
        file.mkdirs()
        val image = File(file, "$id.jpg")
        withContext(Dispatchers.IO) {
            val stream = BufferedOutputStream(FileOutputStream(image, false))
            stream.write(response)
            stream.flush()
            stream.close()
        }
        setMarvelDatabase {
            it.copy(
                characters = it.characters.map { character ->
                    if(character.id == id) {
                        character.copy(
                            thumbnailURI = image.absolutePath
                        )
                    } else {
                        character
                    }
                }
            )
        }
    }

    suspend fun getThumbnail(id: Int): ByteArray {
        val character = getMarvelDatabase().characters.find { it.id == id } ?: throw Exception("Character not found")
        val path = character.thumbnailURI ?: throw Exception("Thumbnail not found")
        return File(path).readBytes()
    }
}