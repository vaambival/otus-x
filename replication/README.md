# Репликация

## Преднастройки

Перед запуском необходимо установить на рабочей машине [Docker](https://docs.docker.com/engine/install/).

Чтобы запустить ведущий узел БД и два ведомых необходимо в терминале в 
текущей директории [replication](.) выполнить команду: 
```
docker compose up -d --scale postgresql-leader=1 --scale postgresql-follower=2
```

Для остановки контейнеров:
```shell
docker compose down
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

## Синхронная репликация

Для синхронной репликации стоит воспользоваться файлом [sync-replication-compose.yaml](sync-replication-compose.yaml).
Для запуска контейнеров в таком случае нужна команда из текущей [директории](.):
```shell
docker compose -f sync-replication-compose.yaml up -d
```

Для создания нагрузки необходимо выполнить скрипт из файла [insert_load.sh](insert_load.sh)
```shell
./insert_load.sh
```

Количество записанных строк (идентификатор последней строки) можно наблюдать
в файле [test_result.txt](test_result.txt).

Для остановки контейнеров:
```shell
docker compose -f sync-replication-compose.yaml down 
```

