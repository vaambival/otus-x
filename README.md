# Подготовка к запуску

## Подготовка БД

В проекте используется СУБД [PostgreSQL](https://www.postgresql.org/download/). Для корректного запуска приложения следует
создать БД otus_x. Для этого рекомендуется создать пользователя, где `хххххх` - пароль пользователя:
```postgres-psql
CREATE ROLE otus_x WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  CREATEDB
  NOCREATEROLE
  REPLICATION
 PASSWORD 'xxxxxx';
```
Далее необходимо создать БД:
```postgres-psql
CREATE DATABASE otus_x
    WITH
    OWNER = otus_x
    ENCODING = 'UTF8'
    LC_COLLATE = 'ru_RU.UTF-8'
    LC_CTYPE = 'ru_RU.UTF-8'
    CONNECTION LIMIT = -1
    TEMPLATE = template0;
```
Миграции по созданию схем и таблиц БД запустятся автоматически при старте приложения.
Для старта приложения необходимо в файле [application.yaml](src/main/resources/application.yaml)
следует указать настройки по подключению к БД:
* url - строка-ссылка для подключения к БД
* username - имя пользователя БД
* password - пароль для подключения к БД

## Настройка JAVA

Для запуска приложения необходимо использовать OpenJDK-21 (Oracle OpenJDK version 21.0.2).
Найти можно [здесь](https://jdk.java.net/21/).
Для запуска следует запустить команду [из корня проекта](.):
```
./mvnw spring-boot:run
```

Приложение запустится на порту 8080 (стандартный порт). Если он у вас занят, то
можно указать свободный порт в файле [application.yaml](src/main/resources/application.yaml):
```yaml
server:
  port: 8080
```

## Postman коллекция

Для тестирования Postman коллекцию запросов с переменными окружения вы можете найти 
[здесь](postman). Эти файлы следует импортировать в Postman

## Балансировка

Все сведения по балансировке можно посмотреть [здесь](balancer/README.md)