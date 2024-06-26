package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.config.ImagePath;
import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.ImageResponse;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImagePath imagePath;
    @Autowired
    private FileService fileService;

    // create
    @PostMapping
    public ResponseEntity<ProductDto> createProductHandler(@Valid @RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProductHandler(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto product = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProductHandler(@PathVariable String productId) {
        productService.deleteProduct(productId);
        ApiResponseMessage response = ApiResponseMessage.builder().message("Product Successfully deleted!!").success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get single products
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        ProductDto product = productService.getSingleProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // get all products
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductHandler(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    // get live product
    @GetMapping("/active")
    public ResponseEntity<PageableResponse<ProductDto>> getAllActiveProductHandler(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        PageableResponse<ProductDto> allProduct = productService.getAllActiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    // search product
    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllActiveProductHandler(
            @PathVariable(value = "keyword") String keyword,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        PageableResponse<ProductDto> allProduct = productService.searchProduct(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    // upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage") MultipartFile productImage, @PathVariable String productId) throws IOException {
        String image = fileService.uploadFile(productImage, imagePath.getProductImagesPath());

        ProductDto productDto = productService.getSingleProduct(productId);
        productDto.setProductImage(image);
        ProductDto updatedProductDto = productService.updateProduct(productDto, productId);

        ImageResponse response = ImageResponse.builder().imageName(updatedProductDto.getProductImage()).message("Product Image successfully Uploaded!!").success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // serve image
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getSingleProduct(productId);
        InputStream resource = fileService.getResource(imagePath.getProductImagesPath(), productDto.getProductImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
