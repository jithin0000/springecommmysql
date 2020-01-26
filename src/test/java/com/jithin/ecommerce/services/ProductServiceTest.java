package com.jithin.ecommerce.services;

import com.github.javafaker.Faker;
import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.exception.ProductNotFoundException;
import com.jithin.ecommerce.model.Brand;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Color;
import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    // TODO: 22/01/20 need to remove faker from this, its a big bug
    // FIXME: 22/01/20 need to remove faker from test

    private ProductRepository productRepositoryMock;

    ProductService SUT;
    private static Long productId = 1L;
    private static Long invalidProductId = -12L;

    @BeforeEach
    void setUp() {
        productRepositoryMock = mock(ProductRepository.class);
        SUT = new ProductService(productRepositoryMock);
    }

    @Test
    void getAll() {
        mockProductSuccess();

        List<Product> result = (List<Product>) SUT.getAll();
        verify(productRepositoryMock, times(1)).findAll();
        assertNotNull(result);
        assertEquals(result.size(), 2);
    }

    @Test
    void getIdSuccessResponse(){

        mockSingleProductSuccess();
        ArgumentCaptor<Long> ac = ArgumentCaptor.forClass(Long.class);
        Optional<Product> result = SUT.get(productId);
        verify(productRepositoryMock, times(1)).findById(ac.capture());
        assertEquals(result.get().getId(), ac.getValue());
    }

    @Test
    void getByIdThrowErrorWhenIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mockSingleProductFailure();
            SUT.get(null);
        });

        assertTrue(exception instanceof IllegalArgumentException);
    }

    @Test
    void getByIdReturnEmptyfIdIsInvalid(){
        mockNullResponse();
        Optional<Product> product = SUT.get(productId);
        verify(productRepositoryMock, times(1)).findById(productId);
        assertEquals(Optional.empty(), product);
    }

    private void mockNullResponse() {
        when(productRepositoryMock.findById(invalidProductId)).thenReturn(Optional.empty());
    }


    @Test
    void createProductWithSuccess() {

        mockCreateSuccess();
        Product result = SUT.create(getSingleProduct());
        ArgumentCaptor<Product> ac = ArgumentCaptor.forClass(Product.class);
        verify(productRepositoryMock, times(1)).save(ac.capture());
        assertTrue(result instanceof Product);
        assertEquals(result.getId(), ac.getValue().getId());
    }

    @Test
    void createProductWithFailure() {

        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            mockCreateFailure();
            SUT.create(getNullableBodyParameter());
        });
        verify(productRepositoryMock, times(1)).save(getNullableBodyParameter());
        assertTrue(exception instanceof IllegalArgumentException);
    }


    @Test
    void deleteSuccess() {

        doNothing().when(spy(productRepositoryMock)).deleteById(productId);
        ArgumentCaptor<Long> ac = ArgumentCaptor.forClass(Long.class);
        DeleteResponseDto result = SUT.delete(productId);

        verify(productRepositoryMock, times(1)).deleteById(ac.capture());
        assertEquals(result.getId(), ac.getValue());

    }


    @Test
    void update_check_update_contains_id_and_body() {

        mockSingleProductSuccess();
        mockCreateSuccess();

        ArgumentCaptor<Long> ac = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Product> pac = ArgumentCaptor.forClass(Product.class);

        Product result = SUT.update(productId, getSingleProduct());
        verify(productRepositoryMock, times(1)).findById(ac.capture());
        verify(productRepositoryMock, times(1)).save(pac.capture());
        assertEquals(result.getId(), ac.getValue());
        assertNotNull(result);
        assertEquals(result.getName(), pac.getValue().getName());
    }
    @Test
    void update_with_invalid_id(){
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            mocksInvalidIdException();
            SUT.update(invalidProductId, getSingleProduct());
        });
        assertTrue(exception instanceof ProductNotFoundException);
        assertTrue(exception.getLocalizedMessage().contains(invalidProductId.toString()));
    }

    private void mocksInvalidIdException() {
        when(productRepositoryMock.findById(invalidProductId)).thenThrow(new ProductNotFoundException(invalidProductId));

    }


    @Test
    void paginatedProductList() {

        when(productRepositoryMock
                .findByNameContainingIgnoreCase("search", PageRequest.of(1,1, Sort.by("createAt"))))
                .thenReturn(getPaginatedProduct());

        Page<Product> result = SUT.getPaginatedResult(1, 1, "createAt", "search");
        verify(productRepositoryMock, times(1))
                .findByNameContainingIgnoreCase("search", PageRequest.of(1, 1, Sort.by("createAt")));

    }

    private Page<Product> getPaginatedProduct() {
        Page<Product> products = new PageImpl<Product>((List<Product>) products());
        return products;
    }

    @Test
    void getFilteredProducts() {
    }


    private void mockCreateFailure() throws Exception {
        when(productRepositoryMock.save(getNullableBodyParameter())).thenThrow(IllegalArgumentException.class);
    }


    private Product getNullableBodyParameter() {
        Product product = new Product();
        return product;
    }


    private void mockCreateSuccess() {
        when(productRepositoryMock.save(getSingleProduct())).thenReturn(getSingleProduct());

    }

    private Product getSingleProduct() {
        Product product = new Product();
        product.setId(productId);
        product.setName("jithin");
        product.setDescription("this is for testing man");
        product.setQuantity(2);
        product.setPrice(456);
        Category category = new Category();
        category.setName("category");
        product.setCategory(category);

        Color color = new Color();
        color.setName("color");
        product.getColors().add(color);

        Brand brand = new Brand();
        brand.setName("company");
        product.setBrand(brand);
        return product;
    }



    private void mockSingleProductFailure() throws Exception {
        when(productRepositoryMock.findById(null)).thenThrow(IllegalArgumentException.class);

    }

    private void mockSingleProductSuccess() {
        when(productRepositoryMock.findById(productId))
                .thenReturn(getSingleOptionalProduct());
    }

    private Optional<Product> getSingleOptionalProduct() {
        Product product = new Product();
        product.setId(productId);
        Faker faker = new Faker();
        product.setName(faker.artist().name());
        product.setDescription(faker.lorem().sentence(20));
        product.setQuantity(faker.number().randomDigitNotZero());
        product.setPrice(faker.number().numberBetween(100, 900));
        Category category = new Category();
        category.setName(faker.commerce().department());
        product.setCategory(category);

        Color color = new Color();
        color.setName(faker.color().name());
        product.getColors().add(color);

        Brand brand = new Brand();
        brand.setName(faker.company().name());
        product.setBrand(brand);
        return Optional.of(product);
    }



    private void mockProductSuccess() {
        when(productRepositoryMock.findAll()).thenReturn(products());

    }

    private Iterable<Product> products() {

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Product product = new Product();
            Faker faker = new Faker();
            product.setName(faker.artist().name());
            product.setDescription(faker.lorem().sentence(20));
            product.setQuantity(faker.number().randomDigitNotZero());
            product.setPrice(faker.number().numberBetween(100, 900));
            Category category = new Category();
            category.setName(faker.commerce().department());
            product.setCategory(category);

            Color color = new Color();
            color.setName(faker.color().name());
            product.getColors().add(color);

            Brand brand = new Brand();
            brand.setName(faker.company().name());
            product.setBrand(brand);
            products.add(product);
        }
        return products;
    }


}