# Allure

Allure вже підключений до цього репозиторію. Новий тест не потребує нових залежностей: на клас і метод ставляться анотації, на дію в page object або API-клієнті — `@Step`. Звіт збирається з `target/allure-results`.

Живі приклади:

- UI: `src/test/java/com/greencity/ui/BaseTest.java`
- API: `src/test/java/com/greencity/api/EcoNewsApiSmokeTest.java`

## Як додати Allure до тесту

1. Залежності не додавати повторно. У `pom.xml` уже є `allure-junit5`, `aspectjweaver` і `allure-maven`. Surefire передає AspectJ javaagent — без нього `@Step` не потрапляє у звіт. Цей `argLine` не прибирати і не перезаписувати.
2. На класі тесту поставити `@Epic` і `@Feature`.
3. На кожному `@Test` поставити `@Story`, `@Severity`, `@DisplayName` і `@Description`. Якщо тест закриває GitHub issue — додати `@Issue`.
4. Новий публічний метод page / component / modal / client, який є дією сценарію, позначити `@Step`.
5. Запустити тести й зібрати звіт командами з розділу «Як згенерувати звіт».

`@ExtendWith` для Allure не потрібен: `allure-junit5` підхоплюється сам.

## Які анотації ставити

| Анотація | Де | Навіщо |
| --- | --- | --- |
| `@Epic` | клас | Великий блок: `UI` або `API` |
| `@Feature` | клас | Екран або ресурс: `Home`, `Eco News`, `Sign in` |
| `@Story` | метод | Сценарій цього тесту, однією фразою |
| `@Severity` | метод | `BLOCKER`, `CRITICAL`, `NORMAL`, `MINOR`, `TRIVIAL` |
| `@DisplayName` | клас і метод | Назва в звіті. На методі лишай id кейса, якщо він є: `TC-P0-HDR-01 - ...` |
| `@Description` | метод | Що перевіряється і який очікуваний результат |
| `@Issue("123")` | метод | Номер GitHub issue. Повне посилання збирається з `allure.properties` |
| `@Link(name = "...", url = "...")` | метод | Довільне посилання, якщо це не issue цього репозиторію |
| `@Step("...")` | метод POM або клієнта | Крок у звіті |

`@Owner` у цьому репозиторії не ставимо.

Серйозність:

| Рівень | Коли |
| --- | --- |
| `BLOCKER` | Неможливо увійти або відкрити застосунок |
| `CRITICAL` | Основний сценарій: створення, редагування, видалення, авторизований API |
| `NORMAL` | Звичайна перевірка, включно зі smoke на кшталт логотипа |
| `MINOR` | Текст, підписи, другорядні контролі |
| `TRIVIAL` | Дрібна візуальна деталь |

Один тестовий метод — одна `@Story`. Не вішай кілька історій на один метод.

### Приклад UI

Так само зроблено в `BaseTest`:

```java
@Epic("UI")
@Feature("Home")
@DisplayName("Home page")
public class BaseTest extends BaseTestRunner {

    @Test
    @Story("Guest sees the header logo")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Logo is displayed on the home page")
    @Description("Smoke check: a guest on the home page sees the header logo.")
    void logoShouldBeDisplayedOnHomePage() {
        assertTrue(homePage.getHeader().isLogoDisplayed(), "The header logo is not displayed.");
    }
}
```

Якщо кейс прив’язаний до issue, на метод додається номер, без URL:

```java
@Issue("96")
```

Шаблон посилання лежить у `src/test/resources/allure.properties`:

```properties
allure.link.issue.pattern=https://github.com/UA-4869-4827/GreenCity_TestCases/issues/{}
```

`{}` замінюється на значення `@Issue`. Якщо issues ведуться в іншому репозиторії, міняється лише цей рядок.

### `@Step`

Крок пишеться на дії, а не на асерт у тесті. Назва — що зробив користувач. Ім’я параметра в фігурних дужках підставляється у звіт:

```java
@Step("Open news article {newsId}")
public NewsDetailsPage open(long newsId) {
    ...
}
```

Пароль, токен і повний email у текст `@Step`, `@Description` і вкладення не потрапляють. Для пароля достатньо кроку без значення, як у `SignInModal`: `@Step("Enter password")`.

Крок прямо в тесті — лише коли дії ще немає в POM чи клієнті:

```java
Allure.step("Open Eco News", () -> homePage.getHeader().openEcoNews());
```

Краще винести дію в page object і позначити її `@Step`.

## Як згенерувати звіт

Перед звітом, який піде в рев’ю, очисти старі результати. Інакше в звіт потраплять тести з попередніх запусків.

Усі тести:

```bash
mvn clean test
mvn allure:serve
```

Один клас:

```bash
mvn clean -Dtest=BaseTest test
mvn allure:serve
```

`mvn allure:serve` збирає звіт і відкриває його в браузері. Сервер працює, доки процес не зупинено.

Зберегти звіт у файли, без браузера:

```bash
mvn allure:report
```

Файли лежать у `target/site/allure-maven/`. Дивитись звіт треба через `mvn allure:serve`: сторінка `index.html`, відкрита як локальний файл, часто порожня.

Сирі результати кожного запуску: `target/allure-results` (шлях задано в `allure.properties`). Каталог `target/` у git не комітиться.

Окремий Allure Commandline не обов’язковий. Якщо він уже стоїть у `PATH`, ті самі результати відкриваються так:

```bash
allure serve target/allure-results
```
