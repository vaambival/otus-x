# Репликация

## Преднастройки

Перед запуском необходимо установить на рабочей машине [Docker](https://docs.docker.com/engine/install/).

Чтобы запустить ведущий узел БД и два ведомых необходимо в терминале в 
текущей директории [replication](.) выполнить команду: 
```
docker compose up -d --scale postgresql-leader=1 --scale postgresql-follower=2
```

### Тестовые данные

Для создания схемы данных необходимо запустить текущий проект c настройками 
подключения к БД PostgreSQL из [application.yaml](../src/main/resources/application.yaml):
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5431/otus_x
```

Для заполнения экземпляров БД можно выполнить код из [генератора](https://github.com/vaambival/otus-x-generator).
Инструкция в репозитории имеется. После настройки репликации, указанные изменения
применятся к ведомым узлам.

## Настройка чтения с реплики

Указываем ведомый в узел в настройках `datasource` для чтения:
```yaml
spring:
  datasource:
    read:
      url: jdbc:postgresql://localhost:49789/otus_x
```
Порт указываем из bind-а портов докер-контейнера реплики

