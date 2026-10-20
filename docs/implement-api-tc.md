# How to implement an API test case

Покрокова інструкція для студента: від GitHub issue (TC) до зеленого JUnit API-тесту в цьому репозиторії.

Пов’язані документи: [API testing](api-testing.md), [Configuration](configuration.md), [Architecture](architecture.md).

## 0. Передумови

1. Локально є `src/test/resources/config.properties` (скопійований з `.example`).
2. Актуальні URL:

```properties
base.api.url=https://greencity.greencity.cx.ua/
base.user.api.url=https://greencity-user.greencity.cx.ua/
```

3. Для auth-сценаріїв - реальні `user.email` / `user.password` (не placeholder).
4. Прочитані Acceptance Criteria / Steps / Expected у GitHub issue.

## 1. Розібрати TC

З issue випиши:

| Що | Приклад |
| --- | --- |
| Метод + path | `POST /eco-news`, `DELETE /eco-news/{id}` |
| Auth потрібен? | так / ні |
| Request body / query / multipart | JSON part `addEcoNewsDtoRequest`, tags, ... |
| Очікуваний status | `201`, `401`, `400`, `403`, `404` |
| Assert на body | id, title, count, ... |
| Cleanup | видалити створену сутність після тесту |

Якщо в issue немає точного endpoint - дивись продуктний контролер / Swagger / живий Network у браузері, або еталон `CreateEcoNewsApiTest`.

## 2. Обрати runner

| Сценарій | Базовий клас |
| --- | --- |
| Публічний GET без логіну | `ApiTestRunner` |
| Потрібен JWT (create / update / delete свого) | `AuthenticatedApiTestRunner` |

`AuthenticatedApiTestRunner` сам логіниться через User service і дає:

- `accessToken`
- `ecoNewsClient` уже з Bearer
- `@Tag("api")` + `@Tag("auth")` (без credentials тест **skip**, не fail)

## 3. Client і DTO (якщо ще немає)

1. DTO в `src/main/java/com/greencity/api/models/...`
2. Client extends `BaseClient` у `.../api/clients/`
3. Методи client повертають `Response` (або зручний `*AsDto` після status assert)

Приклад ідеї:

```text
AddXDtoRequest  ->  XClient.create(request)  ->  Response
XDto            <-  response.as(XDto.class)
```

Для eco-news create уже є:

- `AddEcoNewsDtoRequest`
- `EcoNewsClient.create(...)` (multipart part name: `addEcoNewsDtoRequest`)

Новий ресурс (events, comments) - додай свій client за цим же шаблоном, не пиши весь Rest Assured у тестовому класі.

## 4. Написати тест-клас

Розташування: `src/test/java/com/greencity/api/`

Рекомендована структура одного happy-path TC:

1. **Arrange** - унікальні дані (`UUID` / timestamp у title)
2. **Act** - виклик client
3. **Assert** - status code + ключові поля (+ іноді повторний GET)
4. **Cleanup** - `@AfterEach` / `try/finally` (delete створеного id)

Негативні TC (401 / 403 / 400):

- 401: окремий client **без** token (`new EcoNewsClient(baseApiUrl)`)
- 403: другий користувач (якщо є в конфігу) або заздалегідь чужий id
- 400: невалідний payload з AC (порожній title, text &lt; 20, ...)

Еталон у репо: `CreateEcoNewsApiTest`.

## 5. Чекліст перед PR

- [ ] Клас extends правильний runner
- [ ] Немає hardcoded паролів / токенів у коді
- [ ] Унікальні дані, щоб не конфліктувати з паралельними прогонами
- [ ] Створені сутності прибираються (delete)
- [ ] Assert саме того, що в Expected Result issue
- [ ] `mvn checkstyle:check` без помилок
- [ ] `mvn -Dtest=YourApiTest test` зелений локально

## 6. Команди

```bash
mvn checkstyle:check
mvn -Dtest=CreateEcoNewsApiTest test
mvn -Dtest=YourApiTestClass test
```

## 7. Типові помилки

| Симптом | Що перевірити |
| --- | --- |
| `UnknownHostException` на `*.azurewebsites.net` | Оновити URL на `*.greencity.cx.ua` (див. configuration) |
| Тест skipped | Немає реальних credentials для `@Tag("auth")` |
| `401` на create | Token не передається / неправильний User URL |
| `400` на валідні дані | Ім'я multipart part, обов'язкові поля, мін. довжина text (20) |
| `403` на delete | Видаляєш не свою сутність |
| Flaky / leftover data | Немає cleanup після create |

## 8. Мінімальний шаблон

```java
package com.greencity.api;

import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YourFeatureApiTest extends AuthenticatedApiTestRunner {

    @Test
    @DisplayName("SHORT: what the TC checks")
    void nameAlignedWithIssue() {
        // arrange
        // act
        Response response = ecoNewsClient.getAll(0, 5);
        // assert
        assertEquals(200, response.statusCode(), response.asPrettyString());
    }
}
```

Підстав свій client/DTO і кроки з issue замість `getAll`.
