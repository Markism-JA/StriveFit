// settings.gradle.kts (or build.gradle.kts in root project)
plugins {
    // These plugins should only be applied in the root project
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}