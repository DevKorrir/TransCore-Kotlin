package com.example.transcore.data.models

import com.google.gson.annotations.SerializedName

data class TranslateResponse(
    @SerializedName("data") val data: TranslationsData
)

data class TranslationsData(
    @SerializedName("translations") val translations: List<Translation>
)

data class Translation(
    @SerializedName("translatedText") val translatedText: String
)