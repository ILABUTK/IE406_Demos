# Tutorial 06 — Collaboration Workflows

**Time to complete:** ~45 minutes
**Prerequisites:** [Tutorial 05 — Working with Remotes](../05_Working_with_Remotes/tutorial.md)
**Next:** [Tutorial 07 — Resolving Merge Conflicts](../07_Resolving_Merge_Conflicts/tutorial.md)

---

## Two Models of Collaboration

There are two common ways teams work together on GitHub:

| Model | When to Use |
|-------|-------------|
| **Shared Repository** | Small teams, class projects — everyone has write access to the same repo |
| **Fork & Pull Request** | Open source — you don't have write access, so you copy the repo and propose changes |

We'll cover both. Most of your class projects will use the **Shared Repository** model.

---

## Model 1 — Shared Repository (Class Group Project)

### Setup: Invite Teammates

The repo owner goes to:
**GitHub repo → Settings → Collaborators → Add people**

Enter teammates' GitHub usernames. They accept the invitation via email.

### The Feature Branch Workflow

Everyone follows this rule: **Never commit directly to `main`.** Always work on a branch.

```
main:   A --- B --------------------------------- G (merge)
                \                               /
alice/feature:   C --- D --- E ---------------/
                                              /
bob/bugfix:          F ------------------   /
```

#### Student A's perspective:

```bash
# 1. Pull latest main before starting
git checkout main
git pull

# 2. Create a branch named after your task
git checkout -b alice/data-analysis

# 3. Do your work
# ... edit files ...
git add analysis.py
git commit -m "Add sales trend analysis"
git add analysis.py
git commit -m "Add chart generation"

# 4. Push your branch to GitHub
git push -u origin alice/data-analysis

# 5. Open a Pull Request on GitHub (next section)
```

#### Student B's perspective (working in parallel):

```bash
git checkout main
git pull
git checkout -b bob/data-loader
# ... work independently ...
git push -u origin bob/data-loader
# Open Pull Request
```

They never interfere with each other because they're on separate branches.

---

## Opening a Pull Request (PR)

A **Pull Request** is a formal request to merge your branch into `main`. It's where code review happens.

### On GitHub:

1. After pushing your branch, GitHub usually shows a yellow banner: **"Compare & pull request"** — click it.
   - Or go to the repository → **Pull requests** → **New pull request**
2. Set **base:** `main` ← **compare:** `alice/data-analysis`
3. Write a title: `Add sales trend analysis`
4. Write a description:

```
## What does this PR do?
Adds a sales trend analysis module that:
- Reads CSV data from the /data folder
- Computes monthly averages
- Generates a line chart saved to /output

## How to test
1. Run `python analysis.py --input data/sales.csv`
2. Check output/sales_trend.png was created

## Related issue
Closes #12
```

5. Assign **Reviewers** (your teammates)
6. Click **Create pull request**

### Good PR Habits

- Keep PRs small and focused — one task per PR
- Link to related issues with `Closes #12` (automatically closes the issue when merged)
- Add screenshots for UI changes
- Respond to reviewer comments professionally

---

## Code Review — The Reviewer's Role

When assigned as a reviewer:

1. Read the PR description
2. Click **Files changed** to see the diff
3. Click the `+` on any line to leave a comment

### Types of Feedback

**Request changes — something must be fixed:**
```
This function doesn't handle an empty list. If `data` is empty,
`data[0]` will throw an IndexError. Can you add a guard?
```

**Comment — suggestion, not blocking:**
```
Nit: consider using a list comprehension here for readability.
(Not blocking, just a thought)
```

**Approve — looks good:**
```
LGTM! Clean implementation. One nit above but not blocking.
```

### Responding to Review as the Author

```bash
# Make the requested changes
git checkout alice/data-analysis
# ... edit files ...
git add analysis.py
git commit -m "Handle empty data input gracefully"
git push
```

The PR automatically updates with your new commits. Respond to each comment ("Done!" or explain why you disagree) and click **Re-request review**.

---

## Merging the Pull Request

Once approved, merge on GitHub:

1. Click **Merge pull request** → **Confirm merge**
2. Click **Delete branch** (keeps the repo tidy)

Back on your computer:

```bash
git checkout main
git pull    # get the merge commit
git branch -d alice/data-analysis    # delete local branch too
```

---

## Model 2 — Fork & Pull Request (Open Source)

Use this when you want to contribute to a repo you don't own.

### Step 1: Fork the Repo

On GitHub, go to the repo you want to contribute to and click **Fork** (top right). This creates your own copy of the repo under your GitHub account.

```
original: github.com/instructor/ie406-project
your fork: github.com/yourusername/ie406-project
```

### Step 2: Clone Your Fork

```bash
git clone git@github.com:yourusername/ie406-project.git
cd ie406-project
```

### Step 3: Add the Original as `upstream`

```bash
git remote add upstream git@github.com:instructor/ie406-project.git
git remote -v
```

Output:
```
origin    git@github.com:yourusername/ie406-project.git (fetch)
origin    git@github.com:yourusername/ie406-project.git (push)
upstream  git@github.com:instructor/ie406-project.git (fetch)
upstream  git@github.com:instructor/ie406-project.git (push)
```

### Step 4: Sync with Upstream Before Working

```bash
git fetch upstream
git checkout main
git merge upstream/main
```

### Step 5: Create a Branch and Make Changes

```bash
git checkout -b fix/typo-in-readme
# ... make changes ...
git add README.md
git commit -m "Fix typo in README introduction"
git push origin fix/typo-in-readme
```

### Step 6: Open a PR from Your Fork

On GitHub, go to your fork → **Pull requests** → **New pull request**.
Set: **base repository:** `instructor/ie406-project` ← **head repository:** `yourusername/ie406-project`, branch `fix/typo-in-readme`

The instructor/maintainer reviews and merges your change.

---

## Keeping Your Fork Up to Date

When the instructor pushes new commits to the original repo:

```bash
git fetch upstream
git checkout main
git merge upstream/main
git push origin main    # update your GitHub fork too
```

---

## GitHub Issues

Issues are how teams track bugs, features, and tasks.

### Create an Issue

Go to the repo → **Issues** → **New issue**

Good issue format:
```
Title: Data loader crashes on empty CSV files

**Describe the bug**
Running `python loader.py --input empty.csv` raises IndexError on line 42.

**To reproduce**
1. Create an empty file: `touch empty.csv`
2. Run: `python loader.py --input empty.csv`

**Expected behavior**
Should print "Warning: empty file" and exit gracefully.

**Environment**
- Python 3.11
- macOS 14.2
```

### Reference Issues in Commits

```bash
git commit -m "Handle empty CSV input gracefully

Fixes #8"
```

Keywords that auto-close issues when the PR merges:
`closes`, `fixes`, `resolves` followed by `#issue-number`

---

## Branch Protection Rules

To enforce team discipline, repo owners can set rules in **Settings → Branches → Add rule**:

- **Require pull request before merging** — no direct pushes to main
- **Require approvals** — PR needs 1+ approvals
- **Require status checks** — tests must pass before merge

These are real settings used in professional software development.

---

## A Full Example: Group Project Timeline

```
Week 1:
  Everyone clones the repo
  Instructor enables branch protection on main

Week 2-3:
  Student A: git checkout -b feature/optimization-model
  Student B: git checkout -b feature/data-pipeline
  Student C: git checkout -b feature/visualization

Week 4:
  Everyone pushes branches, opens Pull Requests
  Review each other's PRs (give feedback, ask questions)
  Make requested changes, push, re-request review

Week 5:
  PRs get approved and merged into main
  Everyone pulls the updated main
  Continue with next set of features
```

---

## Summary

```
Shared Repo Workflow:
  pull → branch → work → commit → push → PR → review → merge → pull

Fork Workflow:
  fork → clone → add upstream → branch → work → commit
  → push to fork → PR to original → review → merge
  → fetch upstream → merge upstream/main
```

---

**Next:** [Tutorial 07 — Resolving Merge Conflicts](../07_Resolving_Merge_Conflicts/tutorial.md)
