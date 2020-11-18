package com.smalik.esdatafeeder.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchDocument {

    private String documentId;
    private SearchDocumentData data;
}
