# API testing

Пов’язані документи: [How to implement an API TC](implement-api-tc.md), [Configuration](configuration.md), [Architecture](architecture.md).

## Stack

- Rest Assured, Jackson, JUnit 5
- Clients in `com.greencity.api.clients`
- Models in `com.greencity.api.models`

## Backends

| Config | Default | Endpoints |
| --- | --- | --- |
| `base.api.url` | `https://greencity.greencity.cx.ua/` | `/eco-news`, `/events`, ... |
| `base.user.api.url` | `https://greencity-user.greencity.cx.ua/` | `/ownSecurity/signIn` |

Do **not** use deprecated `*.azurewebsites.net` hosts.

## Runners

| Runner | Role |
| --- | --- |
| `ApiTestRunner` | Guest/public API (`@Tag("api")`), shared `ecoNewsClient` without token |
| `AuthenticatedApiTestRunner` | JWT via User service (`@Tag("api")` + `@Tag("auth")`) |

Auth flow:

1. `ApiAuth.signInAsConfiguredUser` -> `AuthClient.signIn(email, password)` with `projectName=GREENCITY`
2. `accessToken` stored in static field (memory only for the JVM run)
3. New `EcoNewsClient(baseApiUrl, accessToken)` sends `Authorization: Bearer ...`

Without real credentials the class is **skipped** (Assumption), not failed.

## Clients (current)

| Client | Responsibility |
| --- | --- |
| `BaseClient` | base URI, Accept/Content-Type, optional Bearer |
| `AuthClient` | `POST /ownSecurity/signIn` |
| `EcoNewsClient` | `GET/POST/DELETE /eco-news` (create = multipart `addEcoNewsDtoRequest`) |

Extend `BaseClient` for events, comments, etc.

## Reference test: create news

`CreateEcoNewsApiTest` (extend `AuthenticatedApiTestRunner`):

| Case | Expected |
| --- | --- |
| Author creates news | `201`, then `GET` by id matches title; `@AfterEach` `DELETE` |
| No token | `401` |
| Text shorter than 20 chars | `400` |

Pattern for students:

1. Build `AddEcoNewsDtoRequest` (unique title, tag e.g. `"news"`)
2. Call `ecoNewsClient.create(...)`
3. Assert status + body
4. Track id and delete in `@AfterEach`

## How to add an API test

1. Add/extend client + DTO if needed.
2. Put test under `src/test/java/com/greencity/api/`.
3. Extend `ApiTestRunner` or `AuthenticatedApiTestRunner`.
4. Prefer client methods over inline Rest Assured in every test.

```java
public class MyAuthApiTest extends AuthenticatedApiTestRunner {
    @Test
    void deleteOwnNews() {
        assertEquals(200, ecoNewsClient.delete(newsId).statusCode());
    }
}
```

## Run

```bash
mvn -Dtest=EcoNewsApiSmokeTest,AuthenticatedApiSmokeTest test
mvn -Dtest=CreateEcoNewsApiTest test
```

## Smoke samples

- `EcoNewsApiSmokeTest` - `GET /eco-news` page
- `AuthenticatedApiSmokeTest` - sign-in returns token
