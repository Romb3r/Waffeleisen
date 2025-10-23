# Waffeleisen

## Auto-Versioning

This repository is configured with automatic version incrementing using GitHub Actions. The versioning strategy depends on the type of change:

### Versioning Strategy

- **Minor version increment**: When merging pull requests into `main` branch or pushing directly to `main`
- **No version changes**: When pushing to feature branches (to avoid merge conflicts)

### How it works

- The version follows semantic versioning format: `major.minor.patch`
- Feature branch pushes trigger the workflow but don't modify `version.txt` (avoiding merge conflicts)
- Only merges/pushes to main increment the minor version and reset patch to 0 (e.g., `1.2.3` → `1.3.0`)
- The current version is stored in `version.txt`
- Version updates are committed automatically with the message pattern: `Auto-increment version to X.Y.Z [minor] [skip ci]`

### Workflow Behavior

- **Feature branches**: Workflow runs and shows status, but no version changes
- **Main branch**: Workflow runs and increments version
- **Pull request merges**: Workflow runs and increments version

This approach eliminates merge conflicts in `version.txt` while still providing visibility into the versioning process.

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
