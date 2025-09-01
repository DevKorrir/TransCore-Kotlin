import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)

    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.transcore"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.transcore"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "TRANSLATE_API_KEY", getApiKey("TRANSLATE_API_KEY"))
        buildConfigField("String", "TRANSLATE_BASE_URL", getApiKey("TRANSLATE_BASE_URL"))
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

fun getApiKey(propertyKey: String): String {
    val properties = Properties()
    val file = project.rootProject.file("keys.properties")
    properties.load(FileInputStream(file))
    return "\"${properties.getProperty(propertyKey)}\""
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // For Kotlin Coroutines and Flow support
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    // Gson converter (or your preferred converter)
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-compiler:2.57.1")

    // Hilt Navigation Compose (recommended for Compose + Hilt)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //logs
    implementation("com.jakewharton.timber:timber:5.0.1")

    //extend icons
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")


}