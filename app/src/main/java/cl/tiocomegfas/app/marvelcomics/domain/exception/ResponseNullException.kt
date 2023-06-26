package cl.tiocomegfas.app.marvelcomics.domain.exception

class ResponseNullException(private val error: String): Exception(error) {
}