# Tutorial 05 — Working with Remotes

**Time to complete:** ~40 minutes
**Prerequisites:** [Tutorial 04 — Branching & Merging](../04_Branching_and_Merging/tutorial.md)
**Next:** [Tutorial 06 — Collaboration Workflows](../06_Collaboration_Workflows/tutorial.md)

---

## What Is a Remote?

So far, your repository has only lived on your computer. A **remote** is a copy of your repository stored somewhere else — typically on GitHub.

Remotes let you:
- **Back up** your work in the cloud
- **Share** your code with teammates
- **Collaborate** without emailing files
- **Deploy** your code to servers

The most common remote is named `origin` by convention — it's just a nickname for the URL of your GitHub repository.

---

## Part 1 — Pushing an Existing Repo to GitHub

You have a local repo from Tutorial 03. Let's put it on GitHub.

### Step 1: Create a New Repository on GitHub

1. Go to [github.com](https://github.com) and click the **+** icon → **New repository**
2. Name it `ie406-git-practice`
3. **Do NOT** check "Add a README file" (you already have one)
4. **Do NOT** add `.gitignore` or a license (you can do this later)
5. Click **Create repository**

GitHub shows you the next steps. Since you have an existing repo, use the SSH instructions.

### Step 2: Add the Remote

```bash
# In your ie406-git-practice folder:
git remote add origin git@github.com:yourusername/ie406-git-practice.git
```

Replace `yourusername` with your actual GitHub username.

Verify:

```bash
git remote -v
```

Output:
```
origin  git@github.com:yourusername/ie406-git-practice.git (fetch)
origin  git@github.com:yourusername/ie406-git-practice.git (push)
```

You see two lines because Git can use different URLs for reading (`fetch`) and writing (`push`) — though they're usually the same.

### Step 3: Push Your Commits

```bash
git push -u origin main
```

Output:
```
Enumerating objects: 14, done.
Counting objects: 100% (14/14), done.
Delta compression using up to 8 threads
Compressing objects: 100% (10/10), done.
Writing objects: 100% (14/14), 1.23 KiB | 1.23 MiB/s, done.
Total 14 (delta 2), reused 0 (delta 0), pack-reused 0
To git@github.com:yourusername/ie406-git-practice.git
 * [new branch]      main -> main
branch 'main' set up to track 'origin/main'.
```

The `-u` flag sets `origin/main` as the **upstream** for your local `main` branch. You only need `-u` once — from now on, just type `git push`.

Go to your GitHub repository page — you should see all your files!

---

## Part 2 — `git clone` — Download a Repository

Cloning creates a complete copy of a remote repository on your computer, including all history, branches, and commits.

### Clone a Repository

```bash
# Navigate to where you want the folder
cd ~/Documents

git clone git@github.com:yourusername/ie406-git-practice.git
```

Output:
```
Cloning into 'ie406-git-practice'...
remote: Enumerating objects: 14, done.
remote: Counting objects: 100% (14/14), done.
remote: Compressing objects: 100% (8/8), done.
Receiving objects: 100% (14/14), 1.23 KiB | 1.23 KiB/s, done.
```

This creates a folder called `ie406-git-practice` with everything in it. The remote `origin` is automatically set to the URL you cloned from.

### Clone Into a Different Folder Name

```bash
git clone git@github.com:yourusername/ie406-git-practice.git my-project
```

### Clone a Specific Branch

```bash
git clone -b feature/data-loader git@github.com:group/project.git
```

---

## Part 3 — `git push` — Upload Your Commits

After making local commits, push them to GitHub:

```bash
git push
```

If the current branch has no upstream yet:

```bash
git push -u origin my-branch-name
```

### What Gets Pushed?

Only **committed** changes are pushed. Modified files that aren't committed stay local.

```bash
# Make a change
echo "- Statistics module added" >> README.md
git add README.md
git commit -m "Update README with statistics module note"

# Push to GitHub
git push
```

Refresh your GitHub page — the README updates immediately.

### Pushing a Feature Branch

```bash
git checkout -b feature/new-plot
# ... do work, make commits ...
git push -u origin feature/new-plot
```

The branch now appears on GitHub, and your teammates can see it.

---

## Part 4 — `git pull` — Download New Changes

When teammates push commits, you need to download them:

```bash
git pull
```

`git pull` is actually two commands in one:

```
git fetch   +   git merge
```

1. `git fetch` — downloads new commits from the remote (does NOT change your files)
2. `git merge` — merges those commits into your current branch

### Simulate a Remote Change

On the GitHub website, click on `README.md` → click the pencil icon → add a line → commit the change.

Now back in your terminal:

```bash
git pull
```

Output:
```
remote: Enumerating objects: 5, done.
remote: Counting objects: 100% (5/5), done.
remote: Compressing objects: 100% (3/3), done.
Unpacking objects: 100% (3/3), done.
From git@github.com:yourusername/ie406-git-practice
   a1b2c3d..7f8e9d0  main -> origin/main
Updating a1b2c3d..7f8e9d0
Fast-forward
 README.md | 1 +
 1 file changed, 1 insertion(+)
```

Your local `README.md` now has the line you added on GitHub.

### `git pull` with Rebase (Cleaner History)

```bash
git pull --rebase
```

Instead of creating a merge commit, this replays your local commits on top of the downloaded commits. Produces a cleaner, linear history. Many teams require this.

Set it as the default:
```bash
git config --global pull.rebase true
```

---

## Part 5 — `git fetch` — Download Without Merging

`git fetch` downloads changes from the remote but does **not** modify your working files. It's safe to run anytime.

```bash
git fetch origin
```

After fetching, you can inspect what changed before merging:

```bash
# See what commits are on the remote that you don't have
git log origin/main --not main --oneline

# See the diff
git diff main origin/main

# Merge when ready
git merge origin/main
```

---

## Part 6 — Managing Remotes

```bash
# List remotes
git remote -v

# Add a remote
git remote add upstream git@github.com:original-author/project.git

# Rename a remote
git remote rename origin github

# Remove a remote
git remote remove upstream

# Change a remote URL (e.g., when a repo moves)
git remote set-url origin git@github.com:newusername/repo.git
```

### The `upstream` Convention

When you fork someone else's repo (Tutorial 06), you'll have two remotes:

- `origin` — your fork on GitHub
- `upstream` — the original repo you forked from

```bash
git remote add upstream git@github.com:instructor/ie406-project.git
```

This lets you pull in new changes from the original while still pushing to your fork.

---

## Part 7 — Tracking Branches

A **tracking branch** links your local branch to a remote branch so `git push` and `git pull` know where to go.

```bash
# See tracking relationships
git branch -vv
```

Output:
```
* main              a1b2c3d [origin/main] Update README
  add-statistics    8c9d0e1 [origin/add-statistics] Add variance
```

Set tracking manually:

```bash
git branch --set-upstream-to=origin/main main
```

---

## Common Workflow: Start Your Day

```bash
# Always pull before starting work
git pull

# Create a branch for your task
git checkout -b feature/my-task

# ... do work ...

git add .
git commit -m "Implement my task"

# Push your branch
git push -u origin feature/my-task
```

---

## Common Mistakes

### Pushing to the Wrong Branch

```bash
# Check where you are before pushing
git branch
git push
```

### Forgetting to Pull Before Working

If you forget to pull and a teammate pushed new commits, your push will fail:

```
! [rejected]        main -> main (fetch first)
error: failed to push some refs to 'origin'
hint: Updates were rejected because the remote contains work that you do not
hint: have locally.
```

Fix: pull first, then push.

```bash
git pull
git push
```

---

## Summary of Commands

```bash
git remote add origin <url>     # Connect local repo to GitHub
git remote -v                   # List remotes and their URLs
git push -u origin main         # Push and set upstream (first time)
git push                        # Push current branch to its upstream
git clone <url>                 # Download a remote repo
git pull                        # Fetch + merge remote changes
git pull --rebase               # Fetch + rebase (cleaner history)
git fetch                       # Download remote changes without merging
git branch -vv                  # Show tracking relationships
```

---

**Next:** [Tutorial 06 — Collaboration Workflows](../06_Collaboration_Workflows/tutorial.md)
