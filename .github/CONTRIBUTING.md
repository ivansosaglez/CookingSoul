# Contributing to CookingSoul

Thanks for your interest in contributing! Here's how you can help.

---

## Ways to Contribute

- **Report a bug** — open an Issue using the Bug Report template
- **Suggest a feature** — open an Issue using the Feature Request template
- **Fix a bug or add a feature** — fork the repo and open a Pull Request

---

## How to Open an Issue

1. Go to the [Issues tab](../../issues)
2. Click **New Issue**
3. Choose the appropriate template (Bug Report or Feature Request)
4. Fill in the details and submit

Please search existing issues before opening a new one to avoid duplicates.

---

## How to Submit a Pull Request

1. **Fork** this repository (click the Fork button at the top right)
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/CookingSoul.git
   ```
3. **Create a branch** for your change:
   ```bash
   git checkout -b fix/your-bug-description
   # or
   git checkout -b feature/your-feature-name
   ```
4. **Make your changes** and commit:
   ```bash
   git commit -m "fix: describe what you fixed"
   # or
   git commit -m "feat: describe what you added"
   ```
5. **Push** to your fork:
   ```bash
   git push origin your-branch-name
   ```
6. Open a **Pull Request** against the `main` branch of this repo

---

## Commit Message Format

Use this simple format:

| Prefix | When to use |
|--------|-------------|
| `feat:` | Adding something new |
| `fix:` | Fixing a bug |
| `refactor:` | Changing code without adding features or fixing bugs |
| `docs:` | Documentation only changes |
| `test:` | Adding or updating tests |

Example: `fix: crash when opening meal detail with no internet`

---

## Branch Naming

| Type | Format | Example |
|------|--------|---------|
| Bug fix | `fix/short-description` | `fix/favorites-not-saving` |
| New feature | `feature/short-description` | `feature/dark-mode-support` |

---

## Code Style

- Follow the existing code structure (Clean Architecture: data / domain / presentation)
- Use `StateFlow` and `UiState` for ViewModels, consistent with the rest of the project
- Run `./gradlew test` before submitting — all tests must pass

---

## Questions?

If you're unsure about anything, just open an Issue and ask. No question is too small.
