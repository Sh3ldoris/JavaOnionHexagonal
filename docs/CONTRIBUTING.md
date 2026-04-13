# Contributing Guide — Commit Message Conventions

This project follows the [Conventional Commits](https://www.conventionalcommits.org) specification for all commit messages. This ensures a readable git history and enables automated tooling for changelogs and versioning.

You can find cheatsheet for conventional commits here: https://gist.github.com/qoomon/5dfcdf8eec66a051ecd85625518cfd13.

Every commit is checked by git commit-msg hook.

---

## Format

```
<type>(<scope>): <short description>

[optional body]

[optional footer]
```

- **type** — what kind of change this is (required)
- **scope** — what part of the codebase is affected, e.g. `order`, `payment`, `auth` (optional)
- **short description** — imperative, lowercase, no period at the end (required)
- **body** — longer explanation if needed; wrap at 72 characters (optional)
- **footer** — breaking change notes or issue references (optional)

---

## Commit Types

| Type | When to use |
|------|-------------|
| `feat` | Introducing a new feature |
| `fix` | Fixing a bug |
| `chore` | Maintenance tasks that don't affect production code (dependency updates, tooling, build scripts) |
| `docs` | Documentation changes only (README, Javadoc, wiki) |
| `style` | Formatting, whitespace, missing semicolons — no logic change |
| `refactor` | Code restructuring without adding features or fixing bugs |
| `test` | Adding or correcting tests |
| `perf` | Performance improvements |
| `ci` | CI/CD pipeline configuration (GitHub Actions, Jenkins, etc.) |
| `build` | Changes to the build system or dependencies (Maven, Gradle) |
| `revert` | Reverts a previous commit |

---

## Breaking Changes

Append `!` after the type to signal a breaking change:

```
feat(payment)!: replace Stripe with PayPal integration
```

Or describe it in the footer:

```
BREAKING CHANGE: StripeWebhookController no longer exists.
```

---

## Examples

```
feat(order): add discount calculation to checkout flow

fix(auth): resolve token expiry not being validated on refresh

chore: upgrade Spring Boot to 3.3.0

refactor(domain): extract price calculation into ValueObject

test(order): add unit tests for discount edge cases

docs: update module responsibilities in ARCHITECTURE.md

ci: add SonarQube analysis step to GitHub Actions pipeline

feat(payment)!: replace Stripe with PayPal SDK

Migrated payment processing from Stripe to PayPal.
Removes StripeService and all related configuration.

BREAKING CHANGE: StripeWebhookController no longer exists.
Refs: #142
```

---

## Tips

- Use **imperative mood** in the description: `add`, `fix`, `remove` — not `added`, `fixed`, `removed`
- Keep the first line **under 72 characters**
- Reference issue numbers in the footer: `Refs: #42` or `Closes: #42`
- One logical change per commit — avoid mixing a `feat` and a `refactor` in the same commit
