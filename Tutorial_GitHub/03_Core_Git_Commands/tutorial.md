# Tutorial 03 — Core Git Commands

**Time to complete:** ~45 minutes
**Prerequisites:** [Tutorial 02 — Setup & Configuration](../02_Setup_and_Configuration/tutorial.md)
**Next:** [Tutorial 04 — Branching & Merging](../04_Branching_and_Merging/tutorial.md)

---

## Overview

In this tutorial, you'll build a small Python project from scratch while learning Git's core commands. By the end, you'll have a real commit history you created yourself.

Commands covered: `init`, `status`, `add`, `commit`, `log`, `diff`, `show`, `rm`

---

## Setting Up the Practice Project

Create a new folder and initialize a Git repository inside it:

```bash
mkdir ie406-git-practice
cd ie406-git-practice
git init
```

Output:
```
Initialized empty Git repository in /Users/alex/ie406-git-practice/.git/
```

Git created a hidden `.git/` folder inside your project. **Never manually edit or delete this folder** — it contains your entire project history.

```bash
ls -a
# .  ..  .git
```

---

## `git status` — Your Most-Used Command

```bash
git status
```

Output:
```
On branch main

No commits yet

nothing to commit (create/copy files and use "git add" to track)
```

`git status` is safe to run anytime — it never changes anything. Run it constantly. Think of it as Git's dashboard.

---

## Making Your First File

Create a file:

```bash
# macOS/Linux
echo "# IE 406 Git Practice" > README.md

# Windows (Git Bash)
echo "# IE 406 Git Practice" > README.md
```

Now check the status:

```bash
git status
```

Output:
```
On branch main

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        README.md

nothing added to commit but untracked files present (use "git add" to track)
```

Git sees the file but it's **untracked** — Git is not yet watching it.

---

## `git add` — Stage Your Changes

```bash
git add README.md
git status
```

Output:
```
On branch main

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
        new file:   README.md
```

`README.md` is now in the **staging area** (shown in green). It's ready to be committed.

### Staging Multiple Files

```bash
# Stage a specific file
git add filename.py

# Stage all files in current directory
git add .

# Stage all files with a specific extension
git add *.py

# Stage multiple specific files
git add file1.py file2.py
```

> **Tip:** Prefer staging specific files over `git add .` — it prevents accidentally committing files you didn't mean to include.

---

## `git commit` — Save a Snapshot

```bash
git commit -m "Add README"
```

Output:
```
[main (root-commit) a1b2c3d] Add README
 1 file changed, 1 insertion(+)
 create mode 100644 README.md
```

Congratulations — you just made your first commit! Let's unpack this output:
- `main` — the branch you're on
- `a1b2c3d` — the short commit hash (a unique ID for this snapshot)
- `Add README` — your commit message
- `1 file changed, 1 insertion(+)` — a summary of changes

### Writing Good Commit Messages

Your commit message should complete the sentence: **"If applied, this commit will..."**

| Good | Bad |
|------|-----|
| `Add CSV export function` | `stuff` |
| `Fix off-by-one error in loop` | `fix bug` |
| `Remove deprecated API calls` | `changes` |
| `Add unit tests for data_loader` | `wip` |

A good commit history reads like a project diary. Future-you (and teammates) will thank present-you.

---

## Building the Project — More Commits

Let's add some Python code and make a few more commits to build history.

Create `calculator.py`:

```python
# calculator.py

def add(a, b):
    return a + b

def subtract(a, b):
    return a - b
```

Stage and commit:

```bash
git add calculator.py
git commit -m "Add basic calculator with add and subtract"
```

Now add more functions:

```python
# Add to calculator.py

def multiply(a, b):
    return a * b

def divide(a, b):
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

```bash
git add calculator.py
git commit -m "Add multiply and divide functions"
```

Create a test file `test_calculator.py`:

```python
# test_calculator.py
from calculator import add, subtract, multiply, divide

def test_add():
    assert add(2, 3) == 5
    assert add(-1, 1) == 0

def test_subtract():
    assert subtract(5, 3) == 2

def test_multiply():
    assert multiply(3, 4) == 12

def test_divide():
    assert divide(10, 2) == 5.0
```

```bash
git add test_calculator.py
git commit -m "Add unit tests for calculator functions"
```

---

## `git log` — View Your History

```bash
git log
```

Output:
```
commit 7f3a1b9c2d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a
Author: Alex Johnson <alex.johnson@university.edu>
Date:   Mon Jan 15 14:32:01 2024 -0600

    Add unit tests for calculator functions

commit 3d2e1f0a9b8c7d6e5f4a3b2c1d0e9f8a7b6c5d4e
Author: Alex Johnson <alex.johnson@university.edu>
Date:   Mon Jan 15 14:28:44 2024 -0600

    Add multiply and divide functions

commit 1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b
Author: Alex Johnson <alex.johnson@university.edu>
Date:   Mon Jan 15 14:21:30 2024 -0600

    Add basic calculator with add and subtract

commit 0f1e2d3c4b5a6978675645342312100f1e2d3c4b
Author: Alex Johnson <alex.johnson@university.edu>
Date:   Mon Jan 15 14:15:22 2024 -0600

    Add README
```

### Shorter Log Views

```bash
# One line per commit
git log --oneline
```

Output:
```
7f3a1b9 Add unit tests for calculator functions
3d2e1f0 Add multiply and divide functions
1a2b3c4 Add basic calculator with add and subtract
0f1e2d3 Add README
```

```bash
# Visual branch graph (very useful with branches)
git log --oneline --graph --all

# Show last 3 commits
git log -3

# Show commits by a specific author
git log --author="Alex"

# Show commits from the last week
git log --since="1 week ago"
```

---

## `git diff` — See What Changed

`git diff` shows the exact line-by-line changes in your working directory that have NOT been staged yet.

Make a change to `calculator.py` — add a docstring:

```python
def add(a, b):
    """Return the sum of a and b."""
    return a + b
```

Now run:

```bash
git diff
```

Output:
```diff
diff --git a/calculator.py b/calculator.py
index 8a1b2c3..4d5e6f7 100644
--- a/calculator.py
+++ b/calculator.py
@@ -1,5 +1,6 @@
 def add(a, b):
+    """Return the sum of a and b."""
     return a + b
```

- Lines starting with `+` are additions (shown in green in most terminals)
- Lines starting with `-` are deletions (shown in red)
- Lines with neither are context (unchanged)

```bash
# See what is staged (will be in the next commit)
git diff --staged

# Compare two commits
git diff 1a2b3c4 3d2e1f0

# Compare a file specifically
git diff calculator.py
```

---

## `git show` — Inspect a Specific Commit

```bash
# Show the most recent commit
git show

# Show a specific commit by its hash
git show 3d2e1f0
```

Output shows the commit metadata plus the full diff of that commit.

---

## Staging Partial Changes — The Interactive Way

Sometimes you've made several unrelated changes to one file and want to commit them separately. Git lets you stage specific *chunks* (called "hunks"):

```bash
git add -p calculator.py
```

Git will show you each changed section and ask:
```
Stage this hunk [y,n,q,a,d,e,?]?
```

- `y` — yes, stage this chunk
- `n` — no, skip it
- `s` — split into smaller chunks
- `?` — show help

This is an advanced technique but very useful for keeping commits clean.

---

## `git rm` — Remove Files from Tracking

If you want to delete a file AND remove it from Git's tracking:

```bash
git rm oldfile.py
git commit -m "Remove outdated file"
```

If you want to **stop tracking** a file but keep it on disk (e.g., a file you accidentally committed):

```bash
git rm --cached secrets.txt
echo "secrets.txt" >> .gitignore
git commit -m "Stop tracking secrets.txt"
```

---

## Viewing File History

See every commit that touched a specific file:

```bash
git log --follow calculator.py
```

See who last changed each line of a file (very useful for debugging):

```bash
git blame calculator.py
```

Output:
```
1a2b3c4 (Alex Johnson 2024-01-15 14:21:30) def add(a, b):
3d2e1f0 (Alex Johnson 2024-01-15 14:28:44)     """Return the sum of a and b."""
1a2b3c4 (Alex Johnson 2024-01-15 14:21:30)     return a + b
```

---

## The Full Picture — States of a File

```
Untracked   --(git add)-->   Staged   --(git commit)-->   Committed
                                ^                               |
                                |                               |
                         (git add again)                 (edit file)
                                |                               |
                                +------- Modified  <-----------+
```

```bash
git status
```

Colors in `git status`:
- **Red** = Modified or untracked (not staged)
- **Green** = Staged (will be in next commit)

---

## Summary of Commands

```bash
git init                   # Initialize a new repository
git status                 # Check the state of working directory and staging area
git add <file>             # Stage a specific file
git add .                  # Stage all changed files
git add -p <file>          # Stage changes interactively (hunk by hunk)
git commit -m "message"    # Commit staged changes with a message
git log                    # View full commit history
git log --oneline          # Compact one-line history
git diff                   # Show unstaged changes
git diff --staged          # Show staged changes
git show <hash>            # Show a specific commit
git rm <file>              # Delete file and remove from tracking
git blame <file>           # See who last changed each line
```

---

**Next:** [Tutorial 04 — Branching & Merging](../04_Branching_and_Merging/tutorial.md)
