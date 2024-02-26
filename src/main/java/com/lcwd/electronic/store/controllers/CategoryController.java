package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.config.ImagePath;
import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.ImageResponse;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.FileService;
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
    CategoryService categoryService;
    @Autowired
    FileService fileService;
    @Autowired
    ImagePath imagePath;

    // create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategoryHandler(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryHandler(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
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

}
