# Waffeleisen

## Auto-Versioning

This repository is configured with automatic version incrementing using GitHub Actions. Every time code is pushed to the `main` branch, the version number in `version.txt` is automatically incremented.

### How it works

- The version follows semantic versioning format: `major.minor.patch`
- Each commit to `main` branch automatically increments the patch version
- The current version is stored in `version.txt`
- Version updates are committed automatically with the message pattern: `Auto-increment version to X.Y.Z [skip ci]`

### Manual Version Control

You can also manually increment the version using the provided script:

```bash
# Increment patch version (default)
./scripts/increment-version.sh

# Increment minor version
./scripts/increment-version.sh minor

# Increment major version
./scripts/increment-version.sh major
```

### Current Version

The current version can be found in `version.txt`.
