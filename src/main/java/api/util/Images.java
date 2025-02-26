package api.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Images {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public static String saveImage(MultipartFile image) throws IOException {
        File uploadPath = new File(UPLOAD_DIR);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File targetFile = new File(uploadPath, fileName);
        image.transferTo(targetFile.getAbsoluteFile());

        return UPLOAD_DIR + fileName;
    }

}
