package com.company.model;

import com.company.enums.Colour;
import com.company.enums.Size;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SKU {

    @NotNull
    private String skuId;
    private String title;
    private String description;
    private Colour colour;
    private Size size;
    private BigDecimal cost;
    private int count;
}
