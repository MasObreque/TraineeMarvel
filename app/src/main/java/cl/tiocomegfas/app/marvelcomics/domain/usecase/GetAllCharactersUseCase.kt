package cl.tiocomegfas.app.marvelcomics.domain.usecase

import android.util.Log
import cl.tiocomegfas.app.marvelcomics.data.entity.local.GetAllCharactersLocal
import cl.tiocomegfas.app.marvelcomics.data.entity.mapper.GetAllCharactersMapper
import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.data.repository.MarvelRepository
import cl.tiocomegfas.app.marvelcomics.di.qualifier.IoDispatcher
import cl.tiocomegfas.app.marvelcomics.domain.exception.ResponseEmptyException
import cl.tiocomegfas.app.marvelcomics.domain.exception.ResponseNullException
import cl.tiocomegfas.app.marvelcomics.util.base.BaseUseCase
import cl.tiocomegfas.app.marvelcomics.util.base.ResourceState
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: MarvelRepository,
): BaseUseCase<Any?, GetAllCharactersLocal, ResourceState<GetAllCharactersMapper>>(dispatcher) {
    override suspend fun onRequestHandler(request: Any?): GetAllCharactersLocal {
        return repository.getAllCharacters()
    }

    override suspend fun onResponseHandler(response: GetAllCharactersLocal): ResourceState<GetAllCharactersMapper> {
        response.characters ?: throw ResponseNullException("Characters is null")
        val items = mutableListOf<GetAllCharactersMapper.Character>()
        response.characters?.forEach {
            items.add(
                GetAllCharactersMapper.Character(
                    id = it?.id ?: throw ResponseNullException("Character id is null"),
                    name = it.name ?: throw ResponseNullException("Character name is null"),
                    comics = it.comics,
                    series = it.series
                )
            )
        }
        if(items.isEmpty()) throw ResponseEmptyException()
        return ResourceState.Success(
            GetAllCharactersMapper(
                items
            )
        )
    }

    override suspend fun onErrorHandler(cause: Throwable): ResourceState<GetAllCharactersMapper> {
        Log.e("TAG", cause.message ?: "Error")
        return ResourceState.Error(cause)
    }
}