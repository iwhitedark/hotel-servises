# Система бронирования отелей - Hotel Services (Spring Boot microservices)

Итоговый проект «Разработка REST API для системы бронирования отелей на базе Spring Boot с использованием микросервисной архитектуры» по дисциплине "Фреймворк Spring и работа с REST API". 1-й семестр 2-го курса МИФИ ИИКС РПО (2024-2026 уч. г).

Проект демонстрирует базовую микросервисную архитектуру для бронирования отелей на Spring Boot. В составе: Eureka Server для service discovery, API Gateway для маршрутизации, сервис бронирований и сервис управления номерами. Проект использует Java 17, Spring Boot 3.5.x, Spring Cloud и in-memory H2.

## Состав модулей

Репозиторий содержит монорепозиторий `hotel-system` с 4 микросервисами:

```hotel-system/
├── eureka-server/          # Service Registry (порт `8761`)
├── api-gateway/            # API Gateway - маршрутизация запросов (порт `8080`)
├── hotel-service/          # Управление отелями и номерами, выдача рекомендаций по загрузке
└── booking-service/        # Управление бронированиями
```

## Компоненты
- Eureka Server - Сервер регистрации и обнаружения сервисов
- API Gateway - Маршрутизация запросов через Spring Cloud Gateway
- Hotel Service - Управление номерами и рекомендации
- Booking Service - Создание и управление бронированиями

## Технологический стек
- Java: 17
- Spring Boot: 3.5.0
- Spring Cloud: 2024.0.0
- База данных: H2 (in-memory)
- ORM: Spring Data JPA
- Service Discovery: Netflix Eureka
- API Gateway: Spring Cloud Gateway

## Требования

- Java 17+
- Maven 3.9+

## Как запустить

1. Запустите Eureka Server:

   ```bash
   cd hotel-system/eureka-server
   mvn spring-boot:run
   ```

2. Запустите Hotel Service, указав имя и порт (пример):

   ```bash
   cd ../hotel-service
   mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082 --spring.application.name=HOTEL-SERVICE"
   ```

3. Запустите Booking Service:

   ```bash
   cd ../booking-service
   mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=BOOKING-SERVICE"
   ```

4. Запустите API Gateway:

   ```bash
   cd ../api-gateway
   mvn spring-boot:run
   ```

После запуска Eureka будет доступна по адресу `http://localhost:8761`, Gateway — `http://localhost:8080`.

## Маршрутизация через Gateway

Gateway проксирует запросы:

- `/api/bookings/**` → `BOOKING-SERVICE`
- `/api/hotels/**` → `HOTEL-SERVICE`

> Примечание: в текущей реализации `hotel-service` публикует только endpoints для комнат, поэтому запросы к `/api/hotels/**` не обрабатываются. Для интеграции необходимо добавить контроллеры отелей.

## REST API (актуальная реализация)

### Booking Service

**POST** `/api/bookings?roomId={roomId}` — создать бронирование.

- Статусы в ответе: `PENDING`, далее `CONFIRMED` при успешной проверке доступности или `CANCELLED` при ошибке.
- Бизнес-логика вызывает `hotel-service` на эндпоинт подтверждения доступности.

### Hotel Service

**GET** `/api/rooms/recommend` — список комнат, отсортированный по `timesBooked` по возрастанию (при равенстве — по `id`).

**POST** `/api/rooms/{id}/confirm-availability` — stub-эндпоинт подтверждения доступности.

**POST** `/api/rooms/{id}/release` — stub-эндпоинт компенсации.

## Тестирование

Запуск тестов для каждого сервиса:

```bash
cd hotel-system/booking-service
mvn test
```

```bash
cd ../hotel-service
mvn test
```

```bash
cd ../api-gateway
mvn test
```

```bash
cd ../eureka-server
mvn test
```
