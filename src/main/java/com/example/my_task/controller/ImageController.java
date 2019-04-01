package com.example.my_task.controller;


import com.example.my_task.dto.DeletingImage;
import com.example.my_task.dto.ImagesData;
import com.example.my_task.dto.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/image")
public class ImageController {



    @Value("${image.folder}")
    private String imageUploadDir;
    @Value("${image.folder.temp}")
    private String tempDirectory;



    @PostMapping("/addimage")
    public ResponseEntity upload1(@RequestParam(name = "picture") MultipartFile file,
                                  @RequestParam(name = "oldPicName")String oldPicName) throws IOException {
        new File(imageUploadDir+oldPicName).delete(); // delete old picture
        file.transferTo(new File(imageUploadDir + oldPicName));
        return ResponseEntity.ok("");
    }




    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response,
                                    @RequestParam("fileName") String fileName) throws IOException {
        InputStream in =null;
        try {
             in = new FileInputStream(imageUploadDir + fileName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            System.out.println("File not found");
        }
        finally {
            if(in!=null)
            in.close();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity deleteImageByImageName(@RequestParam(name = "imageName")String imageName){

        System.out.println("deletic "+imageName);
        File file = new File(imageUploadDir + imageName);
        System.out.println(imageUploadDir+imageName);
        file.delete();
        return ResponseEntity.status(400).body(Response.builder()
                .success(true)
                .message("Ok")
                .build());
    }

    @PostMapping("/addImageMany")
    public ResponseEntity addImageMany(@RequestParam(name = "pictures")List<MultipartFile> multipartFiles) throws IOException {
        System.out.println(multipartFiles.size());
        System.out.println(multipartFiles);
        for (MultipartFile multipartFile : multipartFiles) {
            System.out.println(multipartFile);
            multipartFile.transferTo(new File("C:\\Users\\muraz\\Desktop\\"+multipartFile.getOriginalFilename()));

        }
        return ResponseEntity.ok(multipartFiles);
    }

    @DeleteMapping("/many")
    public ResponseEntity deleteImageByImageNmae_Many(@RequestBody DeletingImage deletingImage){
        List<String> imageNames = deletingImage.getImageNames();
        for (String imageName : imageNames) {
            File file = new File(imageUploadDir+imageName);
            file.delete();
        }
        return ResponseEntity.ok("Deteted");
    }

    @PostMapping("/changeDireqtion")
    public ResponseEntity changeDireqtionImages(@RequestBody ImagesData imagesData){
        List<Map> data = imagesData.getData();
        File temp = new File(tempDirectory);
        temp.mkdirs();
        for (Map imageData : data) {
            String imageName =(String) imageData.get("imageName");
            String newDireqtion =(String) imageData.get("newDireqtion");
            File oldFile = new File(imageUploadDir+imageName);
            oldFile.renameTo(new File(tempDirectory+newDireqtion+".jpg"));
        }
        File[] tempFiles = temp.listFiles();
        for (File file : tempFiles) {
            file.renameTo(new File(imageUploadDir+file.getName()));
        }
        temp.delete();
        return ResponseEntity.ok("ok");

    }

//    @PostMapping("/addImages")
//    public ResponseEntity changeImages(@RequestParam(name = "newPictures") List<MultipartFile> files,
//                                       @RequestParam(name = "oldPicNames")List<String> oldPicNames) throws IOException {
//        for (int i = 0; i < Math.min(files.size(), oldPicNames.size()); i++) {
//            File file = new File(imageUploadDir+oldPicNames.get(i));
//            file.delete();
//            files.get(i).transferTo(new File(imageUploadDir+oldPicNames.get(i)));
//        }
//       return ResponseEntity.ok("Changed");
//
//    }



    @PostMapping("/addImages")
    public ResponseEntity changeImages(@RequestParam(name = "newPictures") List<MultipartFile> files,
                                       @RequestParam(name = "oldPicNames")List<String> oldPicNames) throws IOException {
        for (int i = 0; i < Math.min(10, oldPicNames.size()); i++) {
            File file = new File(imageUploadDir+oldPicNames.get(i));
            file.delete();
            files.get(i).transferTo(new File(imageUploadDir+oldPicNames.get(i)));
        }
        return ResponseEntity.ok("Changed");
    }

}
