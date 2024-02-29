package com.lcwd.electronic.store.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "path")
public class ImagePath {
    private String userImagesPath;
    private String categoryImagesPath;
    private String productImagesPath;
}
