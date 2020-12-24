package com.leyou.upload.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/png");

    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //校验文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法： {}",originalFilename);
            return null;
        }

        String fileName;
        try {
            //校验文件的内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read == null){
                LOGGER.info("文件内容不合法： {}",originalFilename);
                return null;
            }

            originalFilename = System.currentTimeMillis()+originalFilename;
            //保存到文件的服务器
            file.transferTo(new File("/home/zph/IdeaProjects/leyou-manage-web/leyou-manage-web/static/image/"+originalFilename));

        }catch (Exception e){
            LOGGER.info("服务器内部错误： {}",originalFilename);
            e.printStackTrace();
        }
        //返回url进行回显
        return "http://image.leyou.com/"+originalFilename;
    }
}
