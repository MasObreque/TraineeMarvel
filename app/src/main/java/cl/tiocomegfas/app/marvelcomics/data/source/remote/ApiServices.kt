package cl.tiocomegfas.app.marvelcomics.data.source.remote

import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.util.network.URL_ALL_CHARACTERS
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiServices {

    @GET(URL_ALL_CHARACTERS)
    suspend fun getAllCharacters(): GetAllCharactersResponse?

    @GET
    suspend fun getThumbnailCharacter(
        @Url url: String
    ): ResponseBody?
}