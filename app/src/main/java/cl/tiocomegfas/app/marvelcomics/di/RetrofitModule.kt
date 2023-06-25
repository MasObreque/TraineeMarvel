package cl.tiocomegfas.app.marvelcomics.di

import cl.tiocomegfas.app.marvelcomics.data.source.remote.ApiServices
import cl.tiocomegfas.app.marvelcomics.util.network.AddKeyInterceptor
import cl.tiocomegfas.app.marvelcomics.util.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideAddApiKeyInterceptor(): AddKeyInterceptor {
        return AddKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideApiServices(interceptor: AddKeyInterceptor): ApiServices {
        return RetrofitClient(interceptor).initialize()
    }
}