#!/bin/bash

# Version increment script
# This script increments the version number in version.txt

VERSION_FILE="version.txt"

# Function to increment version
increment_version() {
    local version=$1
    local type=${2:-patch}  # default to patch increment
    
    # Parse version components
    IFS='.' read -r major minor patch <<< "$version"
    
    case $type in
        major)
            major=$((major + 1))
            minor=0
            patch=0
            ;;
        minor)
            minor=$((minor + 1))
            patch=0
            ;;
        patch|*)
            patch=$((patch + 1))
            ;;
    esac
    
    echo "$major.$minor.$patch"
}

# Read current version or default to 1.0.0
if [ -f "$VERSION_FILE" ]; then
    current_version=$(cat "$VERSION_FILE")
else
    current_version="1.0.0"
fi

echo "Current version: $current_version"

# Increment version (patch by default, can be overridden with argument)
increment_type=${1:-patch}
new_version=$(increment_version "$current_version" "$increment_type")

echo "New version: $new_version"
echo "$new_version" > "$VERSION_FILE"

echo "Version updated successfully!"