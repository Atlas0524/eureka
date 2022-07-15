#!/bin/sh

docker-compose build

docker-compose up -d

set +e

docker-compose exec eureka ./gradlew test

docker-compose down