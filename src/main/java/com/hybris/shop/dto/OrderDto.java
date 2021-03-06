package com.hybris.shop.dto;

import com.hybris.shop.annotations.ColumnNameAlias;
import lombok.Data;

@Data
public class OrderDto {

    @ColumnNameAlias(alias = "Order ID")
    private Long id;

    private UserDto user;

    @ColumnNameAlias(alias = "Order status")
    private String status;

    @ColumnNameAlias(alias = "Date")
    private String createdAt;
}
