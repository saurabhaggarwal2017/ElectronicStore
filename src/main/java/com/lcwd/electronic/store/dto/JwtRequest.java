package com.lcwd.electronic.store.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
