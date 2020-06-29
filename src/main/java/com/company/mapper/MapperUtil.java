package com.company.mapper;

import com.company.dto.SkuDto;
import com.company.dto.SkuResponseDto;
import com.company.dto.SkuUpdateDto;
import com.company.model.SKU;

public class MapperUtil {

    public static SKU buildSkuFromSkuDto(SkuDto skuDto) {
        SKU sku = new SKU();
        sku.setColour(skuDto.getColour());
        sku.setCost(skuDto.getCost());
        sku.setCount(skuDto.getCount());
        sku.setDescription(skuDto.getDescription());
        sku.setSize(skuDto.getSize());
        sku.setTitle(skuDto.getTitle());
        sku.setSkuId(skuDto.getSkuId());
        return sku;
    }

    public static SkuResponseDto buildSkuCreateResponse(SKU sku) {
        SkuResponseDto responseDto = new SkuResponseDto();
        responseDto.setSkuId(sku.getSkuId());
        responseDto.setMessage("created");
        return responseDto;
    }

    public static SkuResponseDto buildSkuUpdateResponse(SKU sku) {
        SkuResponseDto responseDto = new SkuResponseDto();
        responseDto.setSkuId(sku.getSkuId());
        responseDto.setMessage("updated");
        return responseDto;
    }

    public static SKU buildUpdateSku(SkuUpdateDto skuUpdateDto, SKU sku) {
        sku.setColour(skuUpdateDto.getColour());
        sku.setCost(skuUpdateDto.getCost());
        sku.setCount(skuUpdateDto.getCount());
        sku.setDescription(skuUpdateDto.getDescription());
        sku.setSize(skuUpdateDto.getSize());
        sku.setTitle(skuUpdateDto.getTitle());
        return sku;
    }
}
