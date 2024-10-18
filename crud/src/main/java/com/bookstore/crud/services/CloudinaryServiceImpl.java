package com.bookstore.crud.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service

public class CloudinaryServiceImpl implements CloudinaryService {
    @Value("${CLOUDINARY.ID}")
    String ID;
    @Value("${CLOUDINARY.KEY}")
    String KEY;
    @Value("${CLOUDINARY.SECRET}")
    String SECRET;

    private Cloudinary cloudinary;

    @PostConstruct
    private void init() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", ID);
        valuesMap.put("api_key", KEY);
        valuesMap.put("api_secret", SECRET);
        cloudinary = new Cloudinary(valuesMap);
    }

    private CloudinaryServiceImpl(){

    }

    @Override
    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if(!Files.deleteIfExists(file.toPath())){
            throw new FileNotFoundException("Failed to delete temporaly fiile");
        }
        return result;
    }

    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}
