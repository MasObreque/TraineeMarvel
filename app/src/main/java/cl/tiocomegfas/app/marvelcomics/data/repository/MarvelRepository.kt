package cl.tiocomegfas.app.marvelcomics.data.repository

import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.data.source.local.LocalDataSource
import cl.tiocomegfas.app.marvelcomics.data.source.remote.RemoteDataSource
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    suspend fun getAllCharacters(): GetAllCharactersResponse {
        return remote.getAllCharacters()
    }

    suspend fun getThumbnailCharacter() {

    }
}