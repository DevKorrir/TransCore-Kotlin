package com.example.transcore.data.api

import com.example.transcore.data.models.Language
import com.example.transcore.data.models.TranslateRequest
import com.example.transcore.data.models.TranslateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TranslateApi {
    @POST("translate")
    suspend fun translate(
        @Body request: TranslateRequest
    ): TranslateResponse

    @GET("languages")
    suspend fun getLanguages(): List<Language>
}