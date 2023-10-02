import java.util.Properties
import java.io.FileReader
import com.samuelunknown.halide_android_sample.NDK_VERSION
import com.samuelunknown.halide_android_sample.CMAKE_VERSION
import com.samuelunknown.halide_android_sample.tasks.ShellScriptTask

plugins {
    alias(libs.plugins.sample.library)
}

val buildReleaseHalideGeneratorsTask = tasks.register<ShellScriptTask>("buildDebugHalideGeneratorsTask") {
    scriptPath = "$projectDir/src/main/cpp/halide/build-debug.sh"
    halidDir = readHalideDirFromLocalProperties()
}

val buildDebugHalideGeneratorsTask = tasks.register<ShellScriptTask>("buildReleaseHalideGeneratorsTask") {
    scriptPath = "$projectDir/src/main/cpp/halide/build-release.sh"
    halidDir = readHalideDirFromLocalProperties()
}

fun readHalideDirFromLocalProperties(): String {
    val localPropertiesFile = rootProject.file("local.properties")
    val properties = Properties()
    FileReader(localPropertiesFile).use { reader -> properties.load(reader) }
    val halideDir = properties.getProperty("halide.dir", "")
    if (halideDir.isEmpty()) {
        throw RuntimeException("'halide.dir' must not be null! Add 'halide.dir=...' to 'local.properties' file")
    }

    return halideDir
}

android {
    namespace = "com.samuelunknown.app.features.processing"

    defaultConfig {
        ndk {
            version = NDK_VERSION // Your downloaded NDK version
            abiFilters.addAll(
                listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            )
        }

        val halideDir = readHalideDirFromLocalProperties()

        externalNativeBuild {
            cmake {
                arguments += listOf(
                    "-DHalideHelpers_DIR=${halideDir}/lib/cmake/HalideHelpers"
                )
            }
        }
    }

    buildTypes {
        debug {
            externalNativeBuild {
                cmake {
                    arguments += listOf(
                        "-DGEN_BINARY_DIR=${projectDir}/build/cmake-build-debug"
                    )
                }
            }

            project.tasks.getByName("preBuild").dependsOn(buildDebugHalideGeneratorsTask)
        }

        release {
            externalNativeBuild {
                cmake {
                    arguments += listOf(
                        "-DGEN_BINARY_DIR=${projectDir}/build/cmake-build-release"
                    )
                }
            }

            project.tasks.getByName("preBuild").dependsOn(buildReleaseHalideGeneratorsTask)
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = CMAKE_VERSION
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.core)
}