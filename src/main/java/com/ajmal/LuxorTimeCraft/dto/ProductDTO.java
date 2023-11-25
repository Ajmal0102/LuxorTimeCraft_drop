package com.ajmal.LuxorTimeCraft.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private int categoryId;
    private double price;
    private int count;
    private String description;
    private String imageName;
}
