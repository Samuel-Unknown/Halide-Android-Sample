// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.sample.gradle.plugin)
    alias(libs.plugins.sample.application) apply false
    alias(libs.plugins.sample.application.compose) apply false
    alias(libs.plugins.sample.library) apply false
    alias(libs.plugins.sample.library.compose) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}