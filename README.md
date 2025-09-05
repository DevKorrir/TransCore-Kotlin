# TransCore — Offline-aware Translator (Android)

**Short description:**
TransCore is a clean, simple Android translator app built with Kotlin, Jetpack Compose, Hilt (DI), MVVM, Retrofit and Room. It performs translations via an external translation API (configurable) and stores a local translation history (Room) that the user can view. This README explains project structure, setup (including keys.properties), how to switch APIs, how to build & run, and troubleshooting tips.

## What this repository contains

Android app (Kotlin + Compose) implementing:

- Language selection (bottom sheet with search)
- Auto-translate on input (debounced)
- Manual Save button to save the last translated result to local history (Room)
- History screen that lists previous translations
- Retrofit API integration (configurable between LibreTranslate and Google Translate)
- Room DB + DAO + Hilt DI for history
- MVVM: TranslatorViewModel (translation logic) and HistoryViewModel (history)
- Hilt modules for providing Room, DAO, and repository bindings

## Tech stack

- Kotlin
- Android Studio
- Jetpack Compose (UI)
- Hilt (Dependency Injection)
- Retrofit + Gson (HTTP & JSON)
- Coroutines + StateFlow / SharedFlow (async and state)
- Room (local persistence)
- Timber / Android Log (logging)

## Key features

- Translate between 100+ languages (configurable backend)
- Debounced real-time translation on input
- Language selection using a searchable bottom sheet
- Swap source / target languages
- Save translations to local history (Room)
- History screen with timestamp, source/target languages
- Clean architecture: ui → viewmodel → usecase/repository → data

## Project structure (folders)

A recommended high-level organization used in this project:

```
app/src/main/java/com/example/transcore/
├─ data/
│  ├─ api/                # Retrofit interfaces & DTOs
│  ├─ local/              # Room: entity, dao, database
│  ├─ repository/         # repository implementations (API + Room)
├─ domain/
│  ├─ model/              # domain models
│  ├─ repo/               # repository interfaces (contracts)
│  ├─ usecase/            # optional use-cases
├─ presentation/
│  ├─ ui/                 # Compose screens & components
│  ├─ viewModel/          # ViewModels (TranslatorViewModel, HistoryViewModel)
├─ di/                    # Hilt modules & bindings
```

## Getting started — clone & prerequisites

Clone the repository:

```bash
git clone https://github.com/DevKorrir/TransCore-Kotlin.git
cd TransCore
```

**Prerequisites:**

- Android Studio + Android SDK (API 21+)
- Gradle (wrapper included)
- A device/emulator with Internet access (for API translation)

Open the project in Android Studio and let it sync.

## Configure API keys (keys.properties)

This project expects a `keys.properties` file at the project root (same level as settings.gradle / gradle.properties). Do not commit this file. Add it to `.gitignore`.

Example `keys.properties`:

```properties
# For Google Translate v2 (optional)
TRANSLATE_API_KEY=AIzaSy...your_key_here...
TRANSLATE_BASE_URL=https://translation.googleapis.com/

# If you prefer LibreTranslate (free), change base URL:
# TRANSLATE_BASE_URL=https://libretranslate.de/
# If LibreTranslate is used, TRANSLATE_API_KEY can be blank or omitted.
```

Add `keys.properties` to `.gitignore`:

```gitignore
# local secret keys
keys.properties
```

## Gradle configuration (example)

Add this snippet to your `app/build.gradle.kts` (or Groovy equivalent) to load the values and expose them as BuildConfig fields:

```kotlin
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

android {
  defaultConfig {
    val properties = gradleLocalProperties(rootDir)
    val apiKey = properties.getProperty("TRANSLATE_API_KEY") ?: ""
    val baseUrl = properties.getProperty("TRANSLATE_BASE_URL") ?: "https://libretranslate.de/"

    buildConfigField("String", "TRANSLATE_API_KEY", "\"$apiKey\"")
    buildConfigField("String", "TRANSLATE_BASE_URL", "\"$baseUrl\"")
  }
}
```

Usage in Kotlin:

```kotlin
val apiKey = BuildConfig.TRANSLATE_API_KEY
val baseUrl = BuildConfig.TRANSLATE_BASE_URL
```

## How to run the app

1. Make sure `keys.properties` is present and contains a valid base URL (and key if using Google v2).
2. Sync Gradle in Android Studio.
3. Run on a device or emulator:
   - Select an emulator / device and click Run.
   - Or use the Gradle wrapper:

```bash
./gradlew installDebug
adb shell am start -n com.example.transcore/.MainActivity
```

## Switching translation backends

The app can use either:

### LibreTranslate (recommended / free usage)

- **Base URL:** `https://libretranslate.de/`
- **Endpoint:** POST `/translate` with JSON `{ q, source, target, format }`
- **No API key required** for public instance (respect rate limits). You can host your own LibreTranslate server if desired.

### Google Translate v2 (easy API key approach)

- **Base URL:** `https://translation.googleapis.com/`
- **Endpoint:** POST `language/translate/v2` (form-encoded) or GET query style.
- **Requires API key** (enable Translate API & create API Key in Google Cloud console).
- **Note:** v2 is legacy; v3 is more powerful but requires OAuth/service account.

### How to change

1. Set appropriate `TRANSLATE_BASE_URL` in `keys.properties`.

   **For LibreTranslate:**
   ```properties
   TRANSLATE_BASE_URL=https://libretranslate.de/
   ```

   **For Google v2:**
   ```properties
   TRANSLATE_BASE_URL=https://translation.googleapis.com/
   TRANSLATE_API_KEY=YOUR_GOOGLE_API_KEY
   ```

2. Make sure the `TranslateApiService` matches the backend endpoints:
   - **LibreTranslate:** `@POST("translate")` with `@Body TranslateRequest` (JSON).
   - **Google v2:** `@FormUrlEncoded @POST("language/translate/v2")` with `@Field("q")`, `@Field("target")`, optional `@Field("source")`, and `@Query("key")`.

3. Rebuild and run.

## Google Translate (v2) — quick guide to API key (if you choose v2)

1. Create project at [console.cloud.google.com](https://console.cloud.google.com)
2. Enable **Cloud Translation API** (v2).
3. Go to **APIs & Services** → **Credentials** → **Create credentials** → **API key**.
4. **(Recommended)** Restrict the key:
   - Restrict API key to Cloud Translation API only.
   - Optionally restrict key usage by Android app package name + SHA-1 fingerprint.
5. Place key in `keys.properties` as `TRANSLATE_API_KEY`.

**Security note:** An API key embedded in the APK can be extracted if the app is distributed. For production, prefer a backend proxy or strict API key restrictions. For a demo/hackathon, restricting the key and keeping it out of VCS is acceptable.

## Where history is saved & how it works

- **History table:** Room entity `translation_history` (fields: id, sourceText, translatedText, sourceLang, targetLang, timestamp).
- When the user presses **Save** (or if you configure auto-save), the app inserts a row into Room via DAO.
- **HistoryScreen** observes the DAO via a Flow and displays items in a LazyColumn.
- **Database file name** (example): `translation_db`. You can view it in Android Studio Device File Explorer (`/data/data/com.example.transcore/databases/translation_db`) or via adb commands.

## Debugging & common issues

### 1. Translation not happening

- Check Logcat for tags used in the app: `TranslateRepo` and `TranslateVM` (we use Timber or Log.d).
- Ensure `INTERNET` permission present in AndroidManifest.xml:
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  ```
- Verify `BuildConfig.TRANSLATE_BASE_URL` is correct.
- If using Google v2, confirm `TRANSLATE_API_KEY` is valid and not over quota.

### 2. HTTP 400 (Bad Request)

- **Common cause:** sending source as an empty string. For auto-detect: omit source OR call the detect endpoint first.
- If you're using v2 detect functionality, call POST `language/translate/v2/detect` (form-encoded q) properly and use detected language code in subsequent translations.


## Example usage (UX)

1. Launch app → type text → app auto-translates (debounced).
2. Choose languages via bottom-sheet (searchable).
3. Press **Save** (Save icon / label) to add current translation to local history.
4. Open **History** via header icon → view saved translations (most recent first) → option to clear.

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests to improve the app's functionality and user experience.

## License & acknowledgements

This project is open source. Special thanks to the LibreTranslate project for providing a free translation API, and to Google for their translation services.
