package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;

public interface ProductService {
    //create
    ProductDto createProduct(ProductDto productDto);

    ProductDto createProductWithCategory(String categoryId, ProductDto productDto);

    //update
    ProductDto updateProduct(ProductDto productDto, String productId);

    ProductDto updateProductCategory(String productId, String categoryId);

    //delete
    void deleteProduct(String productId);

    //get single
    ProductDto getSingleProduct(String productId);

    //get all
    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all which are active
    PageableResponse<ProductDto> getAllActiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search product by title
    PageableResponse<ProductDto> searchProduct(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getProductsByCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
}
