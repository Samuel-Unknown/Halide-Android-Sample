package com.samuelunknown.halide_android_sample.tasks

import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream

/**
 * Task for executing shell script
 */
abstract class ShellScriptTask : Exec() {
    @get:Input
    var scriptPath: String = ""

    @get:Input
    var halidDir: String = ""

    @TaskAction
    override fun exec() {
        // Create ByteArrayOutputStreams to capture standard output and standard error
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()

        // Configure the Exec task to run the shell script
        commandLine("sh", scriptPath)

        // Add environment variable CMAKE_PREFIX_PATH
        environment("CMAKE_PREFIX_PATH", halidDir)

        // Execute the shell script
        super.exec()

        // Print the output of the shell script
        val exitValue = executionResult.get().exitValue
        if (exitValue == 0) {
            println("$outputStream")
        } else {
            println("$errorStream")
            throw RuntimeException("Shell script failed with exit code $exitValue")
        }
    }
}