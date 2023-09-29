plugins {
    alias(libs.plugins.sample.application.compose)
}

android {
    namespace = "com.samuelunknown.halide_android_sample"

    defaultConfig {
        applicationId = "com.samuelunknown.halide_android_sample"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)

    debugImplementation(libs.androidx.compose.ui.tooling)
}