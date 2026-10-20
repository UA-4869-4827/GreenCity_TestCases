# UI testing

## Stack

- Java 21, JUnit 5, Selenium 4, WebDriverManager, Allure
- Page Object Model under `com.greencity.ui`

## Runners

| Runner | When to use |
| --- | --- |
| `BaseTestRunner` | Guest / unauthenticated UI |
| `AuthenticatedBaseTestRunner` | Whole class needs logged-in session (`@Tag("auth")`) |

`AuthenticatedBaseTestRunner` checks `user.email` / `user.password` **before** starting Chrome. Missing or placeholder credentials -> test **skipped**.

One-off login inside a guest class:

```java
ProfilePage profile = loginAsUser(ProfilePage.class);
```

## POM rules

1. Drive UI only through page / component / modal methods.
2. Do not return `WebElement` from the POM - expose intent methods (`isLogoDisplayed()`, `getTitleText()`, ...).
3. Guest paths that open Sign in: `…AsGuest()` returning `SignInModal`.
4. Logged-in paths: method **without** `AsGuest` (waits for destination page).
5. Auth UI is **modals** (`SignInModal`, `SignUpModal`, `ForgotPasswordModal`), not pages.
6. No `Thread.sleep` - explicit waits live in `Base` / POM.
7. Visible copy from `UiMessage` + `src/main/resources/i18n/` (en / uk), not hardcoded English only.
8. `implicitWait` must stay **0**.

## Package map (UI)

```text
ui/page/homepage/       HomePage
ui/page/econews/        EcoNewsPage, NewsDetailsPage, CreateNewsPage, PreviewNewsPage
ui/page/events/         EventsPage, EventDetailsPage, CreateEventPage, ...
ui/page/places/         PlacesPage
ui/page/aboutus/        AboutUsPage
ui/page/profile/        ProfilePage
ui/component/           Header, Footer, NewsCard, EventCard, Comments, ...
ui/modal/               SignIn, SignUp, ForgotPassword, AddPlace
ui/locale/              locale apply + message keys
```

## How to add a UI test

1. Open the GitHub issue (steps / expected).
2. Add class under `src/test/java/com/greencity/ui/` (group by area).
3. Extend `BaseTestRunner` or `AuthenticatedBaseTestRunner`.
4. Assert via POM methods.

```java
public class HeaderGuestTest extends BaseTestRunner {

    @Test
    void guestMySpaceOpensSignIn() {
        SignInModal signIn = homePage.getHeader().openMySpaceAsGuest();
        assertTrue(signIn.getModalTitleText().contains("Welcome back"));
    }
}
```

## Sample tests in repo

- `BaseTest` - logo on Home
- `AuthenticatedSessionTest` - logged-in sample
- `GuestHeaderTests`, `GuestHomePageTest`, `FooterTest`, `NewsDetailsTest`, `EditNewsTest`, ...

## Run

```bash
mvn -Dtest=BaseTest test
mvn -Dtest=EditNewsTest test
mvn test
allure serve target/allure-results
```
