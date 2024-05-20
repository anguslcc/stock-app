# Git Techniques: Tips and Tricks
Last Updated: 20 May 2024, 16:56

# TABLE OF CONTENTS

- [1. Pre-Commit Hooks](#1-pre-commit-hooks)

# 1. Pre-Commit Hooks<a name="#1-pre-commit-hooks"></a>
Sometimes, developers need to perform validations or make modifications just before a commit. For example, you might want to protect certain files from being modified, enforce code formatting standards, or update metadata. The `pre-commit` hook facilitates these operations by running automatically whenever git commit is executed. You can activate this functionality by placing a `pre-commit` script in the `.git/hooks` directory. This script can contain any commands that Git should execute before finalizing a commit, providing a powerful way to ensure quality and consistency in your codebase.


## 1.1  Using Pre-Commit Hooks in Git to Safeguard Your Files
To set up this functionality, place the following code in the `pre-commit` hook file located in your Git repository's `.git/hooks` directory.
```bash
#!/bin/bash
# Enhanced Pre-Commit Hook to Protect Specific Files

# Ensure the script is executed in the root of the Git repository
cd "$(git rev-parse --show-toplevel)" || exit 1

# Array of protected files or patterns
PROTECTED_FILES=(
  "path/to/protected/file1"
  "path/to/protected/file2"
)

# Get a list of all files that have been modified (staged for commit)
MODIFIED_FILES=$(git diff --cached --name-only)

IS_ERROR=0

# Function to check if a file is in the list of modified files
file_is_modified() {
  local file=$1
  echo "$MODIFIED_FILES" | grep -q "^${file}$"
}

# Loop through the list of protected files
for FILE in "${PROTECTED_FILES[@]}"; do
  if file_is_modified "$FILE"; then
    echo "Error: Attempted to commit protected file: $FILE. This file has been automatically unstaged."
    git restore --staged "$FILE"
    IS_ERROR=1
  fi
done

if [ $IS_ERROR -eq 1 ]; then
  echo "Please review your commit"
fi  

exit $IS_ERROR

```

