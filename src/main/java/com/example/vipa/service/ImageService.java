package com.example.vipa.service;

import com.example.vipa.model.PostImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ImageService {
    private final Path uploadPath;

    // Конструктор для инициализации пути
    public ImageService(@Value("${spring.servlet.multipart.location}") String postImageUploadDir) throws IOException {
        // Создаем абсолютный путь и нормализуем его
        this.uploadPath = Paths.get(postImageUploadDir).toAbsolutePath().normalize();

        // Проверяем, существует ли папка, если нет - создаем
        Files.createDirectories(uploadPath);
    }

    // Метод для получения изображений по списку PostImage
/*    public List<Path> getImages(List<PostImage> images) {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("Список изображений пуст или отсутствует.");
        }
        List<Path> imagePaths = new ArrayList<>();
        // Проходим по каждому элементу списка изображений
        for (PostImage image : images) {
            String imageName = image.getUrl(); // Предположим, что в PostImage есть метод getImageName()
            if (imageName == null || imageName.isEmpty()) {
                log.warn("Имя изображения пустое: {}", image);
                continue;
            }
            // Формируем путь к изображению
            Path imagePath = uploadPath.resolve(imageName).normalize();
            // Проверяем, существует ли файл
            if (Files.exists(imagePath)) {
                imagePaths.add(imagePath);
            } else {
                log.error("Изображение не найдено: {}", imageName);
                throw new RuntimeException("Изображение не найдено: " + imageName);
            }
        }
        return imagePaths;
    }*/

    // Метод для сохранения изображения
    public String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл изображения отсутствует.");
        }
        // Получаем имя файла
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        // Создаем путь для сохранения
        Path targetLocation = uploadPath.resolve(fileName);
        // Если файл с таким именем уже существует, генерируем уникальное имя
        if (Files.exists(targetLocation)) {
            fileName = System.currentTimeMillis() + "-" + fileName;
            targetLocation = uploadPath.resolve(fileName);
        }
        try {
            // Копируем файл в директорию
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла: {}", e.getMessage());
            throw new RuntimeException("Не удалось сохранить файл.");
        }
        // Возвращаем путь к файлу для использования в URL
        return "/uploads/post-images/" + fileName;
    }
}
