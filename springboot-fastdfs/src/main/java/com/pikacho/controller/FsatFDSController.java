package com.pikacho.controller;

import com.pikacho.utils.FastDFSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FsatFDSController {
    @Autowired
    private FastDFSClientUtil clientUtil;

    /**
     * 文件下载
     * @param fileUrl
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public String downloadFile(String fileUrl){
        byte[] bs= clientUtil.downloadFile(fileUrl);
        if(bs != null){
            return "下载完成";
        }
        return "下载失败";
    }


    /**
     * 文件上传
     * @param mf
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile mf){
        try {
            String upload = clientUtil.upload(mf);
            return upload;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    @RequestMapping("/index")
    public String page(){
        return "index";
    }

}