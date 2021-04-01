package com.smalik.esdatafeeder.documents;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService service;

    @Operation(summary = "Adds documents with random data to the index")
    @PostMapping("/search")
    public void addDocuments(
            @RequestParam(name = "count", required = false, defaultValue = "1000") int count) {

        service.addDocuments(count);
    }

    @Operation(summary = "Searches the index for the given value in a field")
    @GetMapping("/search/match/{field}/{value}")
    public SearchResults searchMatch(
            @PathVariable("field") String field,
            @PathVariable("value") String value,
            @RequestParam(name = "results", required = false, defaultValue = "10") int results) {

        return service.searchMatch(field, value, results);
    }

    @Operation(summary = "Searches the index for the given prefix value in a field")
    @GetMapping("/search/prefix/{field}/{prefix}")
    public SearchResults searchPrefix(
            @PathVariable("field") String field,
            @PathVariable("prefix") String prefix,
            @RequestParam(name = "results", required = false, defaultValue = "10") int results) {

        return service.searchPrefix(field, prefix, results);
    }

    @Operation(summary = "Searches the index and returns everything")
    @GetMapping("/search")
    public SearchResults search(
            @RequestParam(name = "results", required = false, defaultValue = "10") int results) {

        return service.searchAll(results);
    }
}
