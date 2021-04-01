package com.smalik.esdatafeeder.documents;

import com.smalik.esdatafeeder.documents.SearchDocument;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResults {

    public long total;
    public long timeMillis;
    public List<SearchDocument> documents;
}
