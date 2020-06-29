package com.company.search;

import com.company.model.SKU;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class SearchTrie {
    private final Map<Character, SearchTrie> children = new HashMap<>();
    private boolean endOfWord;
    private Set<SKU> sku = new HashSet<>();
}
