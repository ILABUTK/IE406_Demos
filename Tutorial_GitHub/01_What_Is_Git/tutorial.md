# Tutorial 01 — What Is Git?

**Time to complete:** ~20 minutes
**Prerequisites:** None
**Next:** [Tutorial 02 — Setup & Configuration](../02_Setup_and_Configuration/tutorial.md)

---

## The Problem Git Solves

Picture this: you're writing a term paper with two classmates. You email the file back and forth.

```
report_final.docx
report_final_v2.docx
report_final_REAL.docx
report_final_Lisa_edits.docx
report_final_FINAL.docx
report_SUBMIT_THIS_ONE.docx
```

Sound familiar? Now imagine this with hundreds of code files and four teammates. This is chaos.

**Git solves this.** It is a *version control system* — software that tracks every change ever made to your files, who made it, when, and why. You always have one folder. Git handles the history invisibly in the background.

---

## A Better Analogy: Google Docs History

If you've ever clicked "Version history" in Google Docs, you already understand the core idea of Git:

- Every saved version is recorded
- You can see what changed between any two versions
- You can restore an older version at any time
- Multiple people can work on it

Git does all of this — but for entire projects (many files, any file type) and with far more control.

---

## Key Vocabulary

These terms will appear constantly. Learn them now.

| Term | Plain English Meaning |
|------|-----------------------|
| **Repository (repo)** | A project folder that Git is tracking |
| **Commit** | A saved snapshot of your project at a point in time |
| **Branch** | A parallel version of your project for trying new ideas |
| **Merge** | Combining one branch back into another |
| **Remote** | A copy of your repo stored online (e.g., on GitHub) |
| **Clone** | Downloading a remote repo to your computer |
| **Push** | Uploading your local commits to the remote |
| **Pull** | Downloading new commits from the remote to your computer |
| **Staging area** | A "loading dock" where you prepare changes before committing |

---

## Git vs. GitHub — They Are Not the Same Thing

This is the #1 source of confusion for beginners.

| | Git | GitHub |
|---|-----|--------|
| **What it is** | Software you install on your computer | A website that hosts Git repositories |
| **Who made it** | Linus Torvalds (2005) | Microsoft (acquired 2018) |
| **Works without the other?** | Yes — Git works fully offline | No — GitHub is useless without Git |
| **Analogy** | Microsoft Word | Google Drive |

Think of it this way: **Git is the engine, GitHub is the garage where you park it and share it.**

Other platforms like GitLab and Bitbucket play the same role as GitHub — they are just different garages.

---

## How Git Thinks About Files

Git has three "zones" for your files:

```
+------------------+      git add      +-------------------+      git commit      +----------------+
|  Working         |  ------------->   |  Staging Area     |  ---------------->  |  Repository    |
|  Directory       |                   |  (Index)          |                     |  (History)     |
|                  |                   |                   |                     |                |
|  Your files as   |                   |  Changes you've   |                     |  Permanent     |
|  you edit them   |                   |  chosen to save   |                     |  snapshots     |
+------------------+                   +-------------------+                     +----------------+
```

**Example:**
1. You edit `homework.py` — it's now in the *Working Directory* as modified
2. You run `git add homework.py` — it moves to the *Staging Area*
3. You run `git commit -m "Finish problem 3"` — it becomes a permanent *commit* in the repo's history

The staging area might seem annoying at first, but it's powerful: you can edit 10 files and only commit 3 of them together — keeping your history clean and meaningful.

---

## What a Commit History Looks Like

Each commit is a node in a chain. Git stores the entire history of your project:

```
[Initial commit]  -->  [Add data loader]  -->  [Fix bug in parser]  -->  [Add unit tests]
     A                       B                         C                        D
                                                                              (current)
```

You can:
- Jump back to commit B to see how your code looked then
- Compare B and D to see everything that changed
- "Undo" commit D and return to C

---

## Why Branches Matter

Branches let you work on something new without breaking what already works.

```
main:     A --- B --- C ----------------------- F   (stable, working code)
                       \                       /
feature:                D --- E  (new feature being developed)
```

You build your feature on the `feature` branch. When it's ready, you merge it into `main`. If you abandon the feature, just delete the branch — `main` is untouched.

---

## The Typical Daily Workflow

Here's what a normal workday with Git looks like:

```
1. Pull latest changes from teammates
         git pull

2. Create a branch for your task
         git checkout -b my-feature

3. Do your work (edit files, write code)

4. Stage your changes
         git add myfile.py

5. Commit with a meaningful message
         git commit -m "Add CSV export function"

6. Push your branch to GitHub
         git push origin my-feature

7. Open a Pull Request on GitHub for teammates to review

8. After approval, merge into main
```

You'll learn every one of these steps in the following tutorials.

---

## Summary

- Git is a **version control system** — it tracks all changes to your project
- GitHub is a **website** that hosts Git repositories online
- Every saved state is called a **commit**
- **Branches** let you experiment without breaking your main code
- The three zones are: **Working Directory → Staging Area → Repository**

---

**Next:** [Tutorial 02 — Setup & Configuration](../02_Setup_and_Configuration/tutorial.md)
