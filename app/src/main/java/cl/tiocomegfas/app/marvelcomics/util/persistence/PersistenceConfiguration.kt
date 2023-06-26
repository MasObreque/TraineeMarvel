package cl.tiocomegfas.app.marvelcomics.util.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore

abstract class PersistenceConfiguration<T>(
    private val context: Context,
    private val fileName: String,
    private val serializer: Serializer<T>
) {
    val Context.dataStore: DataStore<T> by dataStore(
        fileName = "$fileName.pb",
        serializer = serializer
    )

    abstract suspend fun getPreferences(): T

    abstract suspend fun setPreferences(preferences: T)
}