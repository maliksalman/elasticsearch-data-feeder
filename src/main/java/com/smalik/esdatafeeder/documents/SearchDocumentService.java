package com.smalik.esdatafeeder.documents;

import com.thedeanda.lorem.LoremIpsum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class SearchDocumentService {

    public static final List<String> POSSIBLE_ATTRIBUTE_KEYS = Arrays.asList(
            "malarkey",
            "covfefe",
            "nii",
            "humph",
            "cattywampus",
            "stooge");
    private static final String[] STOOGES = { "larry", "curley", "moe", "shemp", "joe" };

    private Random random = new Random(529124);
    private LoremIpsum loremIpsum = LoremIpsum.getInstance();

    public SearchDocument generateDocument() {

        LocalDateTime randomDateTime = LocalDateTime.now()
                .minusDays(random.nextInt(1000))
                .plusMinutes(random.nextInt(1000))
                .minusSeconds(random.nextInt(500))
                .plusNanos(random.nextInt(1000) * 1000);

        return SearchDocument.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(randomDateTime)
                .data(loremIpsum.getWords(2,10))
                .attributes(randomAttributes())
                .build();
    }

    private Map<String, String> randomAttributes() {
        HashMap<String, String> attributes = new HashMap<>();
        for (String key: POSSIBLE_ATTRIBUTE_KEYS) {
            attributes.put(key, loremIpsum.getWords(1));
        }
        attributes.put("stooge", STOOGES[random.nextInt(STOOGES.length)]);

        return attributes;
    }
}
