package com.smalik.esdatafeeder.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDocument {
    private String id;
    private String data;
    private LocalDateTime createdAt;
    private Map attributes;
}
