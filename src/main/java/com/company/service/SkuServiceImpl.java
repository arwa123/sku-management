package com.company.service;

import com.company.constants.Constants;
import com.company.dto.SkuDto;
import com.company.dto.SkuResponseDto;
import com.company.dto.SkuUpdateDto;
import com.company.mapper.MapperUtil;
import com.company.model.SKU;
import com.company.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements ISkuService {

    ConcurrentMap<String, SKU> skuDataMap = new ConcurrentHashMap<>();

    @Autowired
    SearchService searchService;

    @Override
    public SkuResponseDto createSku(SkuDto skuDto) throws Exception {
        if (skuDataMap.containsKey(skuDto.getSkuId())) {
            throw new Exception("sku already exists");
        }
        SKU sku = MapperUtil.buildSkuFromSkuDto(skuDto);
        skuDataMap.put(skuDto.getSkuId(), sku);
        searchService.addData(sku);
        return MapperUtil.buildSkuCreateResponse(sku);
    }

    @Override
    public SkuResponseDto updateSku(String skuId, SkuUpdateDto skuUpdateDto) throws Exception {
        if (!skuDataMap.containsKey(skuId)) {
            throw new Exception("sku doesn't exists");
        }
        SKU sku = skuDataMap.get(skuId);
        SKU updatedSku = MapperUtil.buildUpdateSku(skuUpdateDto, sku);
        skuDataMap.put(sku.getSkuId(), updatedSku);
        return MapperUtil.buildSkuUpdateResponse(sku);
    }

    @Override
    public String deleteSku(String skuId) throws Exception {
        if (!skuDataMap.containsKey(skuId)) {
            throw new Exception("sku doesn't exists");
        }
        searchService.removeData(skuDataMap.get(skuId));
        skuDataMap.remove(skuId);
        return "deleted";
    }

    @Override
    public SKU getSku(String skuId) throws Exception {
        if (!skuDataMap.containsKey(skuId)) {
            throw new Exception("sku doesn't exists");
        }
        SKU sku = skuDataMap.get(skuId);
        return sku;
    }

    @Override
    public Set<SKU> search(String searchTerm) {
        Set<SKU> skuSet = new HashSet<>();
        for(String word : searchTerm.split(Constants.SPACE)){
            skuSet.addAll(searchService.search(word));
        }
        return skuSet;
    }

    public Set<SKU> exactSearch(String searchTerm) {
        Set<SKU> skuSet = new HashSet<>();
        for(String word : searchTerm.split(Constants.SPACE)) {
            Set<SKU> skus = searchService.search(word);
            if (skus.isEmpty())
                break;
            if(!skuSet.isEmpty()){
                if(skuSet.containsAll(skus)){
                    skuSet = skuSet.stream()
                            .filter(skus::contains)
                            .collect(Collectors.toSet());
                }
            }else {
                skuSet.addAll(skus);
            }
        }
        return skuSet;
    }


    public Set<SKU> alternateSearch(String searchTerm) {
        return  skuDataMap.values().stream()
                .filter(sku -> sku.getTitle().contains(searchTerm) ||  sku.getDescription().contains(searchTerm))
                .collect(Collectors.toSet());
    }


}
