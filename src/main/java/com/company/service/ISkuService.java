package com.company.service;

import com.company.dto.SkuDto;
import com.company.dto.SkuResponseDto;
import com.company.dto.SkuUpdateDto;
import com.company.model.SKU;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public interface ISkuService {

    SkuResponseDto createSku(SkuDto skuDto) throws Exception;
    SkuResponseDto updateSku(String skuId, SkuUpdateDto skuDto) throws Exception;
    String deleteSku(String skuId) throws Exception;
    SKU getSku(String skuId) throws Exception;
    Set<SKU> search(String searchTerm);

}
