# Tutorial 08 — GitHub Desktop

**Time to complete:** ~40 minutes
**Prerequisites:** A GitHub account ([Tutorial 02](../02_Setup_and_Configuration/tutorial.md))
**Next:** [Tutorial 09 — Common Scenarios & Tips](../09_Common_Scenarios_and_Tips/tutorial.md)

---

## What Is GitHub Desktop?

GitHub Desktop is a free, official graphical user interface (GUI) for Git. Instead of typing commands in a terminal, you click buttons and see visual diffs.

**Who should use it?**
- Students new to Git who find the terminal intimidating
- Anyone who wants a visual overview of their changes
- Developers who switch between projects frequently

**Important:** GitHub Desktop does everything the command line does for everyday tasks. Once you're comfortable with the concepts, you can use whichever tool you prefer — or both.

---

## Installation

1. Download from [desktop.github.com](https://desktop.github.com)
2. Run the installer (Windows or macOS — Linux is not officially supported)
3. Open GitHub Desktop and click **Sign in to GitHub.com**
4. Follow the browser prompts to authenticate

After signing in, GitHub Desktop automatically configures your name and email for Git.

---

## The GitHub Desktop Interface

```
+--------------------------------------------------+
|  [Current Repository ▼]  [Current Branch ▼]  [Fetch/Push] |
+--------------------------------------------------+
|                    |                             |
|   Changes          |    Diff View                |
|   (left panel)     |    (right panel)            |
|                    |                             |
|   Files modified:  |    + added line             |
|   ✓ calculator.py  |    - removed line           |
|   ✓ README.md      |    unchanged line           |
|                    |                             |
+--------------------+-----------------------------+
|  Summary (required)  [Description (optional)]   |
|  [Commit to main]                                |
+--------------------------------------------------+
```

Key areas:
- **Top bar** — switch repos, switch branches, sync with GitHub
- **Left panel** — list of changed files (checkboxes to include/exclude from commit)
- **Right panel** — line-by-line diff (green = added, red = removed)
- **Bottom bar** — write commit message and commit

---

## Part 1 — Creating a New Repository

### Option A: Create from Scratch

1. **File → New repository** (or `Ctrl+N` / `Cmd+N`)
2. Fill in:
   - **Name:** `ie406-desktop-practice`
   - **Local path:** choose a folder on your computer
   - Check **Initialize this repository with a README**
3. Click **Create repository**

GitHub Desktop initializes a Git repo and makes the first commit automatically.

### Option B: Add an Existing Repository

1. **File → Add local repository**
2. Navigate to your `ie406-git-practice` folder (from Tutorial 03)
3. Click **Add repository**

Your existing repo and all its history appear in GitHub Desktop.

---

## Part 2 — Making and Committing Changes

Open the repository in your text editor:
- Click **Open in Visual Studio Code** (or whatever editor is configured)

Make a change to `README.md` — add a line at the end. Save the file.

Switch back to GitHub Desktop. You'll see:

**Left panel:** `README.md` with a checkmark
**Right panel:** the diff showing your addition in green

```diff
  # IE 406 Git Practice

+ ## Overview
+ This project demonstrates core Git concepts.
```

### Write a Commit Message

In the bottom-left:
- **Summary** (required): `Add overview section to README`
- **Description** (optional): More details about what and why

Click **Commit to main**.

The change disappears from the Changes panel — it's been committed.

---

## Part 3 — Viewing History

Click **History** in the left panel (top tabs say "Changes" and "History").

You'll see every commit, with:
- Commit message
- Author and timestamp
- Files changed
- The full diff for that commit

Click any commit to explore it. This is equivalent to `git log` + `git show`.

---

## Part 4 — Publishing to GitHub

If this is a new local repo that doesn't exist on GitHub yet:

Click **Publish repository** (top right, or Repository → Publish repository)

- Set the name
- Choose whether to keep it **private** (recommended for class work)
- Click **Publish repository**

GitHub Desktop pushes everything and sets up the `origin` remote automatically.

---

## Part 5 — Fetching and Pulling

Click **Fetch origin** (top right) to check if there are new commits on GitHub.

If there are new commits, the button changes to **Pull origin** with an arrow and a count:

```
↓ Pull origin (3)
```

Click it to download and merge the changes.

This is equivalent to `git pull`.

---

## Part 6 — Branches

### Create a New Branch

1. Click **Current Branch** dropdown (top middle)
2. Click **New branch**
3. Enter a name: `feature/add-statistics`
4. Click **Create branch**

You're now on the new branch. Make some changes, commit them.

### Switch Branches

Click **Current Branch** → select any branch in the list.

Your files instantly update to reflect that branch — exactly like `git checkout`.

### Publish a Branch to GitHub

When you push a new branch for the first time, the top-right button shows:

```
Publish branch
```

Click it. The branch now appears on GitHub.

---

## Part 7 — Pull Requests

After publishing a branch, GitHub Desktop shows:

```
Create Pull Request
```

Click it. Your browser opens to GitHub with the PR form pre-filled:
- Base branch: `main`
- Compare branch: your branch

Fill in the title and description, then click **Create pull request** on GitHub.

---

## Part 8 — Cloning a Repository

To download someone else's repo:

1. **File → Clone repository**
2. Three options:
   - **GitHub.com tab** — shows all your repositories and organizations
   - **GitHub Enterprise** — for university servers
   - **URL tab** — paste any repo URL
3. Choose the local path
4. Click **Clone**

---

## Part 9 — Resolving Conflicts in GitHub Desktop

When a merge conflict occurs, GitHub Desktop shows:

```
Merge Conflicts
The following files have merge conflicts. Fix the conflicts and commit.

  calculator.py  [Open in editor]
```

Click **Open in editor**. Your editor opens with the conflict markers. Resolve them (as in Tutorial 07), save the file.

Back in GitHub Desktop, the file moves from "conflicts" to "Changes" with a checkmark. Write a commit message and commit — the merge is complete.

---

## Part 10 — Stashing Changes

If you're in the middle of changes and need to switch branches:

1. GitHub Desktop will ask: **"Do you want to stash your changes?"**
2. Click **Stash changes and switch branch**

To restore:
1. Switch back to the original branch
2. **Branch → Pop stash**

---

## GitHub Desktop vs. Command Line — Side by Side

| Task | Command Line | GitHub Desktop |
|------|-------------|----------------|
| Initialize repo | `git init` | File → New repository |
| Stage file | `git add file.py` | Check the checkbox next to file |
| Commit | `git commit -m "msg"` | Type in summary box → Commit button |
| Push | `git push` | Click Push origin |
| Pull | `git pull` | Click Pull origin |
| Create branch | `git checkout -b name` | Current Branch → New branch |
| Switch branch | `git checkout name` | Current Branch → select |
| Merge branch | `git merge name` | Branch → Merge into current branch |
| View history | `git log` | Click History tab |
| View diff | `git diff` | Click any changed file |
| Clone | `git clone url` | File → Clone repository |
| Open PR | (browser) | Click Create Pull Request |

---

## Tips for Using GitHub Desktop Efficiently

- **Always use History to double-check** before pushing — make sure you're committing what you think you are
- **Unchecking files** in the Changes panel is like `git add` — only checked files go into the commit
- **Right-click any file** in the Changes panel for options: discard changes, open in editor, reveal in Explorer
- **Keyboard shortcuts:**
  - `Cmd/Ctrl + Enter` — commit
  - `Cmd/Ctrl + 1` — Changes tab
  - `Cmd/Ctrl + 2` — History tab
  - `Cmd/Ctrl + P` — Push
  - `Cmd/Ctrl + Shift + P` — Pull

---

## When to Graduate to the Command Line

GitHub Desktop is great for everyday tasks, but the command line is better for:

- Complex rebases
- Cherry-picking commits
- Interactive history editing (`git rebase -i`)
- Scripting and automation
- Working on remote servers

Most professional developers use both — GitHub Desktop or a similar GUI for the visual overview, and the terminal for complex operations.

---

**Next:** [Tutorial 09 — Common Scenarios & Tips](../09_Common_Scenarios_and_Tips/tutorial.md)
