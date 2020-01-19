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

    private List<Long> categories = new ArrayList<>();

}
