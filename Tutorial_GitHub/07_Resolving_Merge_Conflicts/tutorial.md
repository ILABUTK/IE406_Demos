# Tutorial 07 — Resolving Merge Conflicts

**Time to complete:** ~35 minutes
**Prerequisites:** [Tutorial 06 — Collaboration Workflows](../06_Collaboration_Workflows/tutorial.md)
**Next:** [Tutorial 08 — GitHub Desktop](../08_GitHub_Desktop/tutorial.md)

---

## What Is a Merge Conflict?

A merge conflict happens when two branches change the **same line(s) of the same file** in different ways. Git can't automatically decide which version to keep, so it stops and asks you to choose.

**Conflicts feel scary at first. They are not a crisis.** They are a normal, expected part of collaboration. Every developer encounters them constantly. This tutorial will make you comfortable handling them.

---

## When Do Conflicts Happen?

```
main:         ... "return a + b" ...
feature:      ... "return a + b + 0" ...   ← same line, different change
```

When Git tries to merge `feature` into `main`, it doesn't know which version you want. So it marks both and waits for you.

Conflicts do NOT happen when:
- Different files were changed
- Different lines of the same file were changed

---

## Let's Create a Conflict (On Purpose)

The best way to learn is to create and resolve a conflict in a safe environment.

Start in your `ie406-git-practice` repo.

### Setup: Two Branches Edit the Same Line

```bash
# Start from main
git checkout main

# Edit line 1 of calculator.py on main
# Change the docstring of divide to: "Divide a by b, returns float."
```

Edit `calculator.py` so the divide function looks like this:

```python
def divide(a, b):
    "Divide a by b, returns float."
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

```bash
git add calculator.py
git commit -m "Update divide docstring on main"
```

Now create a branch from just before this commit (go back to the previous commit to simulate a teammate who branched earlier):

```bash
git checkout -b feature/safe-divide HEAD~1
```

Edit the same line differently on this branch:

```python
def divide(a, b):
    "Safely divide a by b. Raises ValueError if b is zero."
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

```bash
git add calculator.py
git commit -m "Improve divide docstring on feature branch"
```

Now try to merge:

```bash
git checkout main
git merge feature/safe-divide
```

Output:
```
Auto-merging calculator.py
CONFLICT (content): Merge conflict in calculator.py
Automatic merge failed; fix conflicts then commit the result.
```

Git stopped and is waiting for you.

---

## Reading the Conflict Markers

Open `calculator.py`. You'll see something like this:

```python
def divide(a, b):
<<<<<<< HEAD
    "Divide a by b, returns float."
=======
    "Safely divide a by b. Raises ValueError if b is zero."
>>>>>>> feature/safe-divide
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

Let's decode the markers:

```
<<<<<<< HEAD
    [YOUR version — what's in the current branch (main)]
=======
    [THEIR version — what's coming in from the merged branch]
>>>>>>> feature/safe-divide
```

The `=======` is the divider between the two competing versions.

---

## Resolving the Conflict

You have three choices:

### Option A: Keep Your Version (HEAD)

Delete everything except your version:

```python
def divide(a, b):
    "Divide a by b, returns float."
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

### Option B: Keep Their Version

Delete everything except their version:

```python
def divide(a, b):
    "Safely divide a by b. Raises ValueError if b is zero."
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

### Option C: Write a Combined Version (Most Common)

Delete all conflict markers and write the best version:

```python
def divide(a, b):
    "Divide a by b safely. Returns float. Raises ValueError if b is zero."
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b
```

**Always remove all three conflict markers** (`<<<<<<<`, `=======`, `>>>>>>>`). If you leave them in, Python won't be able to parse the file.

---

## Completing the Merge

After editing the file to resolve the conflict:

```bash
# Check status — file is listed as "both modified"
git status
```

```
On branch main
You have unmerged paths.
  (fix conflicts and run "git commit")

Unmerged paths:
  (use "git add <file>..." to mark resolution)
        both modified:   calculator.py
```

Mark the conflict as resolved by staging the file:

```bash
git add calculator.py
```

Check status again:

```bash
git status
```

```
On branch main
All conflicts fixed but you are still merging.
  (use "git commit" to conclude merge)

Changes to be committed:
        modified:   calculator.py
```

Complete the merge:

```bash
git commit -m "Merge feature/safe-divide: combine docstring improvements"
```

---

## Aborting a Merge

If you get overwhelmed mid-conflict, you can cancel the entire merge and return to the state before you ran `git merge`:

```bash
git merge --abort
```

Everything returns to how it was before. Take a breath, understand both changes, then try again.

---

## Multiple Conflicts in One File

A file can have multiple conflict regions:

```python
<<<<<<< HEAD
def add(a, b):
    """Add two numbers."""
=======
def add(x, y):
    """Return x plus y."""
>>>>>>> feature/rename-params
    return a + b

<<<<<<< HEAD
def multiply(a, b):
=======
def multiply(x, y):
>>>>>>> feature/rename-params
    return a * b
```

You must resolve **every** conflict marker before staging. Most editors will show you how many conflicts remain.

---

## Using VS Code to Resolve Conflicts

VS Code has excellent conflict resolution support. When you open a conflicted file, you see colored sections and clickable buttons:

```
[Accept Current Change]  [Accept Incoming Change]  [Accept Both Changes]  [Compare Changes]
```

- **Accept Current Change** = keep HEAD version
- **Accept Incoming Change** = keep the branch you're merging in
- **Accept Both Changes** = keep both (one after the other)
- **Compare Changes** = open a side-by-side diff view

After clicking, remove any remaining conflict markers and save. Then `git add` and `git commit` as normal.

---

## Preventing Conflicts

You can't always avoid conflicts, but you can reduce them:

1. **Pull frequently** — the more in sync you are with teammates, the fewer conflicts
2. **Work in different files/areas** — coordinate who works on what
3. **Keep branches short-lived** — long-lived branches diverge more
4. **Communicate** — "I'm refactoring `utils.py` today, give me a heads up before touching it"

---

## A Real-World Scenario: Two Teammates Edit `config.py`

**Alice's branch** adds a new database setting:

```python
# config.py
DATABASE_URL = "postgresql://localhost/mydb"
DEBUG = False
MAX_RETRIES = 3    # Alice added this
```

**Bob's branch** changes the debug setting:

```python
# config.py
DATABASE_URL = "postgresql://localhost/mydb"
DEBUG = True    # Bob changed this
MAX_RETRIES = 5    # Bob also added a different value
```

Merge conflict in `config.py`:

```python
DATABASE_URL = "postgresql://localhost/mydb"
<<<<<<< HEAD
DEBUG = False
MAX_RETRIES = 3
=======
DEBUG = True
MAX_RETRIES = 5
>>>>>>> bob/update-config
```

Resolution: Talk to each other. Agree on the right values. Write the final version:

```python
DATABASE_URL = "postgresql://localhost/mydb"
DEBUG = False    # Keep False for production
MAX_RETRIES = 5  # Use Bob's value (discussed over Slack)
```

```bash
git add config.py
git commit -m "Resolve config conflict: keep DEBUG=False, use MAX_RETRIES=5"
```

---

## Summary

```
Conflict resolution steps:
  1. Attempt merge: git merge <branch>
  2. See conflicts: git status
  3. Open conflicted files — look for <<<<<<< markers
  4. Edit files to the desired final state
  5. Remove ALL conflict markers (<<<<<<<, =======, >>>>>>>)
  6. Stage resolved files: git add <file>
  7. Complete the merge: git commit

If overwhelmed: git merge --abort
```

---

**Next:** [Tutorial 08 — GitHub Desktop](../08_GitHub_Desktop/tutorial.md)
