package com.example.transcore.data.models

import com.google.gson.annotations.SerializedName

data class LanguagesResponse(
    @SerializedName("data") val data: LanguagesData
)

data class LanguagesData(
    @SerializedName("languages") val languages: List<Language>
)

data class Language(
    @SerializedName("language") val language: String
)
