plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp) // or latest
}

android {
    namespace = "com.marky.strivefit"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.marky.strivefit"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
    }
}

dependencies {

    //firebase
    //noinspection UseTomlInstead
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics")
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Hilt
    implementation(libs.hilt.android.v2562)
    implementation(libs.androidx.room.runtime)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // AndroidX & Kotlin
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // Security
    implementation(libs.androidx.security.crypto)

    // Room
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.room.ktx)

    // Font
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.core.splashscreen)

    // Icon
    implementation(libs.icons.lucide)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Window Imports
    implementation(libs.androidx.material3.window.size.class1)
    implementation(libs.androidx.window)

    //GIF Support
    implementation(libs.coil.compose)
    ksp(libs.androidx.room.compiler)

    //Health Connect
    //noinspection UseTomlInstead
    implementation("androidx.health.connect:connect-client:1.1.0-rc01")

    //Gson - for handling json documents from firebase
    //noinspection UseTomlInstead
    implementation("com.google.code.gson:gson:2.13.1")

    //Image Coil - auto caching from link so no need to manage path manually via room
    //noinspection UseTomlInstead
    implementation("io.coil-kt:coil-compose:2.5.0")
    // For SVG support
    implementation("io.coil-kt:coil-svg:2.5.0")

    implementation("io.coil-kt:coil-gif:2.5.0")
    implementation("io.coil-kt:coil-webp:2.5.0")
}
