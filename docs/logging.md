# Logging

Журнал прогону потрібен, щоб після `mvn test` було видно, який тест стартував, що зробили сторінки і чим закінчився кейс, без повторного запуску і без секретів у консолі.

SLF4J пише події, Logback розкладає їх по файлах. Конфігурація: `src/test/resources/logback.xml`. Каталог `logs/` у корені проєкту в git не потрапляє.

## Файли

| Файл | Що в ньому |
| --- | --- |
| `logs/test-run.log` | Увесь журнал прогону, від DEBUG і вище для `com.greencity` і від INFO для решти |
| `logs/error.log` | Лише ERROR |
| `logs/warn.log` | Лише WARN |
| `logs/info.log` | Лише INFO |
| `logs/debug.log` | Лише DEBUG |
| `logs/archive/` | Старі шматки тих самих файлів, коли файл переріс 10 МБ або змінився день |

Консоль під час Maven показує INFO і вище. DEBUG лишається у файлах, щоб прогін не заливався деталями.

Сторонні бібліотеки (Selenium, Rest Assured, HTTP-клієнт) лишаються на рівні root INFO. Їхній DEBUG часто містить заголовки запитів, тому його не вмикаємо.

## Рівні

| Рівень | Коли писати |
| --- | --- |
| ERROR | Тест упав, або дія не досягла очікуваного результату і це вже збій. Стек пише `TestRunLoggingExtension` |
| WARN | Тест пропущено (немає credentials, спрацював `Assumptions`), або крок відбувся не так, але прогін ще може продовжитись |
| INFO | Старт, успіх і вимкнення тесту. Дія користувача: відкрили список, пошук, підтвердили або скасували видалення, статус відповіді API |
| DEBUG | Деталі кроку, які не потрібні в консолі: який фільтр натиснули, headless Chrome, проміжний URL |
| TRACE | Зараз не використовується. Не логувати тіло відповіді і не опускати root до TRACE |

Не писати в журнал паролі, токени, email і значення з `config.properties`.

## Де вже стоїть логер

- `TestRunLoggingExtension` на `BaseTestRunner` і `ApiTestRunner`. Кожен UI- і API-тест пише STARTED, потім PASSED, FAILED, SKIPPED або DISABLED.
- `EcoNewsPage`: INFO на відкриття списку, пошук і перехід за URL видаленої новини; DEBUG на фільтр за тегом.
- `NewsDetailsPage`: INFO на відкриття новини, діалог видалення, підтвердження і скасування.
- `DeleteNewsTest` і `DeleteNonExistentEventApiTest`: INFO на сценарій і отриманий HTTP-статус. DEBUG лише на підготовку клієнта, без токена.

Новий логер у сторінці або тесті:

```java
private static final Logger LOG = LoggerFactory.getLogger(EcoNewsPage.class);

LOG.info("Opening Eco news list");
LOG.debug("Filtering Eco news by {}", tag);
```

Поле має називатися `LOG`: Checkstyle вважає `static final` константою.
