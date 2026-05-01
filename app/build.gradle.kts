plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.miniproject"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.miniproject"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
android {
    defaultConfig {
        minSdk = 21 // Необхідно для WorkManager
    }
}

dependencies {
    val workVersion = "2.9.0"

    // WorkManager Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:$workVersion")

    // Lifecycle (для ViewModel та LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
}
dependencies {
    // Основна бібліотека WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Для LiveData та ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // Для інструментальних тестів (androidTest)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")

    // ДЛЯ WORK MANAGER ТЕСТІВ (обов'язково!)
    androidTestImplementation("androidx.work:work-testing:2.9.0")

    // Для runBlocking у тестах
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}