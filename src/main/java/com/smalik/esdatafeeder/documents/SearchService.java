package com.smalik.esdatafeeder.documents;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final RestHighLevelClient client;
    private final SearchDocumentService service;
    private final ObjectMapper mapper;
    private final String indexName = "my-data";

    @SneakyThrows
    public void addDocuments(int count) {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < count; i++) {
            SearchDocument searchDocument = service.generateDocument();
            bulkRequest.add(new IndexRequest(indexName)
                    .id(searchDocument.getId())
                    .source(mapper.writeValueAsString(searchDocument), XContentType.JSON));
        }

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("Added documents: Total={}, Time={}", count, bulkResponse.getTook().getMillis());
    }

    @SneakyThrows
    public SearchResults searchAll(int results) {
        return search(QueryBuilders.matchAllQuery(), results);
    }

    @SneakyThrows
    public SearchResults searchMatch( String field, String value, int results) {
        return search(QueryBuilders.matchQuery(field, value), results);
    }

    @SneakyThrows
    public SearchResults searchPrefix( String field, String prefix, int results) {
        return search(QueryBuilders.prefixQuery(field, prefix), results);
    }

    @SneakyThrows
    private  SearchResults search(QueryBuilder query, int results) {

        // create a query
        SearchRequest request = new SearchRequest(
                new String[] { indexName },
                new SearchSourceBuilder()
                        .query(query)
                        .trackTotalHits(true)
                        .sort("createdAt", SortOrder.DESC)
                        .size(results));

        // search
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // parse the results
        return SearchResults.builder()
                .documents(Arrays.stream(response.getHits().getHits())
                        .map(hit -> getSearchDocumentData(hit))
                        .collect(Collectors.toList()))
                .total(response.getHits().getTotalHits().value)
                .timeMillis(response.getTook().getMillis())
                .build();
    }

    @SneakyThrows
    private SearchDocument getSearchDocumentData(SearchHit hit) {
        return mapper.readValue(hit.getSourceAsString(), SearchDocument.class);
    }

    @PostConstruct
    public void createIndex() throws Exception {
        boolean exists = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
        if (!exists) {
            String mappingsAsJson = "{\n" +
                    "  \"properties\": {\n" +
                    "    \"data\": { \"type\": \"text\" },\n" +
                    "    \"attributes.malarkey\": { \"type\": \"keyword\" },\n" +
                    "    \"attributes.covfefe\": { \"type\": \"keyword\" },\n" +
                    "    \"attributes.nii\": { \"type\": \"keyword\" },\n" +
                    "    \"attributes.humph\": { \"type\": \"keyword\" },\n" +
                    "    \"attributes.cattywampus\": { \"type\": \"keyword\" },\n" +
                    "    \"attributes.stooge\": { \"type\": \"keyword\" },\n" +
                    "    \"createdAt\": { \"type\": \"date_nanos\" }\n" +
                    "  }\n" +
                    "}";
            log.info("Mapping: {}", mappingsAsJson);
            CreateIndexRequest indexRequest = new CreateIndexRequest(indexName)
                    .mapping(mappingsAsJson, XContentType.JSON);

            client.indices().create(indexRequest, RequestOptions.DEFAULT);
            log.info("Created index: Name={}", indexName);
        }
    }
}
