package com.lcwd.electronic.store.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageResponse {
    private String imageName;
    private String message;
    private boolean success;
}
