package org.bear.admin.config;

import org.springframework.beans.factory.annotation.Value;

public class AdminConfig {

    @Value("${uploadPath}")
    public  String uploadPath;

    @Value("${imageUrl}")
    public String imageUrl;

}
