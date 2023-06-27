package cl.tiocomegfas.app.marvelcomics.data.repository

import cl.tiocomegfas.app.marvelcomics.data.entity.local.GetAllCharactersLocal
import cl.tiocomegfas.app.marvelcomics.data.entity.request.GetThumbnailCharacterRequest
import cl.tiocomegfas.app.marvelcomics.data.source.local.LocalDataSource
import cl.tiocomegfas.app.marvelcomics.data.source.remote.RemoteDataSource
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    suspend fun getAllCharacters(): GetAllCharactersLocal {
        if(!local.isCharactersSaved()) {
            val response = remote.getAllCharacters()
            local.saveAllCharacters(response)
        }
        return local.getAllCharacters()
    }

    suspend fun getThumbnail(request: GetThumbnailCharacterRequest): ByteArray {
        if(!local.isThumbnailSaved(request.id)) {
            val response = remote.getThumbnail(local.getCharacter(request.id).thumbnailURL)
            local.saveCharacterThumbnail(request.id, response)
        }
        return local.getThumbnail(request.id)
    }
}