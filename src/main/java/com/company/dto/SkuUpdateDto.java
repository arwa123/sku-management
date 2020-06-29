package com.company.dto;

import com.company.enums.Colour;
import com.company.enums.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuUpdateDto {

    private String title;
    private String description;
    private Colour colour;
    private Size size;
    private BigDecimal cost;
    private int count;
}
