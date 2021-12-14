package com.compose.template.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import com.compose.template.data.ApiService
import com.compose.template.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.CacheControl
import retrofit2.Invocation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cacheable

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        @ApplicationContext
        context: Context
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val httpCacheDirectory = File(context.cacheDir, "oflineCache")
        val cache = Cache(httpCacheDirectory, 5 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
//            .addNetworkInterceptor(provideCacheInterceptor())
//            .addInterceptor(provideOfflineCacheInterceptor(context))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val originalResponse: Response = chain.proceed(request)

                originalResponse
        }
    }

    private fun provideOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
var request= chain.request()
            if(!isNetConnected(context)){
                val maxStale = 60 * 60 * 24 * 30
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build();
            }
            chain.proceed(request)
        }
    }


}

class MainInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        request.tag(Invocation::class.java)?.let {
            if (!it.method().isAnnotationPresent(Cacheable::class.java)) {
                builder.cacheControl(CacheControl.Builder()
                    .noStore()
                    .build())
                return chain.proceed(builder.build())
            }
            try {
                builder.cacheControl(CacheControl.FORCE_NETWORK)
                return chain.proceed(builder.build())
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            builder.cacheControl(CacheControl.Builder()
                .maxStale(Int.MAX_VALUE, TimeUnit.DAYS)
                .build())
        }
        return chain.proceed(builder.build())
    }
}

fun isNetConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(TRANSPORT_WIFI) -> true
        capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
        else -> false
    }
}