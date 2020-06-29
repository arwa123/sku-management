package com.company.controller;

import com.company.dto.SkuDto;
import com.company.dto.SkuResponseDto;
import com.company.dto.SkuUpdateDto;
import com.company.model.SKU;
import com.company.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/v1")
public class SkuController {

	@Autowired
	@Qualifier(value = "skuServiceImpl")
	ISkuService skuService;



	@PostMapping(path = "/skus")
	public SkuResponseDto addSku(@Valid @RequestBody SkuDto skuDto) throws Exception {
		return skuService.createSku(skuDto);
	}

	@PutMapping(path = "/skus/{skuId}")
	public SkuResponseDto updateSku(@Valid @PathVariable String skuId, @Valid @RequestBody SkuUpdateDto skuUpdateDto) throws Exception {
			return skuService.updateSku(skuId, skuUpdateDto);
	}


	@DeleteMapping(path = "/skus/{skuId}")
	public String deleteSku(@Valid @PathVariable String skuId) throws Exception {
			return skuService.deleteSku(skuId);
	}

	@GetMapping(path = "/skus/{skuId}")
	public SKU getSku(@Valid @PathVariable String skuId) throws Exception {
		return skuService.getSku(skuId);
	}

	@GetMapping(path = "/skus/search/{searchTerm}")
	public Set<SKU> search(@Valid @PathVariable String searchTerm) {

		return skuService.search(searchTerm);
	}

}
