package cl.tiocomegfas.app.marvelcomics.di

import android.content.Context
import cl.tiocomegfas.app.marvelcomics.data.source.local.MarvelDatabase
import cl.tiocomegfas.app.marvelcomics.util.persistence.CryptoSerializer
import cl.tiocomegfas.app.marvelcomics.util.persistence.PersistenceConfiguration
import cl.tiocomegfas.app.marvelcomics.util.persistence.RawSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun providePersistenceConfiguration(@ApplicationContext context: Context): PersistenceConfiguration<MarvelDatabase> {
        return object : PersistenceConfiguration<MarvelDatabase>(context, "marvel_db", RawSerializer(MarvelDatabase())) {
            override suspend fun getPreferences(): MarvelDatabase {
                return runBlocking {
                    context.dataStore.data.map { preference ->
                        preference
                    }.first()
                }
            }

            override suspend fun setPreferences(preferences: MarvelDatabase) {
                runBlocking {
                    context.dataStore.updateData {
                        preferences
                    }
                }
            }
        }
    }
}