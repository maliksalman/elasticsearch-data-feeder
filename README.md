## ElasticSearch Data Feeder

Feeds an elastic-search index with some randomly generated data

### Run ElasticSearch in docker

For testing purposes, start a single node elastic-search cluster in docker:

```
docker run -p 9200:9200 -p 9300:9300 -d --rm --name elasticsearch -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.9.3

```

... to stop/delete the container

```
docker rm -f elasticsearch
```

### Build the app

```
./gradlew clean build
```

### Adding data to the cluster

The below command will add `10,000` documents to index named `my-index` with a batch-size of `1,000` where ES is running on http://localhost:9200

```
java -jar build/libs/es-data-feeder-0.0.1-SNAPSHOT.jar
```

To change the index name, batch size, ElasticSearch URL, or total documents in the index, either change the [src/main/resources/application.properties](src/main/resources/application.properties) or override the values with environment variables, like so:

```
INDEX_TOTALDOCS=50000 java -jar build/libs/es-data-feeder-0.0.1-SNAPSHOT.jar
```
