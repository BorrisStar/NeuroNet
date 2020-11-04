package com.borris.nn.rest;

import com.borris.nn.service.UploadService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

@Controller
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {this.uploadService = uploadService;}

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file == null || file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        // save the file on the local file system
        // preprocess handwritten digit
        uploadService.uploadAndPreProcess(file, attributes);

        return "redirect:/";
    }
}
