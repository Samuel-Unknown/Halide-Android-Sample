# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake.
cmake_minimum_required(VERSION 3.22.1)

# Declares and names the project.
project(processing)

# Set up C++ language settings
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

if (NOT DEFINED HalideHelpers_DIR)
    message(FATAL_ERROR "CMakeVariable HalideHelpers_DIR is not set")
endif()

if (NOT DEFINED GEN_BINARY_DIR)
    message(FATAL_ERROR "CMakeVariable GEN_BINARY_DIR is not set")
endif()

set(INVERT_GEN_EXE "invert.generator")

# Find HalideHelpers -- this is just the Runtime headers and CMake functions, but no libraries
find_package(HalideHelpers REQUIRED)

# Declare the generator executable as imported, which is built in external project
add_executable(${INVERT_GEN_EXE} IMPORTED)

# Specify the location of exe file to import
set_property(
        TARGET ${INVERT_GEN_EXE}
        PROPERTY IMPORTED_LOCATION ${GEN_BINARY_DIR}/${INVERT_GEN_EXE}
)

# Generate filter function
add_halide_library(invert FROM ${INVERT_GEN_EXE} GENERATOR invert)

add_library(
        # Sets the name of the library.
        processing

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp
)

find_library(
        # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries(
        processing PRIVATE
        Halide::ImageIO
        invert
        ${log-lib}
)