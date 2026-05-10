# Book-Talk — Room Booking REST API

Backend-сервис для бронирования переговорных комнат. Реализован на Java 21 + Spring Boot 3, с JWT-авторизацией, ролевой моделью, миграциями БД и контейнеризацией.

---

## Стек технологий

| Категория | Технологии |
|---|---|
| Язык / Платформа | Java 21, Spring Boot 3.3.5 |
| Веб | Spring Web, Spring Validation |
| Безопасность | Spring Security, JWT (jjwt 0.11.5) |
| База данных | PostgreSQL, Spring Data JPA, Hibernate |
| Миграции | Liquibase |
| Маппинг | MapStruct |
| Документация | SpringDoc OpenAPI (Swagger UI) |
| Логирование | SLF4J + Logback (файл + консоль) |
| Контейнеризация | Docker, Docker Compose |
| CI/CD | GitHub Actions → Docker Hub |

---

## Архитектура

```
src/main/java/api/
├── config/          # Spring Security, CORS, Web конфигурация
├── controller/      # REST-контроллеры (Auth, Room, Booking, User, Department, Role)
├── dto/             # DTO для запросов и ответов (get / post / put / delete)
├── entity/          # JPA-сущности (User, Room, Booking, Weekday, Department, Role...)
├── repository/      # Spring Data JPA репозитории
├── security/        # JWT-фильтр, UserDetails
├── service/         # Интерфейсы сервисов + implementations/
├── util/            # ApiResponse, кастомные аннотации, валидаторы, MapStruct-маперы
└── logger/          # Logging interceptor для всех HTTP-запросов
```

Сервис использует **ролевую модель** (USER / ADMIN). При регистрации пользователю автоматически назначается роль `USER` через PostgreSQL-триггер. Роль `ADMIN` назначается вручную.

---

## Переменные окружения

Создай файл `.env` в корне проекта:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/booktalk
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password

JWT_ACCESS_SECRET_KEY=your_base64_access_secret
JWT_REFRESH_SECRET_KEY=your_base64_refresh_secret
JWT_ACCESS_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=604800000

UPLOAD_DIR=uploads/
```

---

## Запуск через Docker Compose

```yaml
# docker-compose.yml
version: '3.8'
services:
  db:
    image: postgres:16
    environment:
      POSTGRES_DB: booktalk
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: your_password
    ports:
      - "5432:5432"

  app:
    image: bigboyjuicer/booktalk:latest
    ports:
      - "8080:8080"
    env_file:
      - .env
    depends_on:
      - db
```

```bash
docker compose up -d
```

Сервис будет доступен на `http://localhost:8080/api/v1`

---

## Документация API

После запуска Swagger UI доступен по адресу:

```
http://localhost:8080/api/v1/swagger-ui.html
```

---

## Авторизация

Все эндпоинты (кроме `/auth/**` и Swagger) требуют JWT Access Token в заголовке:

```
Authorization: Bearer <access_token>
```

Для обновления токена — передай Refresh Token в заголовке:

```
Refresh-Token: <refresh_token>
```

---

## Основные эндпоинты

### Аутентификация
| Метод | URL | Описание |
|---|---|---|
| POST | `/api/v1/auth/signup` | Регистрация |
| POST | `/api/v1/auth/login` | Вход, получение токенов |
| GET | `/api/v1/auth/refresh` | Обновление access token |

### Комнаты (USER / ADMIN)
| Метод | URL | Доступ | Описание |
|---|---|---|---|
| GET | `/api/v1/rooms` | USER | Список всех комнат (с сортировкой) |
| GET | `/api/v1/rooms/{id}` | USER | Комната по ID |
| GET | `/api/v1/rooms/{id}/image` | USER | Фото комнаты |
| GET | `/api/v1/rooms/{id}/days` | USER | Расписание доступных дней |
| POST | `/api/v1/rooms` | ADMIN | Создать комнату (multipart/form-data) |
| PUT | `/api/v1/rooms/{id}` | ADMIN | Обновить комнату |
| PUT | `/api/v1/rooms/{id}/image` | ADMIN | Обновить фото |
| DELETE | `/api/v1/rooms/{id}` | ADMIN | Удалить комнату |

### Бронирования
| Метод | URL | Доступ | Описание |
|---|---|---|---|
| GET | `/api/v1/bookings/room/{id}/{date}` | USER | Расписание комнаты на дату (dd.MM.yyyy) |
| POST | `/api/v1/bookings` | USER | Создать бронирование |
| DELETE | `/api/v1/bookings` | USER | Удалить своё бронирование |

### Пользователь
| Метод | URL | Описание |
|---|---|---|
| GET | `/api/v1/me` | Текущий пользователь |
| GET | `/api/v1/me/bookings` | Мои бронирования (с пагинацией) |
| PUT | `/api/v1/me` | Обновить профиль |
| PUT | `/api/v1/me/image` | Обновить аватар |
| PUT | `/api/v1/me/change-password` | Сменить пароль |
| DELETE | `/api/v1/me` | Удалить аккаунт |

### Отделы и роли (ADMIN)
| Метод | URL | Описание |
|---|---|---|
| GET/POST/PUT/DELETE | `/api/v1/departments` | CRUD отделов |
| GET/POST/PUT/DELETE | `/api/v1/roles` | CRUD ролей |

---

## CI/CD

При пуше в ветку `dev` GitHub Actions автоматически:
1. Собирает проект (`mvn clean package`)
2. Строит Docker-образ
3. Пушит образ на Docker Hub: `bigboyjuicer/booktalk:latest`

---

## База данных

Миграции управляются через **Liquibase**. При первом запуске автоматически создаются таблицы и базовые данные. Схема включает: `users`, `rooms`, `bookings`, `weekdays`, `departments`, `authorities`, `refresh_tokens`, `address`.

---

## Формат ответов

Все эндпоинты возвращают единый формат:

```json
{
  "success": true,
  "message": "Room successfully found",
  "data": { "room": { ... } },
  "errorsDescription": null
}
```
