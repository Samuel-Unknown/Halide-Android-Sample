# Overview

The goal of this repository is to demonstrate how to use `Halide` generators in an Android application.

`Halide` is a programming language designed to make it easier to write and maintain high-performance image processing code.

You can learn more about `Halide` from these sources:
 - https://halide-lang.org/index.html#gettingstarted
 - https://blog.minhazav.dev/write-fast-and-maintainable-code-with-halide

In this sample app, I use [`invert_generator.cpp`](app/features/processing/src/main/cpp/halide/invert_generator.cpp) 
generator to invert the colors of an image. When the button is pressed, the display mode changes.

<p align="center">
<img src="/Sample.gif?raw=true" width="300px" align="middle">
</p>

# Limitations
This works on macOS, but may require modifications on Windows and Linux. Pull requests are welcome!

On macOS you should have the Clang compiler (you can check it in the terminal by executing `clang --version`).

### Additional info
```
Android min SDK: 24
Android compile and target SDK: 34
NDK version: 25.2.9519653
CMake version: 3.22.1
```

# How to build
To make the project build, you need to:

1) Download `Halide` from https://github.com/halide/Halide/releases (I used Halide-16.0.0-arm-64-osx) and extract the archive
2) Add the following line to `local.properties`: `halide.dir=path_to_the_extracted_halide_archive`
3) Build the project as you typically do.

### Build Halide generators tasks
These tasks are performed automatically before building the `app:features:processing` module.

```
./gradlew buildDebugHalideGeneratorsTask
```
```
./gradlew buildReleaseHalideGeneratorsTask
```

## License
```
Copyright 2023 Samuel Unknown

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
