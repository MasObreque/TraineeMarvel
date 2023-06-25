package cl.tiocomegfas.app.marvelcomics.data.source.remote

import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) {

    suspend fun getAllCharacters(): GetAllCharactersResponse {
        return apiServices.getAllCharacters() ?: throw IllegalArgumentException("The getAllCharacters response is null")
    }
}

//13500b99298ef64d8bc289f13c2fd0f3e5afddc966b4bf9c5cb32f2ffc58b5dd577dc437a
//https://gateway.marvel.com/v1/public/characters?ts=1&apikey=6b4bf9c5cb32f2ffc58b5dd577dc437a&hash=