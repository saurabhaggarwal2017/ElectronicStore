package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.config.ImagePath;
import com.lcwd.electronic.store.dto.*;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ImagePath imagePath;

    // create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategoryHandler(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategory(@Valid @RequestBody ProductDto productDto, @PathVariable String categoryId) {
        ProductDto productWithCategory = productService.createProductWithCategory(categoryId, productDto);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryHandler(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateProductCategory(@PathVariable String categoryId, @PathVariable String productId) {
        ProductDto productDto = productService.updateProductCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategoryHandler(@PathVariable String categoryId) throws IOException {
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage response = ApiResponseMessage.builder().message("Category of id : " + categoryId + " is SuccessFully Deleted!!").success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get All
    @GetMapping
    public ResponseEntity<PageableResponse> getAllCategoryHandler(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        PageableResponse<CategoryDto> response = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {
        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(singleCategory, HttpStatus.OK);
    }

    // search category
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword) {
        List<CategoryDto> categoryDtos = categoryService.searchCategory(keyword);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam MultipartFile categoryImage, @PathVariable String categoryId) throws IOException {
        CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);

        String imageName = fileService.uploadFile(categoryImage, imagePath.getCategoryImagesPath());
        categoryDto.setCoverImage(imageName);
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);

        ImageResponse response = ImageResponse.builder().imageName(imageName).message("The title of Category : " + categoryDto1.getTitle() + " cover Image is successfully uploaded!").success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);
        InputStream resource = fileService.getResource(imagePath.getCategoryImagesPath(), categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping("/{categoryId}/product")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsOfSameCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        PageableResponse<ProductDto> productsByCategory = productService.getProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productsByCategory, HttpStatus.OK);
    }

}
