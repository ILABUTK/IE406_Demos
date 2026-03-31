# Tutorial 02 — Setup & Configuration

**Time to complete:** ~30 minutes
**Prerequisites:** [Tutorial 01 — What Is Git?](../01_What_Is_Git/tutorial.md)
**Next:** [Tutorial 03 — Core Git Commands](../03_Core_Git_Commands/tutorial.md)

---

## Step 1 — Install Git

### macOS

macOS may already have Git. Open Terminal and type:

```bash
git --version
```

If you see something like `git version 2.39.3`, you're done.

If not, install via [Homebrew](https://brew.sh):

```bash
brew install git
```

Or download the macOS installer from [git-scm.com](https://git-scm.com/download/mac).

### Windows

1. Download the installer from [git-scm.com](https://git-scm.com/download/win)
2. Run the installer. **Use all the default settings** — they are sensible for beginners.
3. After installation, open **Git Bash** (it was installed alongside Git). This gives you a Unix-style terminal on Windows. Use this for all commands in these tutorials.

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install git
```

### Verify Installation

```bash
git --version
# git version 2.43.0  (your version may differ, that's fine)
```

---

## Step 2 — Configure Your Identity

Git stamps every commit with your name and email. Set this up once:

```bash
git config --global user.name "Alex Johnson"
git config --global user.email "alex.johnson@university.edu"
```

Use the same email address you'll use for your GitHub account.

**Why `--global`?** It applies to all repos on your computer. You can override it per-repo by running the same command without `--global` inside a specific repo folder.

### Set Your Default Editor

When Git opens a text editor (e.g., to write a commit message), it defaults to `vim`, which confuses most beginners. Change it to something friendlier:

**VS Code (recommended):**
```bash
git config --global core.editor "code --wait"
```

**Nano (simple terminal editor):**
```bash
git config --global core.editor "nano"
```

**Notepad (Windows):**
```bash
git config --global core.editor "notepad"
```

### Set the Default Branch Name

GitHub uses `main` as the default branch name (it used to be `master`). Tell Git to match:

```bash
git config --global init.defaultBranch main
```

### Check Your Configuration

```bash
git config --list
```

Expected output (yours will differ slightly):
```
user.name=Alex Johnson
user.email=alex.johnson@university.edu
core.editor=code --wait
init.defaultbranch=main
```

---

## Step 3 — Create a GitHub Account

1. Go to [github.com](https://github.com) and click **Sign up**
2. Choose a professional username — future employers will see it (e.g., `alexjohnson` not `xXgamer99Xx`)
3. Verify your email address

---

## Step 4 — Connect Git to GitHub with SSH

When you push/pull from GitHub, it needs to verify who you are. **SSH keys** are the standard, secure way to do this — set it up once and never enter your password again.

### What is an SSH Key?

SSH creates a matched pair of keys:
- **Private key** — stays on your computer, never share it
- **Public key** — you give this to GitHub. GitHub uses it to verify that pushes from your computer are really from you.

Think of it as a padlock (public key) that only your key (private key) can open.

### Generate an SSH Key

```bash
ssh-keygen -t ed25519 -C "alex.johnson@university.edu"
```

You'll see:
```
Generating public/private ed25519 key pair.
Enter file in which to save the key (/Users/alex/.ssh/id_ed25519):
```

Press **Enter** to accept the default location.

```
Enter passphrase (empty for no passphrase):
```

You can press **Enter** for no passphrase (convenient) or set one for extra security.

### Add the Key to the SSH Agent

```bash
# Start the agent
eval "$(ssh-agent -s)"

# Add your key
ssh-add ~/.ssh/id_ed25519
```

### Copy Your Public Key

```bash
# macOS
cat ~/.ssh/id_ed25519.pub | pbcopy

# Windows (Git Bash)
cat ~/.ssh/id_ed25519.pub | clip

# Linux
cat ~/.ssh/id_ed25519.pub
# Then manually select and copy the output
```

Your key looks like this (it will be much longer):
```
ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAI... alex.johnson@university.edu
```

### Add the Public Key to GitHub

1. Go to [github.com/settings/keys](https://github.com/settings/keys)
2. Click **New SSH key**
3. Title: give it a name like `My Laptop`
4. Key type: **Authentication Key**
5. Paste your public key into the Key field
6. Click **Add SSH key**

### Test the Connection

```bash
ssh -T git@github.com
```

Expected output:
```
Hi alexjohnson! You've successfully authenticated, but GitHub does not provide shell access.
```

If you see your username — you're connected!

---

## Step 5 — Configure Line Endings (Important on Windows)

Windows and macOS/Linux use different characters to represent the end of a line. This can cause unnecessary changes to show up in Git diffs if your team uses mixed operating systems.

**Windows:**
```bash
git config --global core.autocrlf true
```

**macOS/Linux:**
```bash
git config --global core.autocrlf input
```

---

## Your Configuration File

All global Git settings are stored in a plain text file at `~/.gitconfig`. You can view it:

```bash
cat ~/.gitconfig
```

It looks like this:
```ini
[user]
    name = Alex Johnson
    email = alex.johnson@university.edu
[core]
    editor = code --wait
    autocrlf = input
[init]
    defaultBranch = main
```

You can edit this file directly if you prefer.

---

## Summary of Commands

```bash
git --version                                    # Check Git is installed
git config --global user.name "Your Name"        # Set your name
git config --global user.email "you@email.com"   # Set your email
git config --global init.defaultBranch main      # Set default branch to main
git config --list                                # See all settings
ssh-keygen -t ed25519 -C "you@email.com"         # Generate SSH key
ssh -T git@github.com                            # Test GitHub connection
```

---

**Next:** [Tutorial 03 — Core Git Commands](../03_Core_Git_Commands/tutorial.md)
