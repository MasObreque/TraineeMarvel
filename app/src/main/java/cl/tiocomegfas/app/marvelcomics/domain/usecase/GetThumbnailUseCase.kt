package cl.tiocomegfas.app.marvelcomics.domain.usecase

import android.graphics.BitmapFactory
import cl.tiocomegfas.app.marvelcomics.data.entity.mapper.GetThumbnailCharacterMapper
import cl.tiocomegfas.app.marvelcomics.data.entity.request.GetThumbnailCharacterRequest
import cl.tiocomegfas.app.marvelcomics.data.repository.MarvelRepository
import cl.tiocomegfas.app.marvelcomics.di.qualifier.IoDispatcher
import cl.tiocomegfas.app.marvelcomics.domain.exception.ResponseEmptyException
import cl.tiocomegfas.app.marvelcomics.util.base.BaseUseCase
import cl.tiocomegfas.app.marvelcomics.util.base.ResourceState
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetThumbnailUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: MarvelRepository
): BaseUseCase<GetThumbnailCharacterRequest, ByteArray?, ResourceState<GetThumbnailCharacterMapper>>(dispatcher) {
    override suspend fun onRequestHandler(request: GetThumbnailCharacterRequest): ByteArray? {
        return repository.getThumbnail(request)
    }

    override suspend fun onResponseHandler(response: ByteArray?): ResourceState<GetThumbnailCharacterMapper> {
        response ?: throw ResponseEmptyException()
        val bitmap = BitmapFactory.decodeByteArray(response, 0, response.size)
        return ResourceState.Success(
            GetThumbnailCharacterMapper(bitmap)
        )
    }

    override suspend fun onErrorHandler(cause: Throwable): ResourceState<GetThumbnailCharacterMapper> {
        return ResourceState.Error(cause)
    }


}