# Configuration

## Files

| File | In git? | Purpose |
| --- | --- | --- |
| `src/test/resources/config.properties.example` | yes | Template for students |
| `src/test/resources/config.properties` | **no** (gitignore) | Local secrets and overrides |

```bash
cp src/test/resources/config.properties.example src/test/resources/config.properties
```

PowerShell:

```powershell
copy src\test\resources\config.properties.example src\test\resources\config.properties
```

## Resolution order

`AppConfig` resolves each key as:

1. Environment variable (`BASE_API_URL` style: dots -> `_`, uppercased)
2. System property (`-Dbase.api.url=...`)
3. `config.properties`
4. Built-in default in `AppConfig`

## Important keys

```properties
base.ui.url=https://www.greencity.cx.ua/#/greenCity
base.api.url=https://greencity.greencity.cx.ua/
base.user.api.url=https://greencity-user.greencity.cx.ua/

implicitWait=0
explicitWait=10
pageLoadTimeout=30
scriptTimeout=30

locale=en
browser=chrome
headless=false
window.maximize=true

user.email=...
user.name=...
user.password=...
```

### Notes

- **`implicitWait` must be 0** when POM uses explicit waits.
- **`locale`**: `en` or `uk` (GreenCity `localStorage.language`).
- **Auth**: real `user.email` / `user.password` (or env `USER_EMAIL` / `USER_PASSWORD`). Placeholders like `your.user@example.com` / `your_password` are treated as missing -> `@Tag("auth")` tests skip.
- Never commit real passwords.

## Env examples

```bash
# Linux / macOS
export USER_EMAIL='you@example.com'
export USER_PASSWORD='...'
export BASE_API_URL='https://greencity.greencity.cx.ua/'
export BASE_USER_API_URL='https://greencity-user.greencity.cx.ua/'
```

```powershell
$env:USER_EMAIL = 'you@example.com'
$env:USER_PASSWORD = '...'
```

## Checkstyle

```bash
mvn checkstyle:check
```

Config: `config/checkstyle/checkstyle.xml` (runs on main + test sources during `validate`).
