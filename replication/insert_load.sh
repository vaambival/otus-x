#!/bin/bash

export DB_HOST="localhost"
export DB_PORT="5431"
export DB_NAME="otus_x"
export DB_USER="otus_x"
export PGPASSWORD='otus_x'

psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -c "CREATE TABLE test_data(id int, count int)"
id=1
count=10
while true
do
  insert=$(psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -c "INSERT INTO test_data VALUES ('$id', '$count')")
  if [[ -n $insert ]]
  then
    echo "$id" >> test_result.txt
  fi
  id=$((id+1))
  count=$(((count+1)%1000))
  sleep 1
done