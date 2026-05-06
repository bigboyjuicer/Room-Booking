package api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class Images {

    @Value("${UPLOAD_DIR}")
    private String uploadDir;

    public String saveImage(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File targetFile = new File(uploadPath.toFile(), fileName);
        image.transferTo(targetFile.getAbsoluteFile());

        return fileName;
    }

    public String getUploadDir() {
        return uploadDir;
    }
}
