package cl.tiocomegfas.app.marvelcomics.util.network

import okhttp3.Interceptor
import okhttp3.Response

class AddKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("ts", TIMESTAMP)
            .addQueryParameter("apikey", PUBLIC_KEY)
            .addQueryParameter("hash", HASH_KEY)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}