# Балансировка

В данной директории представлен [docker-compose](docker-compose.yaml) файл
с контейнерами, настроенными для балансировки. Для балансировки backend используется
[Nginx](https://nginx.org/ru/). Конфигурация представлена [здесь](default.conf).
Для балансировки БД используется [HaProxy](https://www.haproxy.org/). Конфигурация
представлена [здесь](haproxy.cfg).

Для запуска использовать команду:
```shell
docker compose up
```

Для завершения:
```shell
docker compose down
```
Для удаления томов добавить флаг `-v`.