package com.jithin.ecommerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.exception.DataException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@EqualsAndHashCode
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createAt = new Date();
    private Date updatedAt = new Date();
}
