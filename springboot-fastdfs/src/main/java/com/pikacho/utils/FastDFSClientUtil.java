package com.pikacho.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FastDFSClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSClientUtil.class);
    @Resource
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf(".") + 1
        );
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                multipartFile.getInputStream(),
                multipartFile.getSize(),
                originalFilename,
                null
        );
        return storePath.getFullPath();

    }
    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            LOGGER.info("fileUrl == >>文件路径为空...");
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    public byte[] downloadFile(String fileUrl){
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = this.storageClient.downloadFile(group, path, downloadByteArray);
        try {

            FileOutputStream fileOutputStream = new FileOutputStream("F:\\java\\xianxicode\\xiazai.jpg");
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}