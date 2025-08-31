package com.example.transcore.data.api

import com.example.transcore.BuildConfig
import com.example.transcore.data.models.TranslateRequest
import com.example.transcore.data.models.TranslateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslateApiService {
    @POST("language/translate/v2")
    suspend fun translate(
        @Query("key") apiKey: String = BuildConfig.TRANSLATE_API_KEY,
        @Body request: TranslateRequest
    ): TranslateResponse

//    @GET("language/translate/v2/languages")
//    suspend fun getLanguages(
//        @Query("key") apiKey: String = BuildConfig.TRANSLATE_API_KEY,
//        @Query("target") target: String = "en"
//    )
}

