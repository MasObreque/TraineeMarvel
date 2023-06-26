package cl.tiocomegfas.app.marvelcomics.util.persistence

import androidx.datastore.core.Serializer
import cl.tiocomegfas.app.marvelcomics.data.source.local.MarvelDatabase
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class CryptoSerializer(
    override val defaultValue: MarvelDatabase
): Serializer<MarvelDatabase> {
    private val crypto: CryptoManager = CryptoManager()

    override suspend fun readFrom(input: InputStream): MarvelDatabase {
        val decryptedBytes = crypto.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = MarvelDatabase.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: MarvelDatabase, output: OutputStream) {
        crypto.encrypt(
            bytes = Json.encodeToString(
                serializer = MarvelDatabase.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }

}