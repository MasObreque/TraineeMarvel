package cl.tiocomegfas.app.marvelcomics.util.persistence

import androidx.datastore.core.Serializer
import cl.tiocomegfas.app.marvelcomics.data.source.local.MarvelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class RawSerializer(
    override val defaultValue: MarvelDatabase
): Serializer<MarvelDatabase> {

    override suspend fun readFrom(input: InputStream): MarvelDatabase {
        return try {
            Json.decodeFromString(
                deserializer = MarvelDatabase.serializer(),
                string = input.bufferedReader().readText()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: MarvelDatabase, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.bufferedWriter().apply {
                write(
                    Json.encodeToString(
                        serializer = MarvelDatabase.serializer(),
                        value = t
                    )
                )
                flush()
            }.close()
        }
    }
}