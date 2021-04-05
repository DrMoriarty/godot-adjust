#!/bin/bash

GODOT_PLUGINS="adjust"

# Compile Plugin
for lib in $GODOT_PLUGINS; do
    ./scripts/generate_xcframework.sh $lib release $1
    ./scripts/generate_xcframework.sh $lib release_debug $1
    rm -rf ./bin/${lib}.debug.xcframework
    mv ./bin/${lib}.release_debug.xcframework ./bin/${lib}.debug.xcframework
done

