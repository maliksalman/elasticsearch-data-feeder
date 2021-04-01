## ElasticSearch Data Feeder

Feeds an elastic-search index with some randomly generated data

### Run ElasticSearch in docker

For testing purposes, start a single node elastic-search cluster in docker:

```
docker run -p 9200:9200 -d --rm --name elasticsearch -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch-oss:7.10.2

```

... to stop/delete the container

```
docker rm -f elasticsearch
```

### Build the app

```
./gradlew clean build
```

### Running the application

```
java -jar build/libs/es-data-feeder-1.0-SNAPSHOT.jar
```
