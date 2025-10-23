# Waffeleisen

## Auto-Versioning

This repository is configured with automatic version incrementing using GitHub Actions. The versioning strategy depends on the type of change:

### Versioning Strategy

This repository implements a comprehensive semantic versioning strategy:

- **🔴 Major version increment**: When creating a tag (e.g., `1.2.3` → `2.0.0`)
- **🟡 Minor version increment**: When merging into `main` or pushing directly to `main` (e.g., `1.2.3` → `1.3.0`, patch resets to 0)
- **🟢 Patch version increment**: When pushing to any feature branch (e.g., `1.2.3` → `1.2.4`)

### How it works

- **Feature branches**: Each push increments the patch version on that branch
- **Main branch**: Merges/pushes increment minor version and reset patch to 0
- **Tags**: Creating any tag increments major version and resets minor/patch to 0
- The current version is stored in `version.txt`
- Version updates are committed automatically with the message pattern: `Auto-increment version to X.Y.Z [major/minor/patch] [skip ci]`

### Workflow Examples

1. **Feature development**:
   ```
   main: 1.0.0
   ├─ feature-branch: 1.0.1 → 1.0.2 → 1.0.3 (patch increments)
   └─ merge to main: 1.1.0 (minor increment, patch reset)
   ```

2. **Release process**:
   ```
   main: 1.5.0
   └─ create tag v1.5.0: 2.0.0 (major increment, minor/patch reset)
   ```

### Usage

- **Feature development**: Work on any branch name, version increments automatically
- **Release to main**: Merge PR or push directly, gets new minor version
- **Major release**: Create a tag (e.g., `git tag v1.0.0 && git push origin v1.0.0`)

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
