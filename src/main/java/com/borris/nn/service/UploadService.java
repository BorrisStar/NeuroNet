package com.borris.nn.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

@Service
public class UploadService {

    @Value("${uploadDir}")
    private String UPLOAD_DIR;
    @Value("${workDir}")
    private String WORK_DIR;
    @Value("${pythonScript}")
    private String PYTHON_SCRIPT;

    private final NNService nnService;

    public UploadService(NNService nnService) {this.nnService = nnService;}

    public void uploadAndPreProcess(MultipartFile file, RedirectAttributes attributes) {
        String fileName = file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            String path = UPLOAD_DIR + fileName;
            FileUtils.writeByteArrayToFile(new File(path), bytes);

            String command = String.format("python3 %s -tp %s -wd %s", PYTHON_SCRIPT, path,  WORK_DIR);
            nnService.preProcessHandWrittenDigit(command);
            // return success response
            attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
            FileUtils.cleanDirectory(new File(UPLOAD_DIR));
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "You failed to upload " + fileName + " => " + e.getMessage());
        }
    }
}
