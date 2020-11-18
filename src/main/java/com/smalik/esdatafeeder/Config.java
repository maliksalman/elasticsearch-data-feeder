package com.smalik.esdatafeeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class Config {

    @Value("${es.url}")
    private String elasticSearchUrl;

    @Value("${index.name}")
    private String indexName;

    @Value("${index.batch-size}")
    private int indexBatchSize;

    @Value("${index.total-docs}")
    private int indexTotalDocuments;

    @Bean
    public RestHighLevelClient restClient() {
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(elasticSearchUrl)));
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }
}
