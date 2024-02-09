package com.lcwd.electronic.store.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApiResponseMessage {
    private String message;
    private boolean success;
}
