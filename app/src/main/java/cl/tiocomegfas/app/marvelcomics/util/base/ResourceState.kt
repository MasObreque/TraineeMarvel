package cl.tiocomegfas.app.marvelcomics.util.base

sealed class ResourceState<T> {
    data class Success<T>(val data: T): ResourceState<T>()
    data class Error<T>(val error: Throwable): ResourceState<T>()
}