package com.jithin.ecommerce.dto.converter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductFilterRequestDto {

    private int page = 0;
    private int pageSize = 9;
    private String sort = "createAt";
    private String search="";
    private int min = 0;
    private int max = 0;

    private List<Long> categories = new ArrayList<>();
    private List<Long> brands = new ArrayList<>();
    private List<String> colors = new ArrayList<>();


}
