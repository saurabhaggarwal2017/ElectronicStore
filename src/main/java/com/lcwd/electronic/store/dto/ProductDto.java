package com.lcwd.electronic.store.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productId;
    @NotBlank(message = "Don't keep title blank!")
    @Size(max = 80, message = "Length is above 80 character!")
    private String title;
    @NotBlank(message = "Don't keep description blank!")
    private String description;
    @NotBlank(message = "Don't keep brand blank!")
    private String brand;
    private int price;
    private int quantity;
    private Date productAddedDate;
    private boolean activeProduct;
    private boolean stock;
    @NotBlank(message = "Don't keep product-Image blank!")
    private String productImage;

    private CategoryDto category;

}
