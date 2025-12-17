package com.rare.launcher.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    private const val OAUTH_BASE_URL = "https://account-public-service-prod03.ol.epicgames.com/"
    private const val LIBRARY_BASE_URL = "https://library-service.live.use1a.on.epicgames.com/"
    
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    private val oauthRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(OAUTH_BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val libraryRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(LIBRARY_BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val oauthApi: EpicGamesApi = oauthRetrofit.create(EpicGamesApi::class.java)
    val libraryApi: EpicGamesApi = libraryRetrofit.create(EpicGamesApi::class.java)
}
