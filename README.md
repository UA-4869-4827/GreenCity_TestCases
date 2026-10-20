# GreenCity Automated Tests

UI test project for [GreenCity](https://www.greencity.cx.ua/#/greenCity).

The **Page Object Model is already in place**. Student work is to implement the GitHub test cases (issues) as JUnit tests that go through those page objects — not to rebuild the POM, and not to use raw Selenium in tests.

## Documentation

Full architecture and testing guides live in **[`docs/`](docs/README.md)**:

- [Architecture](docs/architecture.md)
- [UI testing](docs/ui-testing.md)
- [API testing](docs/api-testing.md)
- [Configuration](docs/configuration.md)

## Technologies

- **Java 21**
- **JUnit 5**
- **Selenium WebDriver 4**
- **Maven**
- **Allure**
- **Rest Assured** - API starter (clients + smoke tests)
- **Cucumber** - available, optional

## Quick start

### 1. Clone

```bash
git clone https://github.com/UA-4869-4827/GreenCity_TestCases.git
cd GreenCity_TestCases
```

### 2. Config

Copy the example file and fill in credentials locally:

```bash
cp src/test/resources/config.properties.example src/test/resources/config.properties
```

Windows (PowerShell):

```powershell
copy src\test\resources\config.properties.example src\test\resources\config.properties
```

Important keys:

```properties
base.ui.url=https://www.greencity.cx.ua/#/
implicitWait=0
explicitWait=10
locale=en
headless=false
```

`implicitWait` must stay **0**. The POM uses explicit waits only. Mixing implicit wait with `WebDriverWait` makes tests flaky.

`config.properties` is gitignored — do not commit passwords.

### 3. Build

```bash
mvn clean install -DskipTests
```

Style check:

```bash
mvn checkstyle:check
```

### 4. Run tests

Sample smoke (logo on Home):

```bash
mvn -Dtest=BaseTest test
```

One class:

```bash
mvn -Dtest=YourTestClass test
```

All tests:

```bash
mvn test
```

## Conventions (follow these in tests)

- Drive the UI through page / component / modal methods. Do not call `driver.findElement` from a test.
- Guest actions that open Sign in use `…AsGuest()` and return `SignInModal` (for example `openMySpaceAsGuest()`, `addPlaceAsGuest()`, `createEventAsGuest()`). The method without `AsGuest` is the logged-in path and waits for the destination page — it will hang for a guest.
- Sign in / Sign up / Forgot password are **modals**, not pages.
- Do not return `WebElement` from the POM. Assert with page methods (`isLogoDisplayed()`, `getSignUpText()`, …).
- Do not use `Thread.sleep`. If a wait is missing, add an explicit wait in the POM.
- Visible copy that exists in `UiMessage` / `src/main/resources/i18n/` must come from there, not from hardcoded `"Sign up"` strings, so En/Uk still work.
- Expected results follow the **live site** as written in the GitHub issue. If the issue and the UI disagree, raise it — do not “fix” the product in the test.

## Project structure

```
src/main/java/com/greencity/
├── config/AppConfig.java              # config.properties + env/system overrides
├── ui/
│   ├── Base.java                      # clicks, types, explicit waits
│   ├── locale/                        # UiLocale, UiMessage, LocaleSupport
│   ├── page/                          # Home, Eco news, Events, Places, About us, ...
│   ├── component/                     # header, footer, cards, gallery toggle, comments
│   └── modal/                         # Sign in, Sign up, Forgot password, Add place
└── api/
    ├── clients/                       # BaseClient, AuthClient, EcoNewsClient
    └── models/                        # SignIn*, EcoNewsDto, ...

src/main/resources/i18n/               # messages_en.properties, messages_uk.properties

src/test/java/com/greencity/
├── ui/testrunners/BaseTestRunner.java              # guest Chrome, locale, HomePage
├── ui/testrunners/AuthenticatedBaseTestRunner.java # UI sign-in before each test
├── ui/BaseTest.java                                # sample guest test
├── ui/AuthenticatedSessionTest.java                # sample logged-in test
├── api/testRunners/ApiTestRunner.java              # guest/public API
├── api/testRunners/AuthenticatedApiTestRunner.java # JWT via User service
├── api/support/ApiAuth.java
├── api/EcoNewsApiSmokeTest.java                    # sample GET /eco-news
├── api/AuthenticatedApiSmokeTest.java              # sample sign-in
├── cucumber/
└── utils/TestValueProvider.java
```

`BaseTestRunner` always starts a **guest** session on Home, applies `locale` via `localStorage.language`, and sets implicit wait to zero.

## Authenticated UI tests

For TCs that need a logged-in user, **do not** inject tokens into `localStorage`. Sign in through the UI:

1. Put real credentials in `config.properties` (`user.email`, `user.password`) or env `USER_EMAIL` / `USER_PASSWORD`.
2. Extend `AuthenticatedBaseTestRunner` instead of `BaseTestRunner`.
3. Use `profilePage` (already signed in) and navigate via header/POM as usual.

```java
package com.greencity.ui;

import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateNewsTest extends AuthenticatedBaseTestRunner {

    @Test
    void userCanOpenEcoNewsWhileLoggedIn() {
        EcoNewsPage news = profilePage.getHeader().openEcoNews();
        assertTrue(news.getHeader().isLoggedIn());
    }
}
```

One-off login inside a mostly-guest class:

```java
ProfilePage profile = loginAsUser(ProfilePage.class);
```

`AuthenticatedBaseTestRunner` is tagged `@Tag("auth")`. Without real credentials those tests are **skipped** (JUnit Assumption) *before* Chrome starts — not failed.

Do **not** use `…AsGuest()` after login — those methods expect the Sign in modal.

## API starter

Two backends:

| Config key | Default | Used for |
| --- | --- | --- |
| `base.api.url` | `https://greencity.greencity.cx.ua/` | Eco news, events, comments, ... |
| `base.user.api.url` | `https://greencity-user.greencity.cx.ua/` | `POST /ownSecurity/signIn` (JWT) |

Guest / public API test:

```java
public class MyApiTest extends ApiTestRunner {
    @Test
    void listNews() {
        assertEquals(200, ecoNewsClient.getAll(0, 5).statusCode());
    }
}
```

Authenticated API test (needs real `user.email` / `user.password`):

```java
public class MyAuthApiTest extends AuthenticatedApiTestRunner {
    @Test
    void deleteOwnNews() {
        // accessToken and authorized ecoNewsClient are ready
        assertEquals(200, ecoNewsClient.delete(newsId).statusCode());
    }
}
```

Add a client under `api/clients/` (extend `BaseClient`), DTOs under `api/models/`, tests under `api/`.
Tag: `@Tag("api")`. Auth API tests also inherit `@Tag("auth")` and are skipped without credentials.

```bash
mvn -Dtest=EcoNewsApiSmokeTest,AuthenticatedApiSmokeTest test
```

## How to add a UI test

1. Open the GitHub issue for your TC and follow its steps / expected.
2. Create a class under `src/test/java/com/greencity/ui/` (group by area: header, home, news, events, …).
3. Extend `BaseTestRunner` (guest) or `AuthenticatedBaseTestRunner` (logged-in).
4. Use JUnit 5 (`@Test`, assertions).

```java
package com.greencity.ui;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeaderGuestTest extends BaseTestRunner {

    @Test
    void guestMySpaceOpensSignIn() {
        SignInModal signIn = homePage.getHeader().openMySpaceAsGuest();
        assertTrue(signIn.getModalTitleText().contains("Welcome back"));
    }
}
```

Do not add a `SignInPage`. Auth is `homePage.getHeader().clickSignIn()` (or `loginAsUser(...)` / `AuthenticatedBaseTestRunner`).

## Allure

```bash
mvn test
allure serve target/allure-results
```

## Requirements

- Java 21+
- Maven 3.8+
- Google Chrome (driver via WebDriverManager)
