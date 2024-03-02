package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.config.ImagePath;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ImagePath imagePath;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        // add date
        productDto.setProductAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(String categoryId, ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Not found of given id!!"));

        // add unique id
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        // add date
        productDto.setProductAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        product.setCategory(category);
//        logger.info("category Details : {}", category);
        Product savedProduct = productRepository.save(product);
//        logger.info("Product details : {} ", savedProduct);
        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not Found of given Id!"));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setProductImage(productDto.getProductImage());
        product.setPrice(productDto.getPrice());
        product.setActiveProduct(productDto.isActiveProduct());
        product.setStock(productDto.isStock());
        product.setQuantity(productDto.getQuantity());

        Product updatedProduct = productRepository.save(product);

        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductCategory(String productId, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found!!"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found!!"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id!!"));
        // delete image
        String fullPathWithImage = imagePath.getProductImagesPath() + product.getProductImage();
        Path path = Paths.get(fullPathWithImage);
        try {
            Files.delete(path);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productRepository.delete(product);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id!!"));

        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> allProducts = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allProducts, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllActiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> allProducts = productRepository.findByActiveProductTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allProducts, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchProduct(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> allProducts = productRepository.findByTitleContaining(keyword, pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allProducts, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getProductsByCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found!!"));

        Sort sort = sortBy.equalsIgnoreCase("desc") ? Sort.by(Sort.Direction.DESC,sortBy) : Sort.by(Sort.Direction.ASC,sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> products = productRepository.findByCategory(category, pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(products, ProductDto.class);
        return pageableResponse;
    }
}
