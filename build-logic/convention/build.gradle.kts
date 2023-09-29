plugins {
    `kotlin-dsl`
}

group = "com.samuelunknown.halide_android_sample.build-logic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("sample.gradle.plugin") {
            val plugin = libs.plugins.sample.gradle.plugin.get()
            id = plugin.pluginId
            version = plugin.version
            implementationClass = "SampleGradlePlugin"
        }

        register("sample.android.application") {
            val plugin = libs.plugins.sample.application.asProvider().get()
            id = plugin.pluginId
            version = plugin.version
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("sample.android.application.compose") {
            val plugin = libs.plugins.sample.application.compose.get()
            id = plugin.pluginId
            version = plugin.version
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("sample.android.library") {
            val plugin = libs.plugins.sample.library.asProvider().get()
            id = plugin.pluginId
            version = plugin.version
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("sample.android.library.compose") {
            val plugin = libs.plugins.sample.library.compose.get()
            id = plugin.pluginId
            version = plugin.version
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}