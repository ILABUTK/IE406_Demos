# Tutorial 09 — Common Scenarios & Tips

**Time to complete:** ~45 minutes
**Prerequisites:** [Tutorial 05 — Working with Remotes](../05_Working_with_Remotes/tutorial.md)
**This is the final tutorial in the series.**

---

## Overview

This tutorial covers the situations that trip up beginners most often — how to undo mistakes, keep your repo clean, and use helpful Git features. Bookmark this one; you'll come back to it.

---

## 1. Undoing Mistakes

### "I haven't committed yet — I want to discard my changes"

Throw away all edits to a file since the last commit:

```bash
git checkout -- calculator.py
# Modern syntax:
git restore calculator.py
```

Throw away ALL changes in the entire working directory:

```bash
git restore .
```

> **Warning:** This is permanent. The changes are gone. Use with care.

---

### "I staged something I didn't mean to — unstage it"

```bash
git restore --staged calculator.py
```

The file goes back to being modified (not staged), and your actual edits are preserved.

---

### "I wrote the wrong commit message"

If you haven't pushed yet:

```bash
git commit --amend -m "Correct message here"
```

This replaces the last commit with a new one (different hash, same changes).

> **Never amend a commit that's already been pushed** — it rewrites history and will cause problems for anyone who pulled it.

---

### "I accidentally committed a file I shouldn't have"

Example: you committed `passwords.txt` or `__pycache__/`.

Remove it from tracking but keep it on disk:

```bash
git rm --cached passwords.txt
echo "passwords.txt" >> .gitignore
git add .gitignore
git commit -m "Stop tracking passwords.txt, add to gitignore"
```

If it already got pushed to GitHub, the file is now in the history even after removal. For sensitive data (passwords, API keys), you may need to rotate the credentials and use `git filter-repo` to scrub history — see the section on `git filter-repo` below.

---

### "I want to undo my last commit but keep the changes"

```bash
git reset HEAD~1
```

This "uncommits" the last commit. Your files are unchanged and unstaged. You can edit them and re-commit.

---

### "I want to undo my last commit and throw away the changes too"

```bash
git reset --hard HEAD~1
```

> **Warning:** Changes are permanently lost. Only do this if you're sure.

---

### "I want to undo a commit that's already been pushed"

**Never rewrite history that's been shared.** Instead, create a new commit that reverses the changes:

```bash
git revert a1b2c3d
```

This creates a new "revert commit" that undoes the changes introduced by commit `a1b2c3d`. Safe to push — it doesn't alter history.

---

### "I deleted a branch by accident — recover it"

```bash
# Find the commit the branch was pointing to
git reflog

# You'll see something like:
# a1b2c3d HEAD@{2}: checkout: moving from my-branch to main

# Recreate the branch
git checkout -b my-branch a1b2c3d
```

`git reflog` is Git's "undo history for everything" — it records every time HEAD moved. You can recover almost anything with it within 90 days.

---

### "I committed to the wrong branch"

You meant to commit to a feature branch but accidentally committed to `main`.

```bash
# Create the correct branch from where you are now
git branch feature/my-task

# Move main back one commit (undo the commit on main)
git reset --hard HEAD~1

# Switch to the correct branch (which still has your commit)
git checkout feature/my-task
```

---

## 2. The `.gitignore` File

`.gitignore` tells Git which files to never track. Create it in the root of your repo:

```bash
touch .gitignore
```

### What to Ignore

```gitignore
# Python
__pycache__/
*.pyc
*.pyo
.pytest_cache/
.venv/
venv/
*.egg-info/
dist/

# Environment files (NEVER commit these — they contain secrets)
.env
.env.local
secrets.json
credentials.json

# OS files
.DS_Store          # macOS
Thumbs.db          # Windows
desktop.ini        # Windows

# Editor files
.vscode/
.idea/
*.swp

# Jupyter Notebooks checkpoints
.ipynb_checkpoints/

# Output/build
output/
build/
*.log
```

### How `.gitignore` Works

- `*.log` — ignore all files ending in `.log`
- `build/` — ignore the `build` folder (trailing slash means directory)
- `!important.log` — exception: do track this specific file (even though `*.log` ignores others)
- `**/temp` — ignore `temp` folder anywhere in the project

### GitHub's Pre-Built `.gitignore` Templates

When creating a new repo on GitHub, select a language-specific `.gitignore` template. Or browse templates at [github.com/github/gitignore](https://github.com/github/gitignore).

Add a Python `.gitignore` to an existing repo:

```bash
curl -o .gitignore https://raw.githubusercontent.com/github/gitignore/main/Python.gitignore
git add .gitignore
git commit -m "Add Python .gitignore"
```

---

## 3. `git stash` — Save Work Without Committing

Stash is a temporary shelf for unfinished work. Use it when you need to switch tasks but aren't ready to commit.

**Scenario:** You're halfway through a feature and your instructor asks you to look at a bug on `main` immediately.

```bash
# Save your unfinished work
git stash

# Your working directory is now clean
git status
# nothing to commit, working tree clean

# Switch to main and investigate the bug
git checkout main
# ... fix the bug, commit it ...

# Come back to your feature
git checkout feature/my-task

# Restore your stashed work
git stash pop
```

### Stash Commands

```bash
git stash                        # Stash all tracked modified files
git stash push -m "halfway done with stats"   # Stash with a name
git stash list                   # See all stashes
git stash pop                    # Apply most recent stash and delete it
git stash apply stash@{0}        # Apply a specific stash (keep it in list)
git stash drop stash@{0}         # Delete a stash
git stash clear                  # Delete ALL stashes
git stash show -p                # See what's in the stash
```

---

## 4. `git cherry-pick` — Take One Commit from Another Branch

Sometimes you want just one specific commit from another branch — not the whole branch.

```bash
# Find the commit hash from the other branch
git log other-branch --oneline
# 3f1a2b0 Add useful utility function

# Cherry-pick it onto your current branch
git cherry-pick 3f1a2b0
```

A new commit appears on your branch with the same changes (but a different hash).

**Common use case:** A hotfix was committed on a feature branch but needs to be in `main` immediately.

---

## 5. `git tag` — Mark Important Versions

Tags are permanent labels for specific commits. Use them for releases, submissions, or milestones.

```bash
# Lightweight tag (just a label)
git tag v1.0

# Annotated tag (includes message, author, date — preferred)
git tag -a v1.0 -m "Project submission for Exam 1"

# Tag a specific past commit
git tag -a v0.9 a1b2c3d -m "Pre-exam snapshot"

# List all tags
git tag

# Push tags to GitHub (they don't push automatically)
git push origin --tags

# Push a specific tag
git push origin v1.0
```

On GitHub, tags appear under **Releases** and **Tags** tabs. You can download a zip of any tagged version.

---

## 6. Searching Your History

```bash
# Search commit messages for a keyword
git log --grep="fix"

# Search for when a specific string appeared or disappeared in the code
git log -S "divide_by_zero"

# Search commits by a person
git log --author="Alice"

# See commits between two dates
git log --after="2024-01-01" --before="2024-06-01"

# Find which commit introduced a bug (binary search)
git bisect start
git bisect bad                  # current commit is broken
git bisect good v1.0            # v1.0 was working
# Git checks out the midpoint; test it, then:
git bisect good   # or:
git bisect bad
# Repeat until Git identifies the first bad commit
git bisect reset  # return to HEAD when done
```

---

## 7. Cleaning Up Sensitive Data (`git filter-repo`)

If you accidentally committed a secret (API key, password) and pushed it, **rotate the credential immediately** — assume it's compromised. Then scrub the history:

```bash
# Install git-filter-repo (pip install git-filter-repo)
pip install git-filter-repo

# Remove a specific file from ALL history
git filter-repo --path passwords.txt --invert-paths

# Replace a string in all files across all history
git filter-repo --replace-text <(echo "MYAPIKEY123==>REDACTED")
```

After this, force-push (coordinate with teammates — this rewrites history):

```bash
git push origin --force --all
```

---

## 8. Comparing Across Branches

```bash
# See what commits are on feature but not on main
git log main..feature/new-module --oneline

# See all changes between two branches
git diff main..feature/new-module

# See diff for a specific file between branches
git diff main..feature/new-module -- calculator.py
```

---

## 9. Quick Reference: "What Do I Do If..."

| Situation | Command |
|-----------|---------|
| Discard unsaved changes to a file | `git restore <file>` |
| Unstage a file | `git restore --staged <file>` |
| Fix last commit message | `git commit --amend -m "new msg"` |
| Undo last commit, keep changes | `git reset HEAD~1` |
| Undo last commit, discard changes | `git reset --hard HEAD~1` |
| Undo a pushed commit (safely) | `git revert <hash>` |
| Recover deleted branch | `git reflog` then `git checkout -b name <hash>` |
| Save work without committing | `git stash` |
| Restore stashed work | `git stash pop` |
| Take one commit from another branch | `git cherry-pick <hash>` |
| See who wrote each line | `git blame <file>` |
| Find when a bug was introduced | `git bisect` |
| Create a version tag | `git tag -a v1.0 -m "message"` |
| Stop tracking a committed file | `git rm --cached <file>` |

---

## 10. Good Git Habits

**Commit often, push often**
Small commits are easier to review, easier to understand, and easier to undo. Don't save up a week of work for one massive commit.

**Write meaningful commit messages**
Your future self and teammates are your audience. "Fixed stuff" helps no one.

**Pull before you push**
Always `git pull` at the start of each work session so you're starting from the latest code.

**Never commit to `main` directly**
Always use a branch. Even for tiny one-line changes. This is a habit worth building now.

**Check `git status` constantly**
It's free, it's safe, and it shows you exactly what's going on. Run it after every operation until you're fluent.

**Read error messages**
Git's error messages are actually quite helpful and often suggest the correct fix. Read them carefully before asking for help.

---

## Congratulations!

You've completed the Git & GitHub tutorial series. You now know:

- [x] What Git is and why it exists
- [x] How to configure Git and connect to GitHub
- [x] The core commands: `init`, `add`, `commit`, `log`, `diff`
- [x] Branching and merging for parallel development
- [x] Working with remotes: `clone`, `push`, `pull`, `fetch`
- [x] Collaboration with pull requests and code review
- [x] How to resolve merge conflicts
- [x] How to use GitHub Desktop for a visual workflow
- [x] How to recover from common mistakes

The next step is to **practice on real projects.** Use Git for every class project, even if you're working alone. The habits you build now will serve you throughout your career.

---

## Further Learning

- [Pro Git Book](https://git-scm.com/book) — free, comprehensive, the authoritative reference
- [Oh My Git!](https://ohmygit.org) — a game that teaches Git concepts interactively
- [GitHub Skills](https://skills.github.com) — interactive courses directly on GitHub
- [Atlassian Git Tutorials](https://www.atlassian.com/git/tutorials) — excellent visual explanations

---

*Return to: [Tutorial Series Overview](../README.md)*
