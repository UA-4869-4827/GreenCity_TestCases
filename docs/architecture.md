# Architecture

## Purpose

Репозиторій - **автоматизовані тести** для вебзастосунку [GreenCity](https://www.greencity.cx.ua/#/greenCity).

Студенти пишуть JUnit 5 тести за GitHub issues (user stories / test cases). Page Object Model і API clients уже є в `src/main`; тести - у `src/test`.

## High-level view

```text
                    +---------------------------+
                    |   GitHub Issues (US / TC) |
                    +-------------+-------------+
                                  |
                                  v
 +------------------+    +--------+--------+    +------------------+
 |  UI tests        |    |  Shared config  |    |  API tests       |
 |  (Selenium)      |<-->|  AppConfig      |<-->|  (Rest Assured)  |
 |  JUnit runners   |    |  TestValueProvider|   |  JUnit runners   |
 +--------+---------+    +--------+--------+    +--------+---------+
          |                       |                      |
          v                       v                      v
 +------------------+    config.properties / env   +------------------+
 |  Live UI         |                              |  Live backends   |
 |  greencity.cx.ua |                              |  greencity.*     |
 +------------------+                              +------------------+
```

## Layers

| Layer | Location | Role |
| --- | --- | --- |
| Config | `com.greencity.config` | URL, waits, locale, credentials (env > system prop > file > default) |
| UI POM | `com.greencity.ui` | Pages, components, modals, i18n, Selenium helpers |
| API clients | `com.greencity.api.clients` | HTTP wrappers over GreenCity / User services |
| API models | `com.greencity.api.models` | Request/response DTOs |
| Test utils | `com.greencity.utils` | `TestValueProvider` facade over `AppConfig` |
| UI tests | `src/test/.../ui` | Guest / auth UI scenarios |
| API tests | `src/test/.../api` | Guest / auth API scenarios |
| Optional BDD | `src/test/.../cucumber` | Cucumber skeleton (не обов'язковий для first wave) |

## Source layout

```text
src/main/java/com/greencity/
├── config/                 AppConfig
├── ui/
│   ├── Base.java           clicks, type, explicit waits
│   ├── locale/             UiLocale, UiMessage, LocaleSupport
│   ├── page/               Home, EcoNews, Events, Places, ...
│   ├── component/          Header, Footer, cards, comments, ...
│   └── modal/              SignIn, SignUp, ForgotPassword, AddPlace
└── api/
    ├── clients/            BaseClient, AuthClient, EcoNewsClient
    └── models/             auth/, econews/

src/test/java/com/greencity/
├── ui/testrunners/         BaseTestRunner, AuthenticatedBaseTestRunner
├── ui/                     *Test classes
├── api/testRunners/        ApiTestRunner, AuthenticatedApiTestRunner
├── api/support/            ApiAuth
├── api/                    *ApiTest classes
├── utils/TestValueProvider.java
└── cucumber/               optional

src/test/resources/
├── config.properties.example
├── config.properties       gitignored (local secrets)
├── allure.properties
└── features/               Cucumber (optional)

config/checkstyle/          Checkstyle rules (Maven validate)
docs/                       this documentation
```

## Test execution flow

### UI (guest)

1. `BaseTestRunner.@BeforeAll` - WebDriverManager + `TestValueProvider`
2. `@BeforeEach` - Chrome, open `base.ui.url`, apply locale, `homePage`
3. Test uses POM methods only
4. `@AfterEach` - quit driver

### UI (authenticated)

1. Same as guest, but `AuthenticatedBaseTestRunner` assumes credentials **before** Chrome
2. `@BeforeEach` signs in via UI Sign in modal
3. `profilePage` ready; navigate through header/POM

### API (guest)

1. `ApiTestRunner.@BeforeAll` - Rest Assured parsers + guest `EcoNewsClient` on `base.api.url`
2. Test calls client methods, asserts status/body

### API (authenticated)

1. Extends `ApiTestRunner`
2. `AuthenticatedApiTestRunner.@BeforeAll` - `POST /ownSecurity/signIn` on `base.user.api.url`
3. JWT kept in memory (`accessToken` + client `token` field)
4. Authorized clients send `Authorization: Bearer ...`

## External systems

| System | Config | Used by |
| --- | --- | --- |
| GreenCity UI | `base.ui.url` | Selenium |
| GreenCity API | `base.api.url` | Eco news, events, comments, ... |
| User API | `base.user.api.url` | Sign-in JWT (`/ownSecurity/signIn`) |

Current defaults (live client):

- UI: `https://www.greencity.cx.ua/#/greenCity`
- API: `https://greencity.greencity.cx.ua/`
- User: `https://greencity-user.greencity.cx.ua/`

Legacy `*.azurewebsites.net` hosts no longer resolve - do not use them.

## Quality gates

- **Checkstyle** (`mvn checkstyle:check`) - includes test sources; AvoidStarImport etc.
- **JUnit 5** tags: `smoke`, `auth`, `api` (as applied on runners/tests)
- **Allure** results under `target/allure-results`

## Design principles

1. **POM for UI** - tests never call `driver.findElement` directly.
2. **Clients for API** - tests talk to `*Client`, not raw Rest Assured everywhere.
3. **No secrets in git** - only `config.properties.example`.
4. **Live product is source of truth** - TC expected results follow the deployed site / issue.
5. **Skip, don't fail** on missing auth credentials (`Assumptions` before browser / before JWT calls).
6. **Cleanup** for create flows (API: `@AfterEach` delete; UI: similar where feasible).

## Related docs

- [UI testing](ui-testing.md)
- [API testing](api-testing.md)
- [Configuration](configuration.md)
