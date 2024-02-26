package com.lcwd.electronic.store.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @Size(min = 3,message = "Min 3 character must be required!")
    private String title;
    @NotBlank(message = "Description required!!")
    private String description;
    @NotBlank(message = "Give cover image for the category!!")
    private String coverImage;
}
