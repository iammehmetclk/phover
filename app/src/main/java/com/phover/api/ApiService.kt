package com.phover.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("curiosity/photos?")
    suspend fun getCuriosity(
        @Query("sol") sol: Int = 1,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("camera") camera: String? = null
    ): ApiResponse

    @GET("opportunity/photos?")
    suspend fun getOpportunity(
        @Query("sol") sol: Int = 1,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("camera") camera: String? = null
    ): ApiResponse

    @GET("spirit/photos?")
    suspend fun getSpirit(
        @Query("sol") sol: Int = 1,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("camera") camera: String? = null
    ): ApiResponse

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"
        private const val API_KEY = "x1yY1rKcz4jVer1mBcRT6gUk6BZy8yK7JUcaUC9v"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}