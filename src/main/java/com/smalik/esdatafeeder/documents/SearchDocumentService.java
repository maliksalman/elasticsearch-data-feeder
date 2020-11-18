package com.smalik.esdatafeeder.documents;

import com.thedeanda.lorem.LoremIpsum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class SearchDocumentService {

    public static final List<String> POSSIBLE_ATTRIBUTE_KEYS = Arrays.asList(
            "foobar",
            "covfefe",
            "blabity",
            "ardvark",
            "nii",
            "humph",
            "bahumbug",
            "bumfuzzle",
            "cattywampus",
            "malarkey",
            "brouhaha",
            "stooge");
    private static final int MILLIS_IN_YEAR = 1000 * 60 * 60 * 24 * 365;
    private static final String[] STOOGES = { "larry", "curley", "moe", "shemp", "joe" };

    private Random random = new Random();
    private LoremIpsum loremIpsum = LoremIpsum.getInstance();
    private Set<String> attributeKeys;

    public SearchDocumentService() {
        this.attributeKeys = new HashSet<>();
        int keysCount = Math.max(POSSIBLE_ATTRIBUTE_KEYS.size()/2, random.nextInt(POSSIBLE_ATTRIBUTE_KEYS.size()));
        for (int i = 0; i < keysCount; i++) {
            this.attributeKeys.add(POSSIBLE_ATTRIBUTE_KEYS.get(random.nextInt(POSSIBLE_ATTRIBUTE_KEYS.size())));
        }
    }

    public SearchDocument generateDocument() {

        Date date = new Date(System.currentTimeMillis() - random.nextInt(MILLIS_IN_YEAR));
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String id = UUID.randomUUID().toString();
        return SearchDocument.builder()
                .documentId(id)
                .data(SearchDocumentData.builder()
                        .startDate(localDate)
                        .endDate(localDate.plus(1, ChronoUnit.YEARS))
                        .groupCode(id)
                        .groupName(loremIpsum.getWords(2,4))
                        .attributes(randomAttributes())
                        .build()
                ).build();
    }

    private Map<String, String> randomAttributes() {
        HashMap<String, String> attributes = new HashMap<>();
        for (String key: attributeKeys) {
            attributes.put(key, loremIpsum.getWords(1));
        }
        attributes.put("stooge", STOOGES[random.nextInt(STOOGES.length)]);

        return attributes;
    }
}
