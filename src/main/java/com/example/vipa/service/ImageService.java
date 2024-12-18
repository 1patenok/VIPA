package com.example.vipa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageService {
    private static final String POST_IMAGE_SUBDIR = "/images/post-images/";
    private static final String CLIENT_IMAGE_SUBDIR = "/images/client-images/";
    private static final String CATEGORY_IMAGE_SUBDIR = "/images/category-images/";

    private final Path postImageUploadPath;
    private final Path clientImageUploadPath;
    private final Path categoryImageUploadPath;

    // Конструктор для инициализации пути
    public ImageService(@Value("${spring.servlet.multipart.location}") String imageUploadDir) throws IOException {
        // Создаем абсолютный путь и нормализуем его
        this.postImageUploadPath = Paths.get(imageUploadDir + POST_IMAGE_SUBDIR)
                .toAbsolutePath().normalize();
        this.clientImageUploadPath = Paths.get(imageUploadDir + CLIENT_IMAGE_SUBDIR)
                .toAbsolutePath().normalize();
        this.categoryImageUploadPath = Paths.get(imageUploadDir + CATEGORY_IMAGE_SUBDIR)
                .toAbsolutePath().normalize();

        // Проверяем, существует ли папка, если нет - создаем
        Files.createDirectories(postImageUploadPath);
    }

    // Метод для сохранения изображения
    public String savePostImage(MultipartFile postImageFile) {
        if (postImageFile == null || postImageFile.isEmpty()) {
            throw new IllegalArgumentException("Файл изображения отсутствует.");
        }
        // Получаем имя файла
        String fileName = postImageFile.getOriginalFilename();
        assert fileName != null;
        // Создаем путь для сохранения
        Path targetLocation = postImageUploadPath.resolve(fileName);
        // Если файл с таким именем уже существует, генерируем уникальное имя
        if (Files.exists(targetLocation)) {
            fileName = System.currentTimeMillis() + "-" + fileName;
            targetLocation = postImageUploadPath.resolve(fileName);
        }
        try {
            // Копируем файл в директорию
            Files.copy(postImageFile.getInputStream(), targetLocation);
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла: {}", e.getMessage());
            throw new RuntimeException("Не удалось сохранить файл.");
        }
        // Возвращаем путь к файлу для использования в URL
        return POST_IMAGE_SUBDIR  + fileName;
    }
}
