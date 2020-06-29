package com.company.search;

import com.company.constants.Constants;
import com.company.model.SKU;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    SearchTrie searchTrie = new SearchTrie();


    boolean isEmpty() {
        return searchTrie == null;
    }

    void insert(String word, SKU sku) {
        SearchTrie current = searchTrie;

        for (char l : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(l, c -> new SearchTrie());
        }
        current.setEndOfWord(true);
        Set<SKU> skuList = current.getSku();
        skuList.add(sku);
        current.setSku(skuList);
    }

    void deleteSku(String word, SKU sku) {
        SearchTrie current = searchTrie;

        for (char l : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(l, c -> new SearchTrie());
        }
        current.setEndOfWord(true);
        Set<SKU> skuList = current.getSku();
        skuList.remove(sku);
        if(skuList.isEmpty())
            delete(word);
    }


    public Set<SKU> search(String word) {
        SearchTrie current = searchTrie;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            SearchTrie node = current.getChildren().get(ch);
            if (node == null) {
                return new HashSet<>();
            }
            current = node;
        }
        return current.getSku();
    }

    boolean delete(String word) {
        return delete(searchTrie, word, 0);
    }


    private boolean delete(SearchTrie current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord()) {
                return false;
            }
            current.setEndOfWord(false);
            return current.getChildren().isEmpty();
        }
        char ch = word.charAt(index);
        SearchTrie node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.isEndOfWord();

        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(ch);
            return current.getChildren().isEmpty();
        }
        return false;
    }


    public SearchTrie addData(SKU sku) {
        getAllWords(sku).stream().distinct().forEach(word -> {
            insert(word, sku);
        });
        return searchTrie;
    }

    public SearchTrie removeData(SKU sku) {
        getAllWords(sku).stream().distinct().forEach(word -> {
            deleteSku(word, sku);
        });
        return searchTrie;
    }

    private List<String> getAllWords(SKU sku) {
        List<String> words = Lists.newArrayList();
        words.addAll(Arrays.asList(sku.getTitle().split(Constants.SPACE)));
        words.addAll(Arrays.asList(sku.getDescription().split(Constants.SPACE)));
        return words;
    }
}
