package com.jithin.ecommerce.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test()
    public void test(){

    }
}