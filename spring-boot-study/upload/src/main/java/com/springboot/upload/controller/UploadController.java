package com.springboot.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 上传文件控制器
 * 直接上传到服务器
 */
@Controller
public class UploadController {
//    指定一个临时路径作为上传目录
    // private  static  String UPLOAD_FOLDER="D:/temp/";

    //    遇到http://localhost:8080,则跳转到upload.html页面
    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile srcFile,
                             RedirectAttributes redirectAttributes) {
//        前端没有选择文件，srcFile为空
        if (srcFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择一个文件");
            return "redirect:upload_status";
        }
//        选择了文件，开始进行上传操作
        try {
            //构建上传目标路径,找到项目的target的classes目录
            File descFile = new File(ResourceUtils.getURL("classpath").getPath());
            if (!descFile.exists()) {
                descFile = new File("");
            }
            System.out.println("file path " + descFile.getAbsolutePath());
            SimpleDateFormat sf_ = new SimpleDateFormat("yyyyMMddHHmmss");
            String times = sf_.format(new Date());
            File upload = new File(descFile.getAbsolutePath(), times);
            //若目标文件加不存在,则创建一个
            if (!upload.exists()) {
                upload.mkdir();
            }
            System.out.println("完整的上传路径：" + upload.getAbsolutePath() + "/" + srcFile.getOriginalFilename());
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
// 获得文件原始名称
            String fileName = srcFile.getOriginalFilename();
// 获得文件后缀名称
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
// 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;

            byte[] bytes = srcFile.getBytes();
            //  拼接上传路径
            // Path path = Paths.get(UPLOAD_FOLDER+srcFile.getOriginalFilename());
            //通过项目路径，拼接上传路径
            Path path = Paths.get(upload.getAbsolutePath() + "/" + newFileName);
//   最重要一部，将源文件写入目标地址!!!!
            Files.write(path, bytes);
//            将文件上传成功得分信息写入messages
            redirectAttributes.addFlashAttribute("message", "文件上传成功！" + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:upload_status";
    }

    //    匹配upload_status页面
    @GetMapping("/upload_status")
    public String uploadStatusPage() {
        return "upload_status";
    }
}
