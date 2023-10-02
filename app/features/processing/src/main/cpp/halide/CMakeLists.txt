# This file can be open as a project in CLion IDE

# Sets the minimum version of CMake.
cmake_minimum_required(VERSION 3.22.1)

# Declares and names the project.
project(generators)

# Set up C++ language settings
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

# We don't need to set CMAKE_PREFIX_PATH here since we already did it in `buildDebugHalideGeneratorsTask`
# and `buildReleaseHalideGeneratorsTask`
# set(CMAKE_PREFIX_PATH "/Users/user_name/Halide-16.0.0-arm-64-osx")

# We need Halide::Generator
find_package(Halide REQUIRED)

# Build generator
add_halide_generator(
        "invert.generator"
        SOURCES
        invert_generator.cpp
)
