package com.bookstore.crud.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map upload(MultipartFile multipartfile) throws IOException;
    Map delete(String id) throws IOException;
}
