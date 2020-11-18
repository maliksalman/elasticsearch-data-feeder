package com.smalik.esdatafeeder.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class SearchDocumentData {

    private String groupCode;
    private String groupName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map attributes;
}
