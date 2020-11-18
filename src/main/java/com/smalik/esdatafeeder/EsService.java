package com.smalik.esdatafeeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalik.esdatafeeder.documents.SearchDocument;
import com.smalik.esdatafeeder.documents.SearchDocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class EsService {

    private RestHighLevelClient client;
    private Config config;
    private SearchDocumentService service;
    private ObjectMapper mapper;

    public void createIndex() throws Exception {
        boolean exists = client.indices().exists(new GetIndexRequest(config.getIndexName()), RequestOptions.DEFAULT);
        if (!exists) {
            String mappingsAsJson = "{\n" +
                    "  \"properties\": {\n" +
                    "    \"groupCode\": { \"type\": \"keyword\" },\n" +
                    "    \"groupName\": { \"type\": \"text\" },\n" +
                    SearchDocumentService.POSSIBLE_ATTRIBUTE_KEYS.stream()
                            .map(key -> "    \"attributes." + key + "\": { \"type\": \"keyword\" },\n")
                            .collect(Collectors.joining()) +
                    "    \"startDate\": { \"type\": \"date\" },\n" +
                    "    \"endDate\": { \"type\": \"date\" }\n" +
                    "  }\n" +
                    "}";
            log.info("Mapping: {}", mappingsAsJson);
            CreateIndexRequest indexRequest = new CreateIndexRequest(config.getIndexName())
                    .mapping(mappingsAsJson, XContentType.JSON);

            client.indices().create(indexRequest, RequestOptions.DEFAULT);
            log.info("Created index: Name={}", config.getIndexName());
        }
    }



    public void addDocumentsInBulk(int previousCount) throws Exception {

        BulkRequest bulkRequest = new BulkRequest();

        int thisBatchSize = Math.min(config.getIndexBatchSize(), (config.getIndexTotalDocuments()- previousCount));
        for (int i = 0; i < thisBatchSize; i++) {
            SearchDocument searchDocument = service.generateDocument();
            bulkRequest.add(new IndexRequest(config.getIndexName())
                    .id(searchDocument.getDocumentId())
                    .source(mapper.writeValueAsString(searchDocument.getData()), XContentType.JSON));
        }

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("Added documents: Total={}, BatchSize={}, Time={}",
                previousCount + thisBatchSize,
                thisBatchSize,
                bulkResponse.getTook().getMillis());
    }

    public void close() throws Exception {
        client.close();
    }
}
