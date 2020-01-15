package com.jithin.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddDepartmentRequestDto {

    @NotNull(message = "this field is required")
    private Long departmentId;
}
