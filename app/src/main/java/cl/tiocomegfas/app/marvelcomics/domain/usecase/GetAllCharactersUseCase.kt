package cl.tiocomegfas.app.marvelcomics.domain.usecase

import cl.tiocomegfas.app.marvelcomics.data.entity.mapper.GetAllCharactersMapper
import cl.tiocomegfas.app.marvelcomics.data.entity.response.GetAllCharactersResponse
import cl.tiocomegfas.app.marvelcomics.data.repository.MarvelRepository
import cl.tiocomegfas.app.marvelcomics.di.qualifier.IoDispatcher
import cl.tiocomegfas.app.marvelcomics.util.base.BaseUseCase
import cl.tiocomegfas.app.marvelcomics.util.base.ResourceState
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: MarvelRepository,
): BaseUseCase<Any?, GetAllCharactersResponse, ResourceState<GetAllCharactersMapper>>(dispatcher) {
    override suspend fun onRequestHandler(request: Any?): GetAllCharactersResponse {
        return repository.getAllCharacters()
    }

    override suspend fun onResponseHandler(response: GetAllCharactersResponse): ResourceState<GetAllCharactersMapper> {
        val items = mutableListOf<GetAllCharactersMapper.Character>()
        response.content.results.forEach {
            items.add(
                GetAllCharactersMapper.Character(
                    id = it.id,
                    name = it.name,
                    thumbnailURL = it.thumbnail.path,
                )
            )
        }
        return ResourceState.Success(
            GetAllCharactersMapper(
                items
            )
        )
    }

    override suspend fun onErrorHandler(cause: Throwable): ResourceState<GetAllCharactersMapper> {
        return ResourceState.Error(cause)
    }
}