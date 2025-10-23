# Waffeleisen

## Auto-Versioning

This repository is configured with automatic version incrementing using GitHub Actions. The versioning strategy depends on the type of change:

### Versioning Strategy

This repository implements a comprehensive semantic versioning strategy:

- **🔴 Major version increment**: When creating a tag (e.g., `1.2.3` → `2.0.0`) Tag name should be the incoming version. When main has version 2.0.0, use 3.0.0 as tag name.
- **🟡 Minor version increment**: When merging into `main` or pushing directly to `main` (e.g., `1.2.3` → `1.3.0`, patch resets to 0)
- **🟢 Patch version increment**: When pushing to any feature branch (e.g., `1.2.3` → `1.2.4`) When a merge conflicts occures, use web editor to resolve. Resolve as you like since the version of the main branch will be taken.

### How it works

- **Feature branches**: Each push increments the patch version on that branch
- **Main branch**: Merges/pushes increment minor version and reset patch to 0
- **Tags**: Creating any tag increments major version and resets minor/patch to 0
- The current version is stored in `version.txt`
- Version updates are committed automatically with the message pattern: `Auto-increment version to X.Y.Z [major/minor/patch] [skip ci]`

### Tag Creation Options

**Option 1: Automatic Tag Creation**
When you create any tag, the workflow will:
1. Increment the major version 
2. Create a new properly versioned tag (e.g., `v2.0.0`)
3. Keep your original tag as well

**Option 2: Manual Release Workflow** (Recommended)
Use the "Create Release Tag" workflow in GitHub Actions:
1. Go to Actions → "Create Release Tag" → "Run workflow"
2. Choose release type (major/minor/patch)
3. Add optional tag message
4. Creates properly versioned tag with updated code

### Current Version

The current version can be found in `version.txt`.

# Git usefull commands

#### Merge from main into branch:
```
git checkout <feature_branch_name> && git rebase main
```
**Make sure to do a git pull in main before.**