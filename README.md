# GreenCity Automated Tests

UI test project for [GreenCity](https://www.greencity.cx.ua/#/greenCity).

The **Page Object Model is already in place**. Student work is to implement the GitHub test cases (issues) as JUnit tests that go through those page objects — not to rebuild the POM, and not to use raw Selenium in tests.

First wave: **guest** role, default locale **En**. Test cases: issues [#36](https://github.com/UA-4869-4827/GreenCity_TestCases/issues/36)–[#70](https://github.com/UA-4869-4827/GreenCity_TestCases/issues/70). Parent user stories: [#12](https://github.com/UA-4869-4827/GreenCity_TestCases/issues/12)–[#30](https://github.com/UA-4869-4827/GreenCity_TestCases/issues/30).

## Technologies

- **Java 21**
- **JUnit 5**
- **Selenium WebDriver 4**
- **Maven**
- **Allure**
- **Cucumber** / **Rest Assured** — available, not required for the first UI wave

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
│   ├── page/                          # Home, Eco news, Events, Places, About us, …
│   ├── component/                     # header, footer, cards, gallery toggle, comments
│   └── modal/                         # Sign in, Sign up, Forgot password, Add place
└── api/clients/BaseClient.java

src/main/resources/i18n/               # messages_en.properties, messages_uk.properties

src/test/java/com/greencity/
├── ui/testrunners/BaseTestRunner.java # Chrome, locale, HomePage
├── ui/BaseTest.java                   # sample test
├── api/testRunners/ApiTestRunner.java
├── cucumber/
└── utils/TestValueProvider.java
```

`BaseTestRunner` always starts a **guest** session on Home, applies `locale` via `localStorage.language`, and sets implicit wait to zero.

## How to add a UI test

1. Open the GitHub issue for your TC and follow its steps / expected.
2. Create a class under `src/test/java/com/greencity/ui/` (group by area: header, home, news, events, …).
3. Extend `BaseTestRunner`.
4. Use JUnit 5 (`@Test`, assertions).

```java
package com.greencity.ui;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeaderGuestTest extends BaseTestRunner {

    @Test
    void guestMySpaceOpensSignIn() {
        SignInModal signIn = homePage.getHeader().openMySpaceAsGuest();
        assertTrue(signIn.getModalTitleText().contains("Welcome back"));
    }
}
```

Do not add a `SignInPage`. Auth is `homePage.getHeader().clickSignIn()`.

## Allure

```bash
mvn test
allure serve target/allure-results
```

## Requirements

- Java 21+
- Maven 3.8+
- Google Chrome (driver via WebDriverManager)
