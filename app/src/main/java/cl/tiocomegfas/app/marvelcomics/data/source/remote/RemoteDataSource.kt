package cl.tiocomegfas.app.marvelcomics.data.source.remote

import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.di.qualifier.JsonResponse
import cl.tiocomegfas.app.marvelcomics.di.qualifier.RawResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @JsonResponse private val jsonService: ApiServices,
    @RawResponse private val rawService: ApiServices
) {

    suspend fun getAllCharacters(): GetAllCharactersResponse {
        return jsonService.getAllCharacters() ?: throw IllegalArgumentException("The getAllCharacters response is null")
    }

    suspend fun getThumbnail(url: String): ByteArray {
        val response = rawService.getThumbnailCharacter(url)
        return response!!.bytes()
    }
}