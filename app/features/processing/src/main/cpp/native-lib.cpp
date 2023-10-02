#include <jni.h>
#include <string>
#include <HalideBuffer.h>
#include "invert.h"

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_samuelunknown_app_features_processing_InvertBitmapUseCaseImpl_invertFromJNI(
        JNIEnv *env,
        jobject thiz,
        jbyteArray input,
        jint input_width,
        jint input_height
) {
    // Step 1: Convert inputBitmap (Java bitmap) to inputBuffer (Halide image format)
    jsize inputArrayLength = env->GetArrayLength(input);
    jbyte *inputByteArrayPtr = env->GetByteArrayElements(input, nullptr);

    // Assuming the input bitmap is in RGB_888 format (3 bytes per pixel)
    jint channels = 3; // (x, y, c)

    Halide::Runtime::Buffer<uint8_t> inputBuffer((uint8_t *) inputByteArrayPtr, input_width, input_height, channels);
    Halide::Runtime::Buffer<uint8_t> outputBuffer(input_width, input_height, channels);

    // Step 2: Perform image processing using Halide code
    invert(inputBuffer, outputBuffer);

    // Step 3: Convert the processed image back to a Java bitmap
    // Assuming you have the processed Halide image in outputBuffer
    // Convert outputBuffer (Halide image) to outputBitmap (byte array)
    jbyteArray outputByteArray = env->NewByteArray(inputArrayLength);

    env->SetByteArrayRegion(outputByteArray, 0, inputArrayLength, (jbyte *) outputBuffer.data());

    // Step 4: Release resources (since we called GetByteArrayElements earlier)
    env->ReleaseByteArrayElements(input, inputByteArrayPtr, JNI_ABORT); // No need to copy back changes

    return outputByteArray;
}