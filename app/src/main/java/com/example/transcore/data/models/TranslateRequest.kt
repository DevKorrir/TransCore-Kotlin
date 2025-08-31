package com.example.transcore.data.models

import com.example.transcore.BuildConfig
import com.google.gson.annotations.SerializedName


//data class TranslateRequest(
//    val q: String,
//    val source: String,
//    val target: String,
//    val format: String = "text",
//    val api_key: String = BuildConfig.TRANSLATE_API_KEY
//)

data class TranslateRequest(
    @SerializedName("q") val q: String,
    @SerializedName("source") val source: String,
    @SerializedName("target") val target: String,
    @SerializedName("format") val format: String = "text"
)