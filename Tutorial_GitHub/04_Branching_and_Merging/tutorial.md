# Tutorial 04 — Branching & Merging

**Time to complete:** ~40 minutes
**Prerequisites:** [Tutorial 03 — Core Git Commands](../03_Core_Git_Commands/tutorial.md)
**Next:** [Tutorial 05 — Working with Remotes](../05_Working_with_Remotes/tutorial.md)

---

## Why Branches Exist

Imagine you're working on a class project. Your group's app is working. You need to add a new feature — a login system — but it will take a few days and might break things along the way.

Without branches, you'd either:
- Edit the working code and risk breaking it for your teammates
- Keep a separate copy of the entire project folder

With branches, you create a lightweight parallel version of your project. Your teammates keep working on `main` while you build the login system on your own branch. When you're done, you merge it in.

---

## Understanding Branches Visually

```
main:     A --- B --- C
                       \
login-feature:          D --- E --- F
```

- `A`, `B`, `C` are commits on `main` — the stable version
- `D`, `E`, `F` are commits on `login-feature` — your work in progress
- When you're done, `F` gets merged back into `main`

```
main:     A --- B --- C ----------- G
                       \           /
login-feature:          D --- E --- F
```

`G` is the merge commit that brings everything together.

---

## `git branch` — Create and List Branches

Continue in the `ie406-git-practice` repo from Tutorial 03.

List all branches:

```bash
git branch
```

Output:
```
* main
```

The `*` shows which branch you're currently on.

Create a new branch:

```bash
git branch add-statistics
```

List again:

```bash
git branch
```

Output:
```
  add-statistics
* main
```

You created the branch, but you're still on `main`. The new branch is a pointer to the same commit — it diverges when you make new commits on it.

---

## `git checkout` / `git switch` — Switch Branches

```bash
git checkout add-statistics
```

Output:
```
Switched to branch 'add-statistics'
```

> **Modern Git (2.23+) alternative:**
> ```bash
> git switch add-statistics
> ```
> Both work. `switch` is clearer but `checkout` is more common and does more things.

Verify:
```bash
git branch
```

Output:
```
* add-statistics
  main
```

### Create and Switch in One Step (Most Common)

```bash
git checkout -b add-statistics
# or
git switch -c add-statistics
```

This is what you'll use 95% of the time.

---

## Making Commits on a Branch

You're now on `add-statistics`. Add a new file:

```python
# stats.py

def mean(data):
    """Return the arithmetic mean of a list."""
    if len(data) == 0:
        raise ValueError("Cannot compute mean of empty list")
    return sum(data) / len(data)

def median(data):
    """Return the median of a list."""
    sorted_data = sorted(data)
    n = len(sorted_data)
    mid = n // 2
    if n % 2 == 0:
        return (sorted_data[mid - 1] + sorted_data[mid]) / 2
    return sorted_data[mid]
```

```bash
git add stats.py
git commit -m "Add mean and median functions"
```

Add more:

```python
# Add to stats.py

def variance(data):
    """Return the population variance."""
    m = mean(data)
    return sum((x - m) ** 2 for x in data) / len(data)

def std_dev(data):
    """Return the population standard deviation."""
    return variance(data) ** 0.5
```

```bash
git add stats.py
git commit -m "Add variance and standard deviation"
```

Now check the log:

```bash
git log --oneline
```

```
8c9d0e1 Add variance and standard deviation
7b8c9d0 Add mean and median functions
7f3a1b9 Add unit tests for calculator functions
3d2e1f0 Add multiply and divide functions
...
```

Switch back to `main` and look at the log:

```bash
git checkout main
git log --oneline
```

```
7f3a1b9 Add unit tests for calculator functions
3d2e1f0 Add multiply and divide functions
1a2b3c4 Add basic calculator with add and subtract
0f1e2d3 Add README
```

The `main` branch doesn't have your stats commits yet — they're safely isolated on `add-statistics`. Also notice that `stats.py` does not exist on `main`:

```bash
ls
# README.md   calculator.py   test_calculator.py
```

Switch back and it reappears:

```bash
git checkout add-statistics
ls
# README.md   calculator.py   stats.py   test_calculator.py
```

This is one of Git's most impressive features — **your working directory changes based on which branch you're on.**

---

## `git merge` — Bring It Together

When your feature is complete, merge it into `main`.

**Rule:** Always merge *into* the branch you want to update. To add `add-statistics` into `main`, switch to `main` first:

```bash
git checkout main
git merge add-statistics
```

Output:
```
Updating 7f3a1b9..8c9d0e1
Fast-forward
 stats.py | 24 ++++++++++++++++++++++++
 1 file changed, 24 insertions(+)
 create mode 100644 stats.py
```

### Fast-Forward Merge

Notice it says **Fast-forward**. This happens when `main` hasn't had any new commits since `add-statistics` branched off. Git simply moves the `main` pointer forward:

```
Before:    main --> C
                     \
                      D --- E  <-- add-statistics

After:     main ---------> E  <-- add-statistics
```

No new merge commit is created — the history is linear.

### Three-Way Merge

If both branches have new commits since they diverged, Git creates a **merge commit**:

```
Before:    main --> C --- F
                     \
                      D --- E  <-- add-statistics

After:     main --> C --- F --- G  (merge commit)
                     \         /
                      D --- E
```

Let's simulate this. On `main`, edit `README.md`:

```bash
git checkout main
echo "## Calculator" >> README.md
git add README.md
git commit -m "Update README with section header"
```

Create a new branch from `add-statistics`:

```bash
git checkout -b add-statistics-tests
```

Create test file:

```python
# test_stats.py
from stats import mean, median, variance, std_dev

def test_mean():
    assert mean([1, 2, 3]) == 2.0
    assert mean([10]) == 10.0

def test_median():
    assert median([1, 2, 3]) == 2
    assert median([1, 2, 3, 4]) == 2.5
```

```bash
git add test_stats.py
git commit -m "Add tests for statistics functions"
```

Now merge into `main`:

```bash
git checkout main
git merge add-statistics-tests
```

Output:
```
Merge made by the 'ort' strategy.
 test_stats.py | 10 ++++++++++
 1 file changed, 10 insertions(+)
 create mode 100644 test_stats.py
```

Git automatically opened your editor to write a merge commit message (just save and close). View the result:

```bash
git log --oneline --graph --all
```

```
*   a1b2c3d Merge branch 'add-statistics-tests'
|\
| * 9f8e7d6 Add tests for statistics functions
| * 8c9d0e1 Add variance and standard deviation
| * 7b8c9d0 Add mean and median functions
* | 5c4b3a2 Update README with section header
|/
* 7f3a1b9 Add unit tests for calculator functions
...
```

---

## Deleting Branches

After merging, a branch is usually no longer needed:

```bash
# Delete a merged branch (safe — won't delete unmerged work)
git branch -d add-statistics
git branch -d add-statistics-tests

# Force delete (even if unmerged — use with caution)
git branch -D experimental-branch
```

---

## Renaming a Branch

```bash
# Rename current branch
git branch -m new-name

# Rename a specific branch
git branch -m old-name new-name
```

---

## Viewing All Branches

```bash
# Local branches only
git branch

# Remote branches only
git branch -r

# All branches (local + remote)
git branch -a
```

---

## A Practical Example: Class Group Project

Here's how a typical group project flows with branches:

```bash
# 1. Everyone clones the shared repo (covered in Tutorial 05)
git clone git@github.com:group/project.git

# 2. Each person creates their own branch for their task
# Student 1 (working on data loading):
git checkout -b feature/data-loader

# Student 2 (working on analysis):
git checkout -b feature/analysis

# Student 3 (working on visualization):
git checkout -b feature/visualization

# 3. Each person works independently and commits
git add .
git commit -m "Implement CSV data loader"

# 4. Push branch to GitHub for others to see (covered in Tutorial 05)
git push origin feature/data-loader

# 5. Open a Pull Request on GitHub (covered in Tutorial 06)
# 6. Teammates review the code
# 7. Merge into main after approval
```

This workflow means no one ever overwrites anyone else's work.

---

## Summary of Commands

```bash
git branch                     # List local branches
git branch <name>              # Create a new branch
git checkout <name>            # Switch to a branch
git checkout -b <name>         # Create and switch in one step
git switch <name>              # Switch (modern syntax)
git switch -c <name>           # Create and switch (modern syntax)
git merge <branch>             # Merge branch into current branch
git branch -d <name>           # Delete a merged branch
git branch -D <name>           # Force delete any branch
git branch -m <new-name>       # Rename current branch
git log --oneline --graph      # Visualize branch history
```

---

**Next:** [Tutorial 05 — Working with Remotes](../05_Working_with_Remotes/tutorial.md)
