//package com.takealook.takealook.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//
//@Configuration
//public class FileUploadConfig {
//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        System.out.println("아니 근데 그러면 이거는 실행되고 있는거임?????");
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
//        multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 파일당 업로드 크기 제한 (5MB)
//        return multipartResolver;
//    }
//}
