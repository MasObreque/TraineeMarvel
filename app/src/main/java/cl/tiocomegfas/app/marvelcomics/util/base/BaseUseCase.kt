package cl.tiocomegfas.app.marvelcomics.util.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in Request, Response, out Mapped>(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(request: Request): Flow<Mapped> {
        return flow {
            emit(onResponseHandler(onRequestHandler(request)))
        }.flowOn(dispatcher)
            .catch { cause ->
                emit(onErrorHandler(cause))
            }
    }
    protected abstract suspend fun onRequestHandler(request: Request): Response
    protected abstract suspend fun onResponseHandler(response: Response): Mapped
    protected abstract suspend fun onErrorHandler(cause: Throwable): Mapped
}