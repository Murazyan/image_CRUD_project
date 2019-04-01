package com.example.my_task.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {
    @Value("${image.folder}")
    private String imageUploadDir;
   static final List<String> requiredPhotosName = new ArrayList<>(Arrays.asList("2.jpg","3.jpg","4.jpg","5.jpg"));

    @GetMapping("/")
    public String openModerationHtml(ModelMap modelMap) {
        File imageForlder = new File(imageUploadDir);
        File[] images = imageForlder.listFiles();
        List<String> requiredImageNames= new ArrayList<>();
        List<String> additionalImagesNames = new ArrayList<>();
        String retushImageName = "";
        for (File image : images) {
            if (image.getName().contains("1.jpg")) {
                retushImageName=image.getName();
            }
            else if(requiredPhotosName.contains(image.getName())){
                requiredImageNames.add(image.getName());
            }
            else {
                additionalImagesNames.add(image.getName());
            }
        }
        modelMap.addAttribute("retushImageName",retushImageName);
        modelMap.addAttribute("requiredImageNames",requiredImageNames);
        modelMap.addAttribute("additionalImagesNames",additionalImagesNames);
        return "moderation";
    }

}
