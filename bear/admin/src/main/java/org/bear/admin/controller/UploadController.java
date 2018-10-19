package org.bear.admin.controller;

import org.bear.admin.bean.PicUploadResult;
import org.bear.admin.config.AdminConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RequestMapping("/pic")
@ResponseBody
@Controller
public class UploadController {

    private Logger LOGGER= LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private AdminConfig adminConfig;


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public PicUploadResult upload(@RequestParam  MultipartFile uploadFile, HttpServletRequest request) throws IOException {




        LOGGER.debug("开始上传图片................");

        //上传图片的路径

        LOGGER.debug("上传图片的路径:{}",adminConfig.uploadPath);

        //获取后缀
        String filename=uploadFile.getOriginalFilename();
        LOGGER.debug("上传文件的源文件名:{}",filename);
        String ext=filename.substring(filename.lastIndexOf("."));
        //生成新的文件名
        String uuid_name=UUID.randomUUID().toString().replaceAll("-","")+ext;

        LOGGER.debug("生成的文件名{}",uuid_name);
        //准备文件对象
        File file =new File(adminConfig.uploadPath+File.separator+uuid_name);

        //保存图片到服务器
        uploadFile.transferTo(file);

        //创建返回结果
        PicUploadResult picUploadResult=new PicUploadResult();

        BufferedImage bufferedImage = ImageIO.read(file);
        if(bufferedImage!=null){
            LOGGER.info("上传成功,上传者的ip地址为:{}",request.getRemoteAddr());
            //设置上传成功的状态
            picUploadResult.setError(0);
            //设置上传成功的路径
            picUploadResult.setUrl(adminConfig.imageUrl+uuid_name);
            LOGGER.info("上传成功的路径为{}");
            //设置图片的宽和高
            picUploadResult.setWidth(bufferedImage.getWidth()+"");
            picUploadResult.setHeight(bufferedImage.getHeight()+"");
        }else{
            LOGGER.error("上传失败,上传的文件名为{},上传者的ip地址为{}",uploadFile.getOriginalFilename(),request.getRemoteAddr());
            //上传失败
            picUploadResult.setError(1);
            picUploadResult.setMessage("图片上传失败!");
        }

        return picUploadResult;
    }


}
