#!/bin/bash

GODOT_PLUGINS="adjust"

# Compile Plugin
for lib in $GODOT_PLUGINS; do
    ./scripts/generate_static_library.sh $lib release $1
    ./scripts/generate_static_library.sh $lib release_debug $1
    mv ./bin/${lib}.release_debug.a ./bin/${lib}.debug.a
done

