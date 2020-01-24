package com.jithin.ecommerce.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Address extends BaseModel {

    @NotNull(message = "address line field is required")
    private String address_line1;
    private String address_line2;
    @NotNull(message = "area field is required")
    private String area;
    @NotNull(message = "postal code field is required")
    private String postalCode;
    @NotNull(message = "phone number field is required")
    private String phoneNumber;
    @NotNull(message = "country code field is required")
    private String countryCode;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
