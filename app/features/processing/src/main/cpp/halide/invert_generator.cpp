#include "Halide.h"

namespace {
    using namespace Halide;

    class InvertGenerator : public Halide::Generator<InvertGenerator> {
    public:
        // We declare a three-dimensional Inputs to the Halide pipeline as public
        // member variables. They'll appear in the signature of our generated
        // function in the same order as we declare them.
        // The first two dimensions will be x, and y, and the third dimension will be
        // the color channel.
        Input <Buffer<uint8_t, 3>> input{"input"};
        Output <Buffer<uint8_t, 3>> output{"output"};

        // Typically you declare your Vars at this scope as well, so that
        // they can be used in any helper methods you add later.
        Var x{"x"}, y{"y"}, c{"c"}; // "c" is a chanel (red, green, blue)

        void generate() {
            Func invert("invert");
            invert(x, y, c) = 255 - input(x, y, c);

            output(x, y, c) = invert(x, y, c);

            output.vectorize(x, 16).parallel(y);
        }
    };

}  // namespace

// We compile this file along with tools/GenGen.cpp. That file defines
// an "int main(...)" that provides the command-line interface to use
// your generator class. We need to tell that code about our
// generator. We do this like so:
HALIDE_REGISTER_GENERATOR(InvertGenerator, invert)
