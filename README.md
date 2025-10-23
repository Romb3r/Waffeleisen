# Waffeleisen

## Auto-Versioning

This repository is configured with automatic version incrementing using GitHub Actions. The versioning strategy depends on the type of change:

### Versioning Strategy

- **Minor version increment**: When merging pull requests into `main` branch or pushing directly to `main`
- **Patch version increment**: When pushing to any other branch (no naming convention required)

### How it works

- The version follows semantic versioning format: `major.minor.patch`
- Any branch other than `main` increments the patch version (e.g., `1.2.3` → `1.2.4`)
- Merges/pushes to main increment the minor version and reset patch to 0 (e.g., `1.2.3` → `1.3.0`)
- The current version is stored in `version.txt`
- Version updates are committed automatically with the message pattern: `Auto-increment version to X.Y.Z [minor/patch] [skip ci]`

### Branch Usage

Simply create any branch name you want - no prefixes required:
- `my-new-feature` (patch version increment)
- `bug-fix` (patch version increment)
- `improvement` (patch version increment)
- `main` (minor version increment)

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
