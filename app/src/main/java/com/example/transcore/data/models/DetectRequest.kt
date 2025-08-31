package com.example.transcore.data.models

data class DetectRequest(val q: List<String>)
data class DetectResponse(val data: DetectData)
data class DetectData(val detections: List<List<Detection>>)
data class Detection(
    val language: String,
    val confidence: Float,
    val isReliable: Boolean
)