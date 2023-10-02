#!/bin/bash

# -e (errexit): Abort the script at the first error, when a command exits with non-zero status (except in until or while loops, if-tests, and list constructs)
# -o pipefail: Causes a pipeline to return the exit status of the last command in the pipe that returned a non-zero return value.
set -eo pipefail

echo "Build Debug Halide Generators"

# check that CMAKE_PREFIX_PATH environment variable is exists
if [[ -z "${CMAKE_PREFIX_PATH}" ]]; then
  echo "CMAKE_PREFIX_PATH is undefined"
  exit 1;
fi

# execute like we running from bash script located folder
cd "$(dirname "${BASH_SOURCE[0]}")"

# set build dir
BUILD_DIR="$PWD/../../../../build/cmake-build-debug"

# delete cmake-build-debug folder
rm -rf "$BUILD_DIR"

cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_MAKE_PROGRAM=ninja -G Ninja -S "$PWD" -B "$BUILD_DIR"
cmake --build "$BUILD_DIR" --target invert.generator -j 8
