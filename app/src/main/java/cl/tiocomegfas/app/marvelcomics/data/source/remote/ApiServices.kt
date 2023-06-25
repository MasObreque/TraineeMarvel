package cl.tiocomegfas.app.marvelcomics.data.source.remote

import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.util.network.URL_ALL_CHARACTERS
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiServices {

    @GET(URL_ALL_CHARACTERS)
    suspend fun getAllCharacters(): GetAllCharactersResponse?

    suspend fun getThumbnailCharacter(
        @QueryMap params: Map<String, String>
    )
}